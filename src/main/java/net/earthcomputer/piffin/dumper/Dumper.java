package net.earthcomputer.piffin.dumper;

import blue.endless.jankson.JsonObject;

public interface Dumper<T> {

    void dump(T thing, JsonObject output);

    String getName();

}
