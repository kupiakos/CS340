package client.server;

import client.game.GameManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.naming.CommunicationException;
import java.io.*;
import java.net.*;
import java.util.List;

/**
 * Created by elijahgk on 9/12/2016.
 * ClientCommunicator receives calls from ServerProxy and submits those calls as HTTP requests to the Catan Server.
 * Package Private
 */
class ClientCommunicator implements IClientCommunicator {

    private static ClientCommunicator SINGLETON = null;
    private String URLPrefix;
    private String cookie;
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();

    private ClientCommunicator() {
        URLPrefix = "http://localhost:8081";
        cookie = "";
    }


    /**
     * Returns the {@link ClientCommunicator SINGLETON}.
     *
     * @return {@link ClientCommunicator} SINGLETON.
     */
    public static ClientCommunicator getSingleton() {
        if (SINGLETON == null) {
            SINGLETON = new ClientCommunicator();
            return SINGLETON;
        }
        return SINGLETON;
    }

    /**
     * Method that will communicate with the server.
     *
     * @param URLSuffix     Refers to which server command is being sent to the server, may not be null.
     * @param requestBody   This is the request body which is required by the server command; may be null.
     * @param requestMethod GET OR POST
     * @return Any information pertinent to the client. Or an error message if not a 200 response code.
     * @pre A can-do method has already been called to make sure that the requested command will work.  A valid URL suffix
     * @post Requested action has been performed and the appropriate information has been returned as Json.
     */
    public String sendHTTPRequest(String URLSuffix, String requestBody, String requestMethod) throws IllegalArgumentException, javax.naming.CommunicationException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(URLPrefix + URLSuffix);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            if (msCookieManager.getCookieStore().getCookies().size() > 0) {
                String cookiesToAdd ="";
                for(HttpCookie c:msCookieManager.getCookieStore().getCookies()){
                    cookiesToAdd += c.toString();
                    if(c!=msCookieManager.getCookieStore().getCookies().get(msCookieManager.getCookieStore().getCookies().size()-1))
                        cookiesToAdd += ';';
                }
                connection.setRequestProperty("Cookie",cookiesToAdd);
            }
            connection.setDoOutput(true);
            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(requestBody);
            output.close();
            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            switch (responseCode) {
                case 200:
                    InputStream input = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(input));
                    String line;
                    if (connection.getHeaderField("Set-cookie") != null) {
                        List<String> cookieHeaders = connection.getHeaderFields().get("Set-cookie");
                        for(String c : cookieHeaders){
                            msCookieManager.getCookieStore().add(null,HttpCookie.parse(c).get(0));
                        }
                        if(URLSuffix == "/user/login"){
                            JsonObject obj = (JsonObject) new JsonParser().parse(URLDecoder.decode(msCookieManager.getCookieStore().getCookies().get(0).getValue(), "UTF-8"));
                            GameManager.getGame().getPlayerInfo().setId(obj.get("playerID").getAsInt());
                        }
                    }
                    while ((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();
                    connection.disconnect();
                    return response.toString();
                case 400:
                    response.append("400 - ");
                    InputStream error = connection.getErrorStream();
                    rd = new BufferedReader(new InputStreamReader(error));
                    line = "";
                    while ((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    throw new IllegalArgumentException(response.toString());
                default:
                    response.append("{\"error\":\"SendHTTPRequest responded with an unhandled error resulting from response code: ").append(responseCode).append("\"");
                    break;
            }
            connection.disconnect();
            return response.toString();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (IOException e) {
            throw new CommunicationException(e.getMessage());
        } finally {
            assert connection != null;
            connection.disconnect();
        }
    }

    public void setURLPrefix(String URLPrefix) {
        this.URLPrefix = URLPrefix;
    }
}
