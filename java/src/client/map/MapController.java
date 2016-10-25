package client.map;

import client.base.Controller;
import client.data.RobPlayerInfo;
import client.devcards.DevCardController;
import client.resources.ResourceBarController;
import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.models.game.ClientModel;
import shared.models.game.GameMap;
import shared.models.game.Hex;
import shared.models.game.Player;
import shared.models.moves.BuildCityAction;
import shared.models.moves.BuildRoadAction;
import shared.models.moves.BuildSettlementAction;
import shared.models.moves.RobPlayerAction;
import shared.utils.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {

    private static final Logger LOGGER = Logger.getLogger(MapController.class.getSimpleName());
    private GameMap prevMap = new GameMap();
    private IRobView robView;

    public MapController(IMapView view, IRobView robView) {
        super(view);
        setRobView(robView);
        observeClientModel();
    }

    @Override
    public IMapView getView() {
        return (IMapView) super.getView();
    }

    private IRobView getRobView() {
        return robView;
    }

    private void setRobView(IRobView robView) {
        this.robView = robView;
    }

    protected void initFromModel() {
        updateFromModel(getModel());
    }

    @Override
    protected synchronized void updateFromModel(ClientModel model) {
        GameMap curMap = model.getMap();
        if ((prevMap == curMap) || curMap.equals(prevMap)) {
            LOGGER.fine("Skipping map update as it is the same");
            return;
        }
        LOGGER.info("Updating map...");
        IMapView view = getView();

        // It seems that right now, there's no way to clear the MapView or
        // remove any placed item. Not sure how e.g. communication errors
        // are supposed to be handled properly with that.

        // This could possibly be made more efficient.
        // The MapComponent is already *horrifically* inefficient, however
        // (such as performing a complete redraw every time the mouse is moved)

        Map<PlayerIndex, CatanColor> colors = new HashMap<>();
        for (Player p : model.getPlayers()) {
            if(p!=null)
                colors.put(p.getPlayerIndex(), p.getColor());
        }

        LOGGER.fine("Updating hexes...");
        MapUtils.difference(curMap.getHexes(), prevMap.getHexes())
                .forEach((loc, hex) -> {
                    LOGGER.finer(() -> "New hex: " + hex);
                    view.addHex(loc, hex.getResource());
                    int num = hex.getNumber();
                    if (num > 0 && num < 12) {
                        view.addNumber(loc, hex.getNumber());
                    }
                });

        LOGGER.fine("Robber: " + curMap.getRobber());
        view.placeRobber(curMap.getRobber());

        LOGGER.fine("Updating ports...");
        MapUtils.difference(curMap.getPorts(), prevMap.getPorts())
                .forEach((loc, port) -> {
                    LOGGER.finer(() -> "New port: " + port);
                    view.addPort(new EdgeLocation(port.getLocation(), port.getDirection()),
                            port.getPortType());
                });

        LOGGER.fine("Updating settlements...");
        MapUtils.difference(curMap.getSettlements(), prevMap.getSettlements())
                .forEach((loc, index) -> {
                    LOGGER.finer(() -> "New settlement at " + loc + " belonging to " + index);
                    view.placeSettlement(loc, colors.get(index));
                });


        LOGGER.fine("Updating cities...");
        MapUtils.difference(curMap.getCities(), prevMap.getCities())
                .forEach((loc, index) -> {
                    LOGGER.finer(() -> "New city at " + loc + " belonging to " + index);
                    view.placeCity(loc, colors.get(index));
                });

        LOGGER.fine("Updating roads...");
        MapUtils.difference(curMap.getRoads(), prevMap.getRoads())
                .forEach((loc, index) -> {
                    LOGGER.finer(() -> "New road at " + loc + " belonging to " + index);
                    view.placeRoad(loc, colors.get(index));
                });
        prevMap = curMap;
    }

    /**
     * Called by {@link MapComponent#mouseAdapter} (and indirectly by {@link MapView#overlayController}
     * to show visual cues when placing a road.
     *
     * @param edgeLoc The proposed road location
     * @return true if the road can be placed at edgeLoc, false otherwise
     */
    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc) {
        // Needs more information than the edgeLoc!
        // Also need info on whether it's the first few turns, etc.
        return getModel().getMap().canAddRoad(
                edgeLoc,
                getPlayer().getPlayerIndex(),
                getFacade().getTurn().isSetup()
        );
    }

    /**
     * @see #canPlaceRoad
     */
    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc) {
        // GameMap's canAddSettlement as this function should not
        // have been able to be called without affording resources, etc.
        return getModel().getMap().canAddSettlement(
                vertLoc,
                getPlayer().getPlayerIndex(),
                getFacade().getTurn().isSetup()
        );
    }

    /**
     * @see #canPlaceRoad
     */
    @Override
    public boolean canPlaceCity(VertexLocation vertLoc) {
        return getModel().getMap().canUpgradeSettlement(
                vertLoc,
                getPlayer().getPlayerIndex()
        );
    }

    /**
     * @see #canPlaceRoad
     */
    @Override
    public boolean canPlaceRobber(HexLocation hexLoc) {
        // TODO: Switch this to robber facade after fixes
        Hex targetHex = getModel().getMap().getHex(hexLoc);
        return (targetHex != null &&
                targetHex.getResource().isLand() &&
                !hexLoc.equals(getModel().getMap().getRobber())
        );
    }

    /**
     * What happens to get to this point:
     * <p>
     * {@link #startMove} is called.
     * <p>
     * {@link MapView#startDrop} opens a
     * new {@link MapView.MapOverlay}, which is a view.
     * This is given the special controller {@link MapView#overlayController}.
     * <p>
     * It also keeps an additional internal {@link MapComponent}
     * which is the half of the map view which actually handles user input.
     * In {@link MapView.MapOverlay#MapOverlay}, it sets the {@link MapView.MapOverlay#mainMap}
     * controller to the special controller.
     * <p>
     * The "place a road" modal is shown and the user can place one (or possibly cancel).
     * Once a location is chosen and the user clicks,
     * the {@link MapComponent#mouseAdapter}'s {@link java.awt.event.MouseAdapter#mouseClicked}
     * calls its controller's {@link IMapController#placeRoad} function.
     * In the end, this will call the special {@link MapView#overlayController}'s.
     * <p>
     * That then cancels any drops in the overlay, closes the modal,
     * and uses its parent's view's {@link MapView#getController} to get the actual map controller,
     * which then calls this function. Whew.
     */
    @Override
    public void placeRoad(EdgeLocation edgeLoc) {
        getView().placeRoad(edgeLoc, getPlayer().getColor());
//        getFacade().getBuilding().buildRoad(getPlayer(), edgeLoc, );
        getAsync().runModelMethod(server::buildRoad,
                new BuildRoadAction(
                        getFacade().getTurn().isSetup(),
                        edgeLoc,
                        getPlayer().getPlayerIndex()))
                .onError(e -> LOGGER.severe("Failed to place road: " + e.getMessage()))
                .start();
    }

    /**
     * @see #placeRoad
     */
    @Override
    public void placeSettlement(VertexLocation vertLoc) {
        getView().placeSettlement(vertLoc, getPlayer().getColor());
        getAsync().runModelMethod(server::buildSettlement,
                new BuildSettlementAction(
                        getFacade().getTurn().isSetup(),
                        vertLoc,
                        getPlayer().getPlayerIndex()))
                .onError(e -> LOGGER.severe("Failed to place settlement: " + e.getMessage()))
                .start();
    }

    /**
     * @see #placeRoad
     */
    @Override
    public void placeCity(VertexLocation vertLoc) {
        getView().placeCity(vertLoc, getPlayer().getColor());
        getAsync().runModelMethod(server::buildCity,
                new BuildCityAction(vertLoc, getPlayer().getPlayerIndex()))
                .onError(e -> LOGGER.severe("Failed to place city: " + e.getMessage()))
                .start();
    }

    /**
     * Since placing the robber and stealing is a single call to the server,
     * all server communication is done in {@link #robPlayer}.
     * <p>
     * We need to open the robber modal to choose what to steal.
     *
     * @see #placeRoad
     */
    @Override
    public void placeRobber(HexLocation hexLoc) {
        getFacade().getTurn().startRobbing();
        getModel().getMap().setRobber(hexLoc);
        getView().placeRobber(hexLoc);

        List<RobPlayerInfo> robPlayerInfos = new ArrayList<>();
        for (Player p : getModel().getPlayers()) {
            if (p == null ||
                    p.getPlayerIndex() == getPlayer().getPlayerIndex() ||
                    p.getResources().getTotal() == 0) {
                continue;
            }
            robPlayerInfos.add(new RobPlayerInfo(p));
        }
        // TODO: Discover how moving the robber with no robbing candidates is supposed to work.
        if (robPlayerInfos.size() != 0) {
            getRobView().setPlayers(
                    robPlayerInfos.toArray(new RobPlayerInfo[robPlayerInfos.size()])
            );
            getRobView().showModal();
        }
    }

    /**
     * This is called by other controllers, which must call the view.
     * <p>
     * User selects a resource card in the {@link client.resources.ResourceBarView}.
     * <p>
     * The equivalent function e.g. {@link ResourceBarController#buildRoad} is called.
     * {@link client.resources.ResourceBarController#executeElementAction}
     * <p>
     * The action created by {@link client.catan.RightPanel#RightPanel} and
     * {@link client.catan.RightPanel#createStartMoveAction} is called.
     * <p>
     * This function is called.
     * <p>
     * How startMove with robbing is called is unknown.
     * It looks like it may be completely controlled by the MapController possibly.
     * Somehow the {@link client.roll.RollController} will need to be involved.
     */
    @Override
    public void startMove(PieceType pieceType) {
        // There doesn't seem to be a known way to transfer the parameters so placeCity, etc. are called correctly.
        // Class fields could work?
        getView().startDrop(pieceType, getPlayer().getColor(), true);
    }

    /**
     * User presses cancel in the {@link client.map.MapView.MapOverlay}
     * {@link client.map.MapView#overlayController} cancelMove
     * this function
     */
    @Override
    public void cancelMove() {
        // Unlike startMove, this is called *by* the view.
    }

    /**
     * {@link client.devcards.PlayDevCardView#btnGrpPnlListener}
     * plays a soldier card when selected in the modal.
     * <p>
     * It calls {@link DevCardController#playSoldierCard},
     * which then calls this function with an action created
     * in {@link client.catan.RightPanel#RightPanel}.
     * <p>
     * Robber placement should be initiated.
     */
    @Override
    public void playSoldierCard() {
        getView().startDrop(PieceType.ROBBER, getPlayer().getColor(), false);
    }

    /**
     * @see #playSoldierCard
     */
    @Override
    public void playRoadBuildingCard() {
        // TODO: Identify situations when requiring placing two roads is impossible, and preclude card.
        // TODO: Identify if startDrop (OverlayView.showModal) is blocking or not.
        getView().startDrop(PieceType.ROAD, getPlayer().getColor(), false);
        getView().startDrop(PieceType.ROAD, getPlayer().getColor(), false);
    }

    /**
     * First, {@link #placeRobber} is called to physically move the robber.
     * Then, the robber view (modal) is shown.
     * <p>
     * When a player to rob is selected with a button click, this is called.
     * <p>
     * If this is being interpreted correctly,
     * the new robber location has already been chosen at this point.
     *
     * @see RobView#actionListener
     */
    @Override
    public void robPlayer(RobPlayerInfo victim) {
        getAsync().runModelMethod(server::robPlayer,
                new RobPlayerAction(
                        getModel().getMap().getRobber(),
                        getPlayer().getPlayerIndex(),
                        victim.getPlayerIndex().index()))
                .onError(e -> LOGGER.severe("Failed to rob player: " + e.getMessage()))
                .start();
    }

}

