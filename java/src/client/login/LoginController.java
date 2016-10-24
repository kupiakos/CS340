package client.login;

import client.base.Controller;
import client.base.IAction;
import client.misc.IMessageView;
import client.server.ServerProxy;
import shared.models.user.Credentials;

import javax.security.auth.login.CredentialNotFoundException;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController {
    private IMessageView messageView;
    private IAction loginAction;

    /**
     * LoginController constructor
     *
     * @param view        Login view
     * @param messageView Message view (used to display error messages that occur during the login process)
     */
    public LoginController(ILoginView view, IMessageView messageView) {

        super(view);
        this.server = new ServerProxy();
        this.messageView = messageView;
    }

    public ILoginView getLoginView() {

        return (ILoginView) super.getView();
    }

    public IMessageView getMessageView() {

        return messageView;
    }

    /**
     * Returns the action to be executed when the user logs in
     *
     * @return The action to be executed when the user logs in
     */
    public IAction getLoginAction() {

        return loginAction;
    }

    /**
     * Sets the action to be executed when the user logs in
     *
     * @param value The action to be executed when the user logs in
     */
    public void setLoginAction(IAction value) {

        loginAction = value;
    }

    @Override
    public void start() {
        getLoginView().showModal();
    }

    @Override
    public void signIn() {
        // TODO: log in user
        String username = getLoginView().getLoginUsername();
        String password = getLoginView().getLoginPassword();
        if (!validateUsername(username) || !validatePassword(password)) {
            displayInvalidTextError();
            return;
        }
        Credentials credentials = new Credentials(password, username);
        getAsync().runMethod(server::login, credentials)
                .onError(e -> displayLoginError(e))
                .onSuccess(() -> {
                    System.out.println("login successful");
                    getLoginView().closeModal();
                    getGameManager().getPlayerInfo().setName(credentials.getUsername());
                    loginAction.execute();
                })
                .start();
    }

    @Override
    public void register() {

        // TODO: register new user (which, if successful, also logs them in)

        String username = getLoginView().getRegisterUsername();
        String password = getLoginView().getRegisterPassword();
        if (!validateUsername(username) || !validatePassword(password)) {
            displayInvalidTextError();
            return;
        }
        Credentials credentials = new Credentials(password, username);
        getAsync().runMethod(server::register, credentials)
                .onError(e -> displayRegistrationError(e))
                .onSuccess(() -> {
                    System.out.println("registration successful");
                    login(credentials);
                })
                .start();
    }

    private void login(Credentials credentials) {
        getAsync().runMethod(server::login, credentials)
                .onError(e -> displayRegistrationError(e))
                .onSuccess(() -> {
                    System.out.println("post-registration login successful");
                    getLoginView().closeModal();
                    getGameManager().getPlayerInfo().setName(credentials.getUsername());
                    loginAction.execute();
                })
                .start();
    }

    public boolean validateUsername(String username) {
        final int MIN_UNAME_LENGTH = 3;
        final int MAX_UNAME_LENGTH = 7;
        if (username.length() < MIN_UNAME_LENGTH
                || username.length() > MAX_UNAME_LENGTH) {
            return false;
        } else {
            for (char c : username.toCharArray()) {
                if (!Character.isLetterOrDigit(c)
                        && c != '_' && c != '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validatePassword(String input) {
        final int MIN_PASS_LENGTH = 5;
        if (input.length() < MIN_PASS_LENGTH) {
            return false;
        } else {
            for (char c : input.toCharArray()) {
                if (!Character.isLetterOrDigit(c)
                        && c != '_' && c != '-') {
                    return false;
                }
            }
        }
        return true;
    }


    void displayInvalidTextError() {
        messageView.setTitle("Invalid Credentials Error");
        messageView.setMessage("Either your username or password did not meet credential requirements.\nA username must be between 3 and 7 characters.\nA password must be at least 5 characters and must be composed of alphanumeric characters, underscores or hyphens");
        messageView.showModal();
    }

    void displayLoginError(Exception e) {
        messageView.setTitle("Login Error");
        if (e.getClass() == CredentialNotFoundException.class) {
            messageView.setMessage("USERNAME AND/OR PASSWORD NOT FOUND.");
        } else {
            messageView.setMessage("LOGIN ERROR ON SERVER. ERROR: " + e.getMessage());
        }
        messageView.showModal();
    }

    void displayRegistrationError(Exception e) {
        messageView.setTitle("Registration Error");
        if (e.getClass() == CredentialNotFoundException.class) {
            messageView.setMessage("USERNAME AND/OR PASSWORD NOT VALID OR ALREADY REGISTERED.");
        } else {
            messageView.setMessage("REGISTRATION ERROR ON SERVER.");
        }
        messageView.showModal();
    }

}

