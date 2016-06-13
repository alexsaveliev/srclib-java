package com.sourcegraph.javagraph;

import java.util.*;

/**
 * NormalizedGraphWriter collects refs and defs, ensures they are unique
 * and splits definitions testing if file they came from belongs to generated sources
 */
class NormalizedGraphWriter implements GraphWriter {

    /**
     * Contains defs from generated sources
     */
    Set<LocalDefKey> generated = new HashSet<>();

    /**
     * Ensures defs are unique
     */
    Set<DefKey> seenDefs = new HashSet<>();

    /**
     * Contains defs from regular sources
     */
    Collection<Def> defs = new LinkedList<>();

    /**
     * Contains all the refs
     */
    Set<Ref> refs = new LinkedHashSet<>();

    private Project project;

    NormalizedGraphWriter(Project project) {
        this.project = project;
    }

    @Override
    public Ref writeRef(Ref ref) {
        if (project.isGenerated(ref.file)) {
            return null;
        }
        return refs.add(ref) ? ref : null;
    }

    @Override
    public Def writeDef(Def def) {
        if (!project.isGenerated(def.file)) {
            if (!seenDefs.add(def.defKey)) {
                return null;
            }
            defs.add(def);
            return def;
        } else {
            LocalDefKey key = new LocalDefKey(def.unitName, def.defKey.getPath());
            return generated.add(key) ? def : null;
        }
    }

    @Override
    public void flush() {
    }

    /**
     * @param ref ref to check
     * @return true if ref points to def from generated source code
     */
    public boolean isGenerated(Ref ref) {

        return generated.contains(new LocalDefKey(ref.defUnit, ref.defKey.getPath()));

    }
}