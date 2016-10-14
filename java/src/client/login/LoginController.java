package client.login;

import client.base.Controller;
import client.base.IAction;
import client.misc.IMessageView;
import client.server.ServerProxy;
import client.utils.ServerAsyncHelper;
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
        Credentials credentials = new Credentials(password,username);
        getAsync().runMethod(server::login,credentials)
                .onError(e->displayLoginError(e))
                .onSuccess(()->{System.out.println("login successful");
                    getLoginView().closeModal();})
                .start();
    }

    @Override
    public void register() {

        // TODO: register new user (which, if successful, also logs them in)

        String username = getLoginView().getRegisterUsername();
        String password = getLoginView().getRegisterPassword();
        Credentials credentials = new Credentials(password,username);
        getAsync().runMethod(server::register,credentials)
                .onError(e->displayRegistrationError(e))
                .onSuccess(()-> {
                    System.out.println("registration successful");
                    login(credentials);
                })
                .start();
    }

    private void login(Credentials credentials){
        getAsync().runMethod(server::login,credentials)
                .onError(e->displayRegistrationError(e))
                .onSuccess(()-> {
                    System.out.println("post-registration login successful");
                    getLoginView().closeModal();})
                .start();
    }

    void displayLoginError(Exception e){
        messageView.setTitle("Login Error");
        if(e.getClass()== CredentialNotFoundException.class){
            messageView.setMessage("USERNAME AND/OR PASSWORD NOT FOUND.");
        }
        else{
            messageView.setMessage("LOGIN ERROR ON SERVER.");
        }
        messageView.showModal();
    }

    void displayRegistrationError(Exception e){
        messageView.setTitle("Registration Error");
        if(e.getClass()== CredentialNotFoundException.class){
            messageView.setMessage("USERNAME AND/OR PASSWORD NOT VALID OR ALREADY REGISTERED.");
        }
        else{
            messageView.setMessage("REGISTRATION ERROR ON SERVER.");
        }
        messageView.showModal();
    }

}

