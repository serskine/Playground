package com.soupthatisthick.playground.util.json;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.soupthatisthick.playground.util.text.Text;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

@SuppressWarnings("WeakerAccess")
public class JsonUtil {

    private static final JsonObjectMapper includeAllMapper;
    private static final JsonObjectMapper nonNullMapper;

    static {
        nonNullMapper = JsonObjectMapper.getInstance(true, false);
        includeAllMapper = JsonObjectMapper.getInstance(false, false);
    }

    private JsonUtil() {

    }

    public static String prettyPrintUglyJsonString(String uglyJsonString) {
        if (Text.isNullOrEmpty(uglyJsonString)) {
            return "";
        }
        Object jsonObject = toObject(uglyJsonString, Object.class);
        return toJson(jsonObject, true, true);
    }

    public static String toJson(Object object) { return toJson(object, false);}

    public static String toJson(Object object, boolean prettyPrint) {
        return toJson(object, prettyPrint, false);
    }

    public static String toJson(Object object, boolean prettyPrint, boolean includeNullValues) {
        if (includeNullValues) {
            return includeAllMapper.toJson(object, prettyPrint);
        }
        return nonNullMapper.toJson(object, prettyPrint);
    }

    public static <T> T toObject(String json, Class<T> objectType) {
        return includeAllMapper.toObject(json, objectType);
    }

    public static <T extends Collection, E> T toObject(String json, Class<T> objectType, Class<E> genericType) {
        try {
            JavaType collectionType = includeAllMapper.getTypeFactory().constructCollectionType(objectType, genericType);
            return includeAllMapper.readValue(json, collectionType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode toJsonNode(Object object) {
        if (object == null) {
            return toJsonNodeFromJsonString(null);
        } else if (object instanceof Serializable) {
            return toJsonNodeFromJsonString(toJson(object));
        } else {
            return toJsonNodeFromJsonString(toJson(object.toString()));
        }
    }

    public static JsonNode toJsonNodeFromJsonString(String value) {
        try {
            if (value==null) {
                return null;
            } else {
                return includeAllMapper.readTree(value);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

}