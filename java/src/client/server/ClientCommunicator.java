package client.server;

import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;

import java.net.MalformedURLException;

/**
 * Created by elijahgk on 9/12/2016.
 * ClientCommunicator receives calls from ServerProxy and submits those calls as HTTP requests to the Catan Server.
 * Package Private
 */
class ClientCommunicator {

    private static ClientCommunicator SINGLETON = null;

    private ClientCommunicator(){}

    public static ClientCommunicator getSingleton(){
        if(SINGLETON==null){
            SINGLETON = new ClientCommunicator();
            return SINGLETON;
        }
        return SINGLETON;
    }

    /**
     * Method that will communicate with the server and serialize the return information into GSON.
     * @pre A can-do method has already been called to make sure that the requested command will work.  A valid URL suffix
     * @post Requested action has been performed and the appropriate information has been returned as GSON.
     * @param URLSuffix Refers to which server command is being sent to the server, may not be null.
     * @param requestBody This is the request body which is required by the server command; may be null.
     * @return Any information pertinent to the client via gson
     */
    public Gson sendHTTPRequest(String URLSuffix, Gson requestBody) throws MalformedURLException, MalformedJsonException{
        return null;
    }
}
