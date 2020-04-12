package net.earthcomputer.piffin.dumper;

public interface TopLevelDumper<T> extends Dumper<T> {

    T getTopLevelThing();

}
