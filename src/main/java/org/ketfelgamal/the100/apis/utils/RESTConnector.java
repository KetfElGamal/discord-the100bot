package org.ketfelgamal.the100.apis.utils;

import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Amr Gawish on 23-Aug-16.
 */
public class RESTConnector {

    public static final String THE_100_URL = "https://www.the100.io/api/v1/groups/";


    public static String sendGetRequest(String baseUrl, String queryString,
                                        Hashtable<String, String> requestProperties) throws NamingException,
            IOException{
        return sendRequest(baseUrl, queryString, requestProperties, "GET",
                null);
    }

    private static String sendRequest(String baseUrl, String queryString,
                                      Hashtable<String, String> requestProperties,
                                      String requestMethod,
                                      String jsonBody) throws MalformedURLException,
            IOException{

        if(queryString==null)
            queryString="";

        URL url = new URL(baseUrl + queryString);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
            con.addRequestProperty(entry.getKey(), entry.getValue());
        }
        con.setRequestMethod(requestMethod);
        con.setDoInput(true);
        con.setDoOutput(true);


        //Sending input
        if (jsonBody != null && jsonBody.length() > 0) {
            OutputStream os = con.getOutputStream();
            os.write(jsonBody.getBytes());
            os.close();
        }

        con.connect();
        int responseCode = con.getResponseCode();
        if (responseCode/ 100 != 2) {

            if (responseCode/ 100 == 5) {
                StringBuilder response = new StringBuilder();
                BufferedReader brError =
                        new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String line;
                while ((line = brError.readLine()) != null) {
                    response.append(line + "\n");
                }
                brError.close();

            }

            throw new RuntimeException("Coudln't connect, status is: " +
                    con.getResponseCode() + " - " +
                    con.getResponseMessage());

        }

        StringBuilder json = new StringBuilder();
        BufferedReader br =
                new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            json.append(line + "\n");
        }
        br.close();
        con.disconnect();


        return json.toString();
    }

    public static void main(String[] args) throws IOException, NamingException {
        System.out.println(RESTConnector.sendGetRequest(THE_100_URL +"1601/users", null,The100Utils.get100TokenProperties(null,"l6rpaTGcXa4wD49SMFhNfQ")));
    }
}
