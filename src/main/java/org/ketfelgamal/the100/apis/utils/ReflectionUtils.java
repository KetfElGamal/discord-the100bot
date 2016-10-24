package org.ketfelgamal.the100.apis.utils;


import org.ketfelgamal.the100.apis.dto.common.The100Field;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;


public class ReflectionUtils {
    public ReflectionUtils() {
        super();
    }

    public static Object callGetter(Object obj,
                                    String propertyName) throws IntrospectionException,
                                                                IllegalAccessException,
                                                                InvocationTargetException {

        for (PropertyDescriptor pd :
             Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors()) {
            if (pd.getReadMethod() != null &&
                propertyName.equals(pd.getName()))
                return pd.getReadMethod().invoke(obj);
        }
        return null;

    }


    public static void callSetter(Object obj, String propertyName,
                                  Object propertyValue) throws IntrospectionException,
                                                               IllegalAccessException,
                                                               InvocationTargetException {

        for (PropertyDescriptor pd :
             Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors()) {
            if (pd.getReadMethod() != null &&
                propertyName.equals(pd.getName())) {
                pd.getWriteMethod().invoke(obj, propertyValue);
                break;
            }
        }
    }

    public static Hashtable<String, String> retrieveFieldsCRMFieldsMapping(Class<?> clazz) {
        Hashtable<String, String> fieldsMapping =
            new Hashtable<String, String>();

        for (Field field : clazz.getDeclaredFields()) {

            // if method is annotated with @The100Field
            if (field.isAnnotationPresent(The100Field.class)) {
                String fieldName = field.getName();
                Annotation annotation = field.getAnnotation(The100Field.class);
                The100Field fieldInfo = (The100Field)annotation;

                // if enabled = true (default)
                if (fieldInfo.name() != null) {
                    fieldsMapping.put(fieldName, fieldInfo.name());
                }

            }

        }
        return fieldsMapping;
    }

    private static String getCRMFieldNameFromField(Field field) {
        String result = null;
        if (field.isAnnotationPresent(The100Field.class)) {
            The100Field fieldInfo = field.getAnnotation(The100Field.class);
            result = fieldInfo.name();
        }
        return result;
    }

    /**
     * @param clazz
     * @return
     */
    public static List<Field> retrieveAllFields(Class<?> clazz) {
        return Arrays.asList(clazz.getDeclaredFields());
    }

    public static Field getfieldByName(String name, Class<?> clazz) {
        for (Field f : retrieveAllFields(clazz)) {
            if (f.getName().equals(name))
                return f;
        }
        return null;
    }
    
    public static Class<?> getParameterizedInfo(String name, Class<?> clazz) {
        Field f = getfieldByName(name, clazz);
        ParameterizedType listType = (ParameterizedType) f.getGenericType();
        Class<?> theChildType = (Class<?>) listType.getActualTypeArguments()[0];
        return theChildType;
    }

    public static Hashtable<String, Object> retrieveAllNonNullFieldsWithValue(Object obj) throws IntrospectionException,
                                                                                                 IllegalAccessException,
                                                                                                 InvocationTargetException {
        Class<?> type = obj.getClass();
        Hashtable<String, Object> theReturnedList =
            new Hashtable<String, Object>();
        List<Field> fields = retrieveAllFields(type);

        for (Field f : fields) {
            Object propertyValue = callGetter(obj, f.getName());
            if (propertyValue != null) {
                if (propertyValue.getClass().getSimpleName().equals("Date")) {
                    theReturnedList.put(getCRMFieldNameFromField(f),
                                        DateUtils.format((Date)propertyValue));
                } else if(propertyValue.getClass().getSimpleName().equals("List") || 
                          propertyValue.getClass().getSimpleName().equals("ArrayList")){
                       continue;
                }else {
                    String fieldName = getCRMFieldNameFromField(f);
                    if(fieldName != null){
                        theReturnedList.put(fieldName,
                                            propertyValue);
                    }
                }
            }
        }
        return theReturnedList;
    }

    public static Hashtable<String, Class<?>> retrieveFieldsTypeMapping(Class<?> clazz) {
        Hashtable<String, Class<?>> fieldsMapping =
            new Hashtable<String, Class<?>>();

        for (Field field : clazz.getDeclaredFields()) {

            // if method is annotated with @The100Field
            if (field.isAnnotationPresent(The100Field.class)) {
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();
                // if enabled = true (default)
                fieldsMapping.put(fieldName, fieldType);

            }

        }
        return fieldsMapping;
    }
}
