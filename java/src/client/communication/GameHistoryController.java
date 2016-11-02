package client.communication;

import client.base.Controller;
import shared.definitions.CatanColor;
import shared.models.game.ClientModel;
import shared.models.game.MessageList;
import shared.models.game.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController {
    private static final Logger LOGGER = Logger.getLogger(GameHistoryController.class.getSimpleName());

    public GameHistoryController(IGameHistoryView view) {
        super(view);
        observeClientModel();
    }

    @Override
    public IGameHistoryView getView() {
        return (IGameHistoryView) super.getView();
    }

    private void initFromModel() {
        getView().setEntries(new ArrayList<>());
        updateFromModel(getModel());
    }

    @Override
    public void updateFromModel(ClientModel model) {
        MessageList log = model.getLog();

        LOGGER.fine("Updating game history");

        Map<String, CatanColor> colors = model.getPlayers().stream()
                .collect(Collectors.toMap(Player::getName, Player::getColor));

        List<LogEntry> entries = log.getLines().stream()
                .map(e -> new LogEntry(
                        colors.get(e.getSource()),
                        e.getMessage()))
                .collect(Collectors.toList());

        getView().setEntries(entries);
    }

}

