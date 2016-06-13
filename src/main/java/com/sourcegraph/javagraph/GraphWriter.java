package com.sourcegraph.javagraph;

/**
 * This interface is responsible for collecting and writing references and definitions produced by grapher
 */
public interface GraphWriter {

    /**
     * Writes reference
     * @param r reference to write
     * @return ref written (may be null if this ref is already written)
     */
    Ref writeRef(Ref r);

    /**
     * Writes definition
     * @param def definition to write
     * @return def written (may be null if this def is already written)
     */
    Def writeDef(Def def);

    /**
     * Flush underlying streams
     */
    void flush();
}
