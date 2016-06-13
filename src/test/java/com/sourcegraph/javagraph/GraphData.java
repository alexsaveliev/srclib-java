package com.sourcegraph.javagraph;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of graph writer that collects references and definitions and then writes them as JSON
 */
class GraphData implements GraphWriter {

    final List<Ref> refs = new LinkedList<>();
    final List<Def> defs = new LinkedList<>();

    final Map<DefKey, Def> keyToSymbol = new HashMap<DefKey, Def>();

    /**
     * @param defKey definition key
     * @return list of references to given definition key
     */
    List<Ref> refsTo(DefKey defKey) {
        List<Ref> refs = new LinkedList<>();
        for (Ref r : this.refs) {
            boolean exactMatch = r.defKey.equals(defKey);
            boolean fuzzyMatch = defKey.getPath().equals(r.defKey.getPath()) && (
                    (defKey.getOrigin() == null &&
                            (r.defKey.getOrigin() == null || r.defKey.getOrigin().getScheme().equals("string"))) ||
                            (defKey.getOrigin() != null && defKey.getOrigin().getPath().equals("ANY"))
            );
            if (exactMatch || fuzzyMatch) {
                refs.add(r);
            }
        }
        return refs;
    }

    @Override
    public Ref writeRef(Ref r) {
        refs.add(r);
        return r;
    }

    @Override
    public Def writeDef(Def def) {
        defs.add(def);
        keyToSymbol.put(def.defKey, def);
        return def;
    }

    @Override
    public void flush() {
    }

    public Def getSymbolFromKey(DefKey defKey) {
        return keyToSymbol.get(defKey);
    }

}
