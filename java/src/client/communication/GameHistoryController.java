package client.communication;

import client.base.Controller;
import shared.definitions.CatanColor;
import shared.models.game.ClientModel;
import shared.models.game.MessageEntry;
import shared.models.game.MessageList;
import shared.models.game.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


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
        List<LogEntry> entries = new ArrayList<LogEntry>();
        entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));

        getView().setEntries(entries);
        updateFromModel(getModel());
    }

    @Override
    public void updateFromModel(ClientModel model) {
        MessageList log = model.getLog();

        ArrayList<LogEntry> entries = new ArrayList<>();
        LOGGER.fine("Updating game history");

        for (MessageEntry logMember : log.getLines()) {
            String m = logMember.getMessage();
            String s = logMember.getSource();

            CatanColor messageColor = null;
            for (Player player : getModel().getPlayers()) {
                if (player.getName().equals(s)) {
                    messageColor = player.getColor();
                }
            }
            entries.add(new LogEntry(messageColor, m));
        }
        getView().setEntries(entries);
    }

}

