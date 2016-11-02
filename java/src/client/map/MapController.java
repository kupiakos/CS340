package client.map;

import client.base.Controller;
import client.data.RobPlayerInfo;
import client.devcards.DevCardController;
import client.resources.ResourceBarController;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import shared.definitions.*;
import shared.facades.TurnFacade;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.models.game.ClientModel;
import shared.models.game.GameMap;
import shared.models.game.Hex;
import shared.models.game.Player;
import shared.models.moves.*;
import shared.utils.MapUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {

    private static final Logger LOGGER = Logger.getLogger(MapController.class.getSimpleName());
    private boolean dropping = false;
    private GameMap prevMap = new GameMap();
    private Map<PlayerIndex, CatanColor> prevColors = new HashMap<>();
    private IRobView robView;
    private HexLocation newRobberLoc = null;
    // For Road Building Card
    private EdgeLocation firstRoadLoc = null;
    private MapStatus status = MapStatus.NORMAL;

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

    private void updateMap(ClientModel model) {
        GameMap curMap = model.getMap();
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
            colors.put(p.getPlayerIndex(), p.getColor());
        }

        LOGGER.fine("Updating hexes...");
        if (prevMap.getHexes().isEmpty()) {
            for (HexLocation loc : getFacade().getMap().getOceanBorder(curMap.getRadius())) {
                LOGGER.finer(() -> "New water hex:" + loc);
                view.addHex(loc, HexType.WATER);
            }
        }
        MapUtils.difference(curMap.getHexes(), prevMap.getHexes())
                .forEach((loc, hex) -> {
                    LOGGER.finer(() -> "New hex: " + hex);
                    view.addHex(loc, hex.getResource());
                    int num = hex.getNumber();
                    if (num > 0 && num <= 12) {
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

    private boolean haveChangedColors() {
        Map<PlayerIndex, CatanColor> curColors = getModel().getPlayers().stream()
                .collect(Collectors.toMap(Player::getPlayerIndex, Player::getColor));
        boolean changed = !prevColors.equals(curColors);
        prevColors = curColors;
        return changed;
    }

    @Override
    protected synchronized void updateFromModel(ClientModel model) {
        GameMap curMap = model.getMap();
        // Spoof the current client version of the roads during road building
        if (firstRoadLoc != null) {
            curMap.getRoads().put(firstRoadLoc, getPlayer().getPlayerIndex());
        }
        if (haveChangedColors()) {
            // If we've changed colors, force a redraw
            LOGGER.info("Color of players has changed");
            prevMap = new GameMap();
        }
        if ((prevMap == curMap) || curMap.equals(prevMap)) {
            LOGGER.fine("Skipping map update as it is the same");
        } else {
            updateMap(model);
        }

        TurnFacade turn = getFacade().getTurn();
        status.startMovingRobber(this);

        // Initial Setup
        if (turn.isSetup() && turn.isPlayersTurn(getPlayer()) && !dropping) {
            int settlements = getPlayer().getSettlements();
            int roads = getPlayer().getRoads();
            TurnStatus phase = getFacade().getTurn().getPhase();
            int round = phase == TurnStatus.FIRST_ROUND ? 0 : 1;
            boolean endOfTurn = true;
            if (settlements >= Constants.START_SETTLEMENTS - round) {
                endOfTurn = false;
                setStatus(MapStatus.setupSettlements(phase));
            } else if (roads >= Constants.START_ROADS - round) {
                endOfTurn = false;
                setStatus(MapStatus.setupRoads(phase));
            }
            if (!endOfTurn) {
                status.doSetup(this);
            }
        }
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
        dropping = false;
        getView().placeRoad(edgeLoc, getPlayer().getColor());
        status.placeRoad(this, edgeLoc);
    }

    /**
     * @see #placeRoad
     */
    @Override
    public void placeSettlement(VertexLocation vertLoc) {
        dropping = false;
        getView().placeSettlement(vertLoc, getPlayer().getColor());
        getAsync().runModelMethod(server::buildSettlement,
                new BuildSettlementAction(
                        getFacade().getTurn().isSetup(),
                        vertLoc,
                        getPlayer().getPlayerIndex()))
                .onError(e -> LOGGER.severe("Failed to place settlement: " + e.getMessage()))
                .onSuccess(() -> status.keepBuilding(this))
                .start();
    }

    /**
     * @see #placeRoad
     */
    @Override
    public void placeCity(VertexLocation vertLoc) {
        dropping = false;
        getView().placeCity(vertLoc, getPlayer().getColor());
        getAsync().runModelMethod(server::buildCity,
                new BuildCityAction(vertLoc, getPlayer().getPlayerIndex()))
                .onError(e -> LOGGER.severe("Failed to place city: " + e.getMessage()))
                .onSuccess(() -> status.keepBuilding(this))
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
//        getFacade().getTurn().startMovingRobber();
        dropping = false;
        GameMap map = getModel().getMap();
        map.setRobber(hexLoc);
        getView().placeRobber(hexLoc);

        RobPlayerInfo[] robPlayers = map.getHexBuildings(hexLoc).stream()
                .map(map::getBuildingOwner)
                .distinct()
                .filter(pi -> pi != getPlayer().getPlayerIndex())
                .map(pi -> new RobPlayerInfo(getModel().getPlayer(pi)))
                .toArray(RobPlayerInfo[]::new);

        newRobberLoc = hexLoc;
        getRobView().setPlayers(robPlayers);
        getRobView().showModal();
        status.startRobbing(this);
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
        startMove(pieceType, !getFacade().getTurn().isSetup());
    }

    public void startMove(PieceType pieceType, boolean isCancelAllowed) {
        // There doesn't seem to be a known way to transfer the parameters so placeCity, etc. are called correctly.
        // Class fields could work?
        if (!dropping) {
            getView().startDrop(pieceType, getPlayer().getColor(), isCancelAllowed);
        }
        dropping = true;
    }

    /**
     * User presses cancel in the {@link client.map.MapView.MapOverlay}
     * {@link client.map.MapView#overlayController} cancelMove
     * this function
     */
    @Override
    public void cancelMove() {
        // Unlike startMove, this is called *by* the view.
        setStatus(MapStatus.NORMAL);
        dropping = false;
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
        setStatus(MapStatus.SOLDIER_MOVING);
        startMove(PieceType.ROBBER, true);
    }

    /**
     * @see #playSoldierCard
     */
    @Override
    public void playRoadBuildingCard() {
        // TODO: Identify situations when requiring placing two roads is impossible, and preclude card.
        // TODO: Identify if startDrop (OverlayView.showModal) is blocking or not.
        setStatus(MapStatus.ROAD_BUILDING_FIRST);
        startMove(PieceType.ROAD, true);
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
    public void robPlayer(@Nullable RobPlayerInfo victim) {
        status.robPlayer(this, victim);
    }

    private void setStatus(MapStatus status) {
        this.status = status;
    }

    private enum MapStatus {
        FIRST_SETTLEMENT {
            @Override
            public void doSetup(MapController c) {
                c.startMove(PieceType.SETTLEMENT, false);
            }

            @Override
            public void keepBuilding(MapController c) {
                c.setStatus(FIRST_ROAD);
                c.status.doSetup(c);
            }

            @Override
            public boolean isSetup() {
                return true;
            }
        },

        FIRST_ROAD {
            @Override
            public void doSetup(MapController c) {
                c.startMove(PieceType.ROAD, false);
            }

            @Override
            public void keepBuilding(MapController c) {
                c.setStatus(SECOND_SETTLEMENT);
                this.finishSetupTurn(c, SECOND_SETTLEMENT);
            }

            @Override
            public boolean isSetup() {
                return true;
            }
        },

        SECOND_SETTLEMENT {
            @Override
            public void doSetup(MapController c) {
                c.startMove(PieceType.SETTLEMENT, false);
            }

            @Override
            public void keepBuilding(MapController c) {
                c.setStatus(SECOND_ROAD);
                c.status.doSetup(c);
            }

            @Override
            public boolean isSetup() {
                return true;
            }
        },

        SECOND_ROAD {
            @Override
            public void doSetup(MapController c) {
                c.startMove(PieceType.ROAD, false);
            }

            @Override
            public void keepBuilding(MapController c) {
                c.setStatus(NORMAL);
                this.finishSetupTurn(c, NORMAL);
            }

            @Override
            public boolean isSetup() {
                return true;
            }
        },

        ROAD_BUILDING_FIRST {
            @Override
            public void placeRoad(MapController c, EdgeLocation edgeLoc) {
                c.firstRoadLoc = edgeLoc;
                if (c.getPlayer().getRoads() > 1) {
                    c.setStatus(ROAD_BUILDING_SECOND);
                    c.startMove(PieceType.ROAD);
                } else {
                    finishRoadBuilding(c, c.firstRoadLoc, null);
                }
            }
        },

        ROAD_BUILDING_SECOND {
            @Override
            public void placeRoad(MapController c, EdgeLocation edgeLoc) {
                c.firstRoadLoc = edgeLoc;
                finishRoadBuilding(c, c.firstRoadLoc, edgeLoc);
            }
        },

        ROBBING {
            @Override
            public void startMovingRobber(MapController c) {
            }

            @Override
            public void robPlayer(MapController c, @Nullable RobPlayerInfo victim) {
                c.getAsync().runModelMethod(c.server::robPlayer,
                        new RobPlayerAction(
                                c.newRobberLoc,
                                c.getPlayer().getPlayerIndex(),
                                victim == null ? -1 : victim.getPlayerIndex().index()))
                        .onSuccess(() -> c.setStatus(NORMAL))
                        .onError(e -> {
                            LOGGER.severe("Failed to rob player: " + e.getMessage());
                            c.setStatus(NORMAL);
                        })
                        .start();
            }
        },

        SOLDIER {
            @Override
            public void startMovingRobber(MapController c) {
            }

            @Override
            public void robPlayer(MapController c, @Nullable RobPlayerInfo victim) {
                c.getAsync().runModelMethod(c.server::useSoldier,
                        new SoldierAction(
                                c.newRobberLoc,
                                c.getPlayer().getPlayerIndex(),
                                victim == null ? -1 : victim.getPlayerIndex().index()))
                        .onSuccess(() -> c.setStatus(NORMAL))
                        .onError(e -> {
                            LOGGER.severe("Failed to rob player with soldier: " + e.getMessage());
                            c.setStatus(NORMAL);
                        })
                        .start();

            }
        },

        ROBBING_MOVING {
            @Override
            public void startMovingRobber(MapController c) {
            }

            @Override
            public void startRobbing(MapController c) {
                c.setStatus(ROBBING);
            }
        },

        SOLDIER_MOVING {
            @Override
            public void startMovingRobber(MapController c) {
            }

            @Override
            public void startRobbing(MapController c) {
                c.setStatus(SOLDIER);
            }
        },

        NORMAL;

        @Contract(pure = true)
        public static MapStatus setupSettlements(TurnStatus status) {
            return status == TurnStatus.FIRST_ROUND ? FIRST_SETTLEMENT : SECOND_SETTLEMENT;
        }

        @Contract(pure = true)
        public static MapStatus setupRoads(TurnStatus status) {
            return status == TurnStatus.FIRST_ROUND ? FIRST_ROAD : SECOND_ROAD;
        }

        public boolean isSetup() {
            return false;
        }

        public void startMovingRobber(MapController c) {
            if (c.getFacade().getTurn().getPhase() == TurnStatus.ROBBING &&
                    c.getFacade().getTurn().isPlayersTurn(c.getPlayer())) {
                c.setStatus(ROBBING_MOVING);
                c.startMove(PieceType.ROBBER, false);
            } else {
                c.getRobView().closeOneModal();
            }
        }

        public void startRobbing(MapController c) {

        }

        public void robPlayer(MapController c, @Nullable RobPlayerInfo victim) {
            assert false;
        }

        public void keepBuilding(MapController c) {

        }

        public void doSetup(MapController c) {

        }

        public void placeRoad(MapController c, EdgeLocation edgeLoc) {
            c.getAsync().runModelMethod(c.server::buildRoad,
                    new BuildRoadAction(
                            c.getFacade().getTurn().isSetup(),
                            edgeLoc,
                            c.getPlayer().getPlayerIndex()))
                    .onError(e -> LOGGER.severe("Failed to place road: " + e.getMessage()))
                    .onSuccess(() -> c.status.keepBuilding(c))
                    .start();
        }

        protected void finishRoadBuilding(MapController c, EdgeLocation loc1, EdgeLocation loc2) {
            c.setStatus(NORMAL);
            c.getAsync().runModelMethod(c.server::useRoadBuilding,
                    new RoadBuildingAction(loc2, loc1, c.getPlayer().getPlayerIndex()))
                    .onSuccess(() -> c.firstRoadLoc = null)
                    .onError(e -> LOGGER.severe("Failed to use road building card: " + e.getMessage()))
                    .start();
        }

        protected void finishSetupTurn(MapController c, MapStatus newStatus) {
            c.getAsync().runModelMethod(c.server::finishTurn, new FinishMoveAction(c.getPlayer().getPlayerIndex()))
                    .onSuccess(() -> c.dropping = false)
                    .onError(e -> LOGGER.severe("failed to finish setup turn " + e.getMessage()))
                    .start();
        }
    }
}

