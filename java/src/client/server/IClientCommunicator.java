package client.server;

import javax.naming.CommunicationException;
import javax.security.auth.login.CredentialNotFoundException;

/**
 * Created by elija on 9/29/2016.
 */
public interface IClientCommunicator {

    String sendHTTPRequest(String URLSuffix, String requestBody, String requestMethod) throws IllegalArgumentException, CommunicationException, CredentialNotFoundException;
}
