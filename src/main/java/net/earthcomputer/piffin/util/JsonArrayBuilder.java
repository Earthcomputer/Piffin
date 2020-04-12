package net.earthcomputer.piffin.util;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonPrimitive;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class JsonArrayBuilder {

    private JsonArray array = new JsonArray();

    private JsonArrayBuilder() {
    }

    public static JsonArrayBuilder create() {
        return new JsonArrayBuilder();
    }

    public JsonArrayBuilder add(JsonElement value) {
        array.add(value);
        return this;
    }

    public JsonArrayBuilder add(JsonObjectBuilder value) {
        return add(value.build());
    }

    public JsonArrayBuilder add(JsonArrayBuilder value) {
        return add(value.build());
    }

    public JsonArrayBuilder addString(String value) {
        return add(new JsonPrimitive(value));
    }

    public JsonArrayBuilder addBoolean(boolean value) {
        return add(new JsonPrimitive(value));
    }

    public JsonArrayBuilder addInt(int value) {
        return add(new JsonPrimitive(value));
    }

    public JsonArrayBuilder addLong(long value) {
        return add(new JsonPrimitive(value));
    }

    public JsonArrayBuilder addFloat(float value) {
        return add(new JsonPrimitive(value));
    }

    public JsonArrayBuilder addDouble(double value) {
        return add(new JsonPrimitive(value));
    }

    public JsonArrayBuilder addAll(Stream<JsonElement> elements) {
        elements.forEach(this::add);
        return this;
    }

    public JsonArrayBuilder addObjects(Stream<JsonObjectBuilder> objects) {
        return addAll(objects.map(JsonObjectBuilder::build));
    }

    public JsonArrayBuilder addArrays(Stream<JsonArrayBuilder> arrays) {
        return addAll(arrays.map(JsonArrayBuilder::build));
    }

    public JsonArrayBuilder addStrings(Stream<String> strings) {
        return addAll(strings.map(JsonPrimitive::new));
    }

    public JsonArrayBuilder addAll(IntStream ints) {
        return addAll(ints.mapToObj(JsonPrimitive::new));
    }

    public JsonArrayBuilder addAll(LongStream longs) {
        return addAll(longs.mapToObj(JsonPrimitive::new));
    }

    public JsonArrayBuilder addAll(DoubleStream doubles) {
        return addAll(doubles.mapToObj(JsonPrimitive::new));
    }

    public JsonArray build() {
        return array;
    }

}
