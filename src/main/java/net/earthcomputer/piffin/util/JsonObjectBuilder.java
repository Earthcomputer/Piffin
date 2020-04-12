package net.earthcomputer.piffin.util;

import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;

public final class JsonObjectBuilder {

    private JsonObject object = new JsonObject();

    private JsonObjectBuilder() {
    }

    public static JsonObjectBuilder create() {
        return new JsonObjectBuilder();
    }

    public JsonObjectBuilder put(String key, JsonElement value) {
        object.put(key, value);
        return this;
    }

    public JsonObjectBuilder put(String key, JsonObjectBuilder value) {
        return put(key, value.build());
    }

    public JsonObjectBuilder put(String key, JsonArrayBuilder value) {
        return put(key, value.build());
    }

    public JsonObjectBuilder putString(String key, String value) {
        return put(key, new JsonPrimitive(value));
    }

    public JsonObjectBuilder putBoolean(String key, boolean value) {
        return put(key, new JsonPrimitive(value));
    }

    public JsonObjectBuilder putInt(String key, int value) {
        return put(key, new JsonPrimitive(value));
    }

    public JsonObjectBuilder putLong(String key, long value) {
        return put(key, new JsonPrimitive(value));
    }

    public JsonObjectBuilder putFloat(String key, float value) {
        return put(key, new JsonPrimitive(value));
    }

    public JsonObjectBuilder putDouble(String key, double value) {
        return put(key, new JsonPrimitive(value));
    }

    public JsonObject build() {
        return object;
    }

}
