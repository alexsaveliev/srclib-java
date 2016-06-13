package com.sourcegraph.javagraph;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * LocalDefKey represents key object pointing to local (defined in the same repository) def.
 * We using it to filter refs pointing to generated definitions
 */
class LocalDefKey {

    String path;
    String unit;

    LocalDefKey(String path, String unit) {
        this.path = path;
        this.unit = unit;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(o, this);
    }
}