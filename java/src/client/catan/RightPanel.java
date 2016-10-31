package client.catan;

import client.base.IAction;
import client.devcards.BuyDevCardView;
import client.devcards.DevCardController;
import client.devcards.PlayDevCardView;
import client.map.IMapController;
import client.points.GameFinishedView;
import client.points.PointsController;
import client.points.PointsView;
import client.resources.ResourceBarController;
import client.resources.ResourceBarElement;
import client.resources.ResourceBarView;
import org.jetbrains.annotations.NotNull;
import shared.definitions.PieceType;

import javax.swing.*;

@SuppressWarnings("serial")
public class RightPanel extends JPanel {

    private PlayDevCardView playCardView;
    private BuyDevCardView buyCardView;
    private DevCardController devCardController;
    private PointsView pointsView;
    private GameFinishedView finishedView;
    private PointsController pointsController;
    private ResourceBarView resourceView;
    private ResourceBarController resourceController;

    public RightPanel(final IMapController mapController) {

        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        // Initialize development card views and controller
        //
        playCardView = new PlayDevCardView();
        buyCardView = new BuyDevCardView();
        IAction soldierAction = mapController::playSoldierCard;
        IAction roadAction = mapController::playRoadBuildingCard;
        devCardController = new DevCardController(playCardView, buyCardView,
                soldierAction, roadAction);
        playCardView.setController(devCardController);
        buyCardView.setController(devCardController);

        // Initialize victory point view and controller
        //
        pointsView = new PointsView();
        finishedView = new GameFinishedView();
        pointsController = new PointsController(pointsView, finishedView);
        pointsView.setController(pointsController);
        finishedView.setController(pointsController);

        // Initialize resource bar view and controller
        //
        resourceView = new ResourceBarView();
        resourceController = new ResourceBarController(resourceView);
        resourceController.setElementAction(ResourceBarElement.ROAD,
                createStartMoveAction(mapController,
                        PieceType.ROAD));
        resourceController.setElementAction(ResourceBarElement.SETTLEMENT,
                createStartMoveAction(mapController,
                        PieceType.SETTLEMENT));
        resourceController.setElementAction(ResourceBarElement.CITY,
                createStartMoveAction(mapController,
                        PieceType.CITY));
        resourceController.setElementAction(ResourceBarElement.BUY_CARD,
                devCardController::startBuyCard);
        resourceController.setElementAction(ResourceBarElement.PLAY_CARD,
                devCardController::startPlayCard);
        resourceView.setController(resourceController);

        this.add(pointsView);
        this.add(resourceView);
    }

    @NotNull
    private IAction createStartMoveAction(final IMapController mapController,
                                          final PieceType pieceType) {

        return () -> mapController.startMove(pieceType);
    }

}

