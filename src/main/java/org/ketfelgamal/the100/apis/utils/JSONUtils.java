package org.ketfelgamal.the100.apis.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ClassUtils;
import org.ketfelgamal.the100.apis.dto.common.The100Object;

import java.beans.IntrospectionException;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;

import java.text.ParseException;

import java.util.Hashtable;
import java.util.List;


public class JSONUtils {

    public JSONUtils() {
        super();
    }

    public static <T> T getObjectFromJson(String json,
                                          Class<T> type) throws ParseException,
                                                                IntrospectionException,
                                                                IllegalAccessException,
                                                                InvocationTargetException,
                                                                InstantiationException,
                                                                IOException,
                                                                JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return ConvertJsonTo100Object.convertOne(mapper.readTree(json), type);
    }
    
    public static <T> List<T> getObjectsFromJson(String json,
                                          Class<T> type) throws ParseException,
                                                                IntrospectionException,
                                                                IllegalAccessException,
                                                                InvocationTargetException,
                                                                InstantiationException,
                                                                IOException,
                                                                JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return ConvertJsonTo100Object.convert(mapper.readTree(json), type);
    }

    public static <T> T evaluateJsonNodeValue(JsonNode doc,
                                              String propertyName,
                                              Class<T> type) throws ParseException,
                                                                    IntrospectionException,
                                                                    IllegalAccessException,
                                                                    InvocationTargetException,
                                                                    InstantiationException {
        String typeName = type.getSimpleName();
        boolean is100Object = ClassUtils.isAssignable(type, The100Object.class);
        if (doc.get(propertyName) != null) {
            if (typeName.equals("String")) {
                return type.cast(substituteNullStringWithEmpty(doc.get(propertyName).asText()));
            } else if (typeName.equals("Long")) {
                return type.cast(doc.get(propertyName).asLong());
            } else if (typeName.equals("Double")) {
                return type.cast(doc.get(propertyName).asDouble());
            } else if (typeName.equals("Boolean")) {
                return type.cast(doc.get(propertyName).asBoolean());
            } else if (typeName.equals("Date")) {
                String dateStr =
                    substituteNullStringWithEmpty(doc.get(propertyName).asText());
                if (dateStr != null) {
                    return type.cast(DateUtils.parse(doc.get(propertyName).asText()));
                } else {
                    return null;
                }
            } else {
                if(is100Object)
                    return ConvertJsonTo100Object.convertOne(doc.path(propertyName), type);
                
                return null;
            }
        }
        return null;
    }

    public static JsonNode createJsonFrom100Object(The100Object object) throws IntrospectionException,
                                                                            IllegalAccessException,
                                                                            InvocationTargetException {

        ObjectMapper mapper = new ObjectMapper();
        Hashtable<String, Object> data =
            ReflectionUtils.retrieveAllNonNullFieldsWithValue(object);


        return mapper.valueToTree(data);
    }

    private static String substituteNullStringWithEmpty(String text) {
        if (text != null && text.equalsIgnoreCase("null"))
            return null;
        else
            return text;
    }
}
