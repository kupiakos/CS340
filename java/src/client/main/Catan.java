package client.main;

import client.catan.CatanPanel;
import client.game.GameManager;
import client.join.*;
import client.login.LoginController;
import client.login.LoginView;
import client.misc.MessageView;

import javax.swing.*;

/**
 * Main entry point for the Catan program
 */
@SuppressWarnings("serial")
public class Catan extends JFrame {

    private CatanPanel catanPanel;

    public Catan() {

        client.base.OverlayView.setWindow(this);

        this.setTitle("Settlers of Catan");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        catanPanel = new CatanPanel();
        this.setContentPane(catanPanel);

        display();
    }

    public static void main(final String[] args) {
        if (args.length == 2) {
            GameManager.getGame().setHostPort(args[0], args[1]);
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new Catan();

            PlayerWaitingView playerWaitingView = new PlayerWaitingView();
            final PlayerWaitingController playerWaitingController = new PlayerWaitingController(
                    playerWaitingView);
            playerWaitingView.setController(playerWaitingController);

            JoinGameView joinView = new JoinGameView();
            NewGameView newGameView = new NewGameView();
            SelectColorView selectColorView = new SelectColorView();
            MessageView joinMessageView = new MessageView();
            final JoinGameController joinController = new JoinGameController(
                    joinView,
                    newGameView,
                    selectColorView,
                    joinMessageView);
            joinController.setJoinAction(playerWaitingController::start);
            joinView.setController(joinController);
            newGameView.setController(joinController);
            selectColorView.setController(joinController);
            joinMessageView.setController(joinController);

            LoginView loginView = new LoginView();
            MessageView loginMessageView = new MessageView();
            LoginController loginController = new LoginController(
                    loginView,
                    loginMessageView);
            loginController.setLoginAction(joinController::start);
            loginView.setController(loginController);
            loginView.setController(loginController);
            loginController.start();
        });
    }

    //
    // Main
    //

    private void display() {
        pack();
        setVisible(true);
    }

}

