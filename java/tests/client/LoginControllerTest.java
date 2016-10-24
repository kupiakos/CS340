package client;

import client.game.GameManager;
import client.game.MockGM;
import client.login.ILoginView;
import client.login.LoginController;
import client.misc.IMessageView;
import client.utils.MockServerAsyncHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import shared.IServer;
import shared.models.user.Credentials;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by elija on 10/23/2016.
 */
public class LoginControllerTest {
    private LoginController controller;
    private GameManager mockManager;
    private ILoginView mockLoginView;
    private IMessageView mockMessageView;
    private IServer mockProxy;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Spy
    private MockServerAsyncHelper async;

    @Before
    public void initialize() {
        mockLoginView = mock(ILoginView.class);
        mockMessageView = mock(IMessageView.class);
        mockManager = new MockGM();
        mockProxy = mock(IServer.class);
        controller = new LoginController(mockLoginView,mockMessageView);
        controller.setServer(mockProxy);
        mockManager.setAsync(async);
        controller.setGameManager(mockManager);

    }

    @Test
    public void testValidators(){
        when(mockLoginView.getRegisterUsername()).thenReturn("Batman");
        when(mockLoginView.getRegisterPassword()).thenReturn("I AM");
        assertTrue(controller.validateUsername("Batman"));
        assertFalse(controller.validateUsername("I AM"));
        controller.register();
        verify(mockLoginView, atMost(1)).getRegisterUsername();
        verify(mockLoginView, atMost(1)).getRegisterPassword();
        verify(mockMessageView).showModal();
    }

    @Test
    public void testRegistration(){
        Credentials credentials1 = new Credentials("1234567", "Jimbo");
        when(mockLoginView.getRegisterUsername()).thenReturn("Jimbo");
        when(mockLoginView.getRegisterPassword()).thenReturn("1234567");
        assertTrue(controller.validateUsername(credentials1.getUsername()));
        assertTrue(controller.validatePassword(credentials1.getPassword()));
        controller.register();
        verify(async).runMethod(controller.getServer()::register, credentials1);
    }
}
