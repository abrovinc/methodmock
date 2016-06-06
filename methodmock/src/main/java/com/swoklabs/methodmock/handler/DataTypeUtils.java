package com.swoklabs.methodmock.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by orjan on 2016-06-06.
 */
public final class DataTypeUtils {

    private final static Map<String, String> primitiveMapping = new HashMap<String, String>();

    static {
        primitiveMapping.put("int", "java.lang.Integer");
        primitiveMapping.put("long", "java.lang.Long");
        primitiveMapping.put("double", "java.lang.Double");
        primitiveMapping.put("float", "java.lang.Float");
        primitiveMapping.put("boolean", "java.lang.Boolean");
        primitiveMapping.put("byte", "java.lang.Byte");
        primitiveMapping.put("char", "java.lang.Character");
        primitiveMapping.put("short", "java.lang.Short");
    }

    private DataTypeUtils() {
        // Hide utility class constructor
    }

    public static boolean isPrimitive(Class returnType, Class mockResponse) {
        final String mappedValue = primitiveMapping.get(returnType.getName());
        final boolean isPrimitive = mockResponse.getName().equalsIgnoreCase(mappedValue);
        return isPrimitive;
    }
}
