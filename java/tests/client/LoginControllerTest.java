package client;

import client.game.GameManager;
import client.login.ILoginView;
import client.login.LoginController;
import client.misc.IMessageView;
import client.utils.ServerAsyncHelper;
import org.mockito.Mockito;
import shared.IServer;
import shared.models.user.Credentials;

import javax.security.auth.login.CredentialNotFoundException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by elija on 10/23/2016.
 */
public class LoginControllerTest {
    private LoginController controller;
    private ServerAsyncHelper mockAsync;
    private GameManager mockManager;
    private ILoginView mockLoginView;
    private IMessageView mockMessageView;

    @org.junit.Before
    public void initialize() {
        mockLoginView = mock(ILoginView.class);
        mockMessageView = mock(IMessageView.class);
        mockManager = mock(GameManager.class);
        controller = new LoginController(mockLoginView,mockMessageView);
        mockAsync = mock(ServerAsyncHelper.class);
        mockManager.setAsync(mockAsync);
        controller.setGameManager(mockManager);
    }

    @org.junit.Test
    public void registerTest() throws Exception{
        Credentials credentials1 = new Credentials("1234567", "Jimbo");
        Credentials credentials2 = new Credentials("I AM", "Batman");
        when(mockLoginView.getRegisterUsername()).thenReturn("Batman");
        when(mockLoginView.getRegisterPassword()).thenReturn("I AM");
        assertTrue(controller.validateUsername("Batman"));
        assertFalse(controller.validateUsername("I AM"));
        controller.register();
        verify(mockLoginView, atMost(1)).getRegisterUsername();
        verify(mockLoginView, atMost(1)).getRegisterPassword();
        verify(mockAsync, never()).runMethod(controller.getServer()::register,credentials2);
        verify(mockMessageView).showModal();
        reset(mockLoginView,mockMessageView);
        when(mockLoginView.getRegisterUsername()).thenReturn("Jimbo");
        when(mockLoginView.getRegisterPassword()).thenReturn("1234567");
        when(mockManager.getAsync()).thenReturn(mockAsync);
        controller.register();
        verify(mockAsync).runMethod(controller.getServer()::register,credentials1);

    }
}