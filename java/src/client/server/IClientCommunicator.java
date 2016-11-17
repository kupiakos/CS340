package client.server;

import javax.naming.CommunicationException;
import javax.security.auth.login.CredentialNotFoundException;
import java.util.Map;

/**
 * Created by elija on 9/29/2016.
 */
public interface IClientCommunicator {

    String sendHTTPRequest(String URLSuffix, String requestBody, String requestMethod, Map<String, String> parameters) throws IllegalArgumentException, CommunicationException, CredentialNotFoundException;
}
