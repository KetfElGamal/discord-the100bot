package org.ketfelgamal.the100.apis.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.beans.IntrospectionException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.ParameterizedType;

import java.text.ParseException;

import java.util.*;

public class ConvertJsonTo100Object {
    public ConvertJsonTo100Object() {
        super();
    }


    public static <T> List<T> convert(JsonNode root,
                                      Class<T> type) throws ParseException,
                                                            IntrospectionException,
                                                            IllegalAccessException,
                                                            InvocationTargetException,
                                                            InstantiationException {
        List<T> crmObjects = new ArrayList<T>();


        Iterator<JsonNode> docsIter = root.elements();

        while (docsIter.hasNext()) {
            JsonNode doc = docsIter.next();
            crmObjects.add(convertOne(doc, type));
        }
        return crmObjects;

    }


    public static <T> T convertOne(JsonNode root,
                                   Class<T> type) throws ParseException,
                                                         IntrospectionException,
                                                         IllegalAccessException,
                                                         InvocationTargetException,
                                                         InstantiationException {
        T crmObject = type.newInstance();

        Hashtable<String, String> fieldsInfo =
            ReflectionUtils.retrieveFieldsCRMFieldsMapping(type);
        Hashtable<String, Class<?>> fieldsTypeInfo =
            ReflectionUtils.retrieveFieldsTypeMapping(type);

        for (Map.Entry<String, String> entry : fieldsInfo.entrySet()) {
            String propertyName = entry.getKey();
            String crmPropertyName = entry.getValue();
            Class<?> propertyType = fieldsTypeInfo.get(propertyName);

            if (propertyType.getSimpleName().equals("List") ||
                propertyType.getSimpleName().equals("ArrayList")) {

                //Get the generic type
                Field theListField =
                    ReflectionUtils.getfieldByName(propertyName, type);
                ParameterizedType listType =
                    (ParameterizedType)theListField.getGenericType();
                Class<?> theChildType =
                    (Class<?>)listType.getActualTypeArguments()[0];
                List<?> theChildList =
                    ConvertJsonTo100Object.convert(root.path(crmPropertyName),
                                                   theChildType);
                ReflectionUtils.callSetter(crmObject, propertyName,
                                           theChildList);
            } else {
                ReflectionUtils.callSetter(crmObject, propertyName,
                                           JSONUtils.evaluateJsonNodeValue(root,
                                                                           crmPropertyName,
                                                                           propertyType));

            }
        }

        return crmObject;

    }
}
