package org.ketfelgamal.the100.apis.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.IntrospectionException;

import java.io.IOException;


import java.lang.reflect.InvocationTargetException;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.http.client.ClientProtocolException;

public class The100Utils {

    private static JsonNode retrieveThe100OJsonNodes(String the100Token, String fullURL,
                                                     Hashtable<String, String> properties,
                                                     String queryString,
                                                     Class clazz) throws IOException,
                                                                NamingException,
                                                                IllegalAccessException {


        if (properties == null) {
            properties = new Hashtable<>();

        }
        String jsonFile =
            getJsonObject(fullURL, queryString, the100Token,
                          properties);

        System.out.println(jsonFile);

        if (jsonFile != null) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(jsonFile);
        }
        return null;
    }


    public static String getJsonObject(String baseURL, String queryString,
                                       String the100Token,
                                       Hashtable<String, String> properties) throws IOException,
                                                                                    ClientProtocolException,
                                                                                    NamingException{

        return RESTConnector.sendGetRequest(baseURL, queryString, get100TokenProperties(properties, the100Token));
    }


    
    public static Hashtable<String, String> get100TokenProperties(Hashtable<String, String> properties, String the100Token) {
        if(properties == null)
            properties = new Hashtable<String, String>();
        
        if (the100Token == null) {
            throw new RuntimeException("The 100 token cannot be empty or null.");
        }
        
        properties.put("Authorization", "Token token=\"" + the100Token+ "\"");
        
        return properties;
    }

    public static <T> List<T> retrieveThe100Objects(String the100Token,
                                                    String the100SubURL,
                                                    Hashtable<String, String> properties,
                                                    String queryString,
                                                    Class<T> clazz) throws IOException,
                                                                        ClientProtocolException,
                                                                        NamingException,
                                                                        IllegalAccessException,
                                                                        ParseException,
                                                                        IntrospectionException,
                                                                        InvocationTargetException,
                                                                        InstantiationException {

        List<T> theList = new ArrayList<T>();
        JsonNode root =
                retrieveThe100OJsonNodes(the100Token, RESTConnector.THE_100_URL +the100SubURL, properties, queryString,
                                 clazz);
        if (root != null) {
            theList = ConvertJsonTo100Object.convert(root, clazz);
        }
        return theList;
    }


}
