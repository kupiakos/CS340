package client.server;

import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;
import com.sun.jmx.snmp.daemon.CommunicationException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

/**
 * Created by elijahgk on 9/12/2016.
 * ClientCommunicator receives calls from ServerProxy and submits those calls as HTTP requests to the Catan Server.
 * Package Private
 */
class ClientCommunicator {

    private static ClientCommunicator SINGLETON = null;
    private String URLPrefix;

    private ClientCommunicator(){
        URLPrefix = "localhost:8081";
    }



    /**
     * Returns the {@link ClientCommunicator SINGLETON}.
     * @return {@link ClientCommunicator} SINGLETON.
     */
    public static ClientCommunicator getSingleton(){
        if(SINGLETON==null){
            SINGLETON = new ClientCommunicator();
            return SINGLETON;
        }
        return SINGLETON;
    }

    /**
     * Method that will communicate with the server.
     * @pre A can-do method has already been called to make sure that the requested command will work.  A valid URL suffix
     * @post Requested action has been performed and the appropriate information has been returned as Json.
     * @param URLSuffix Refers to which server command is being sent to the server, may not be null.
     * @param requestBody This is the request body which is required by the server command; may be null.
     * @param requestMethod GET OR POST
     * @return Any information pertinent to the client. Or an error message if not a 200 response code.
     */
    public String sendHTTPRequest(String URLSuffix, String requestBody, String requestMethod) throws MalformedURLException, javax.naming.CommunicationException{
        URL url = new URL(URLPrefix+URLSuffix);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(requestMethod);
            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(requestBody);
            output.close();

            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            if(responseCode==200) {
                InputStream input = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(input));
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                connection.disconnect();
                return response.toString();
            }
            else{
                switch (responseCode){
                    case 400:  response.append("{\"error\":\"400 Response Code: Bad URL Request\""); break;
                    default: response.append("{\"error\":\"SendHTTPRequest responded with an unhandled error\""); break;
                }
                connection.disconnect();
                return response.toString();
            }
        } catch (IOException e) {
            throw new CommunicationException(e);
        } finally {
            connection.disconnect();
        }
    }

    public void setURLPrefix(String URLPrefix) {
        this.URLPrefix = URLPrefix;
    }
}
