package client.server;

/**
 * Created by elija on 9/29/2016.
 */
public interface IClientCommunicator {

    String sendHTTPRequest(String URLSuffix, String requestBody, String requestMethod) throws IllegalArgumentException, javax.naming.CommunicationException;
}
