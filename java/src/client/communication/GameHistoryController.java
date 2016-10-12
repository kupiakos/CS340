package client.communication;

import client.base.Controller;
import client.game.GameManager;
import shared.definitions.CatanColor;
import shared.models.game.MessageEntry;
import shared.models.game.MessageList;
import shared.models.game.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;



/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController, Observer {

    public GameHistoryController(IGameHistoryView view) {
        super(view);
        initFromModel();
        GameManager.getGame().addObserver(this);

    }

    @Override
    public IGameHistoryView getView() {

        return (IGameHistoryView) super.getView();
    }

    private void initFromModel() {
         List<LogEntry> entries = new ArrayList<LogEntry>();
         entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));

        getView().setEntries(entries);
        update(GameManager.getGame(), null);
    }

    @Override
    public void update(Observable o, Object arg) {
        MessageList log = GameManager.getGame().getClientModel().getLog();

        ArrayList<LogEntry> entries = new ArrayList<>();
        if (log != null) {
            for (MessageEntry logMember : log.getLines()) {
                String m = logMember.getMessage();
                String s = logMember.getSource();

                CatanColor messageColor = null;
                for (Player player : GameManager.getGame().getClientModel().getPlayers()) {
                    if (player.getName().equals(s)) {
                        messageColor = player.getColor();
                    }
                }
                entries.add(new LogEntry(messageColor, m));
            }
        }
        getView().setEntries(entries);
    }

}

