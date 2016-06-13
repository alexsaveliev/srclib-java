package com.sourcegraph.javagraph;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Checks if path is a subdirectory of any of the directories specified
 */
class SubDirectoryFileFilter {

    private Collection<Path> paths;

    /**
     * @param dirs list of root directories
     */
    SubDirectoryFileFilter(Collection<String> dirs) {
        paths = new LinkedList<>();
        if (dirs == null) {
            return;
        }
        for (String dir : dirs) {
            paths.add(PathUtil.CWD.resolve(dir));
        }         
    }

    /**
     * @param path path to check
     * @return true if path is a subdirectory of any of the directories specified
     */
    boolean matches(String path) {
        if (path == null) {
            return false;
        }
        Path p = PathUtil.CWD.resolve(path);
        for (Path dir : paths) {
            if (p.startsWith(dir)) {
                return true;
            }
        }
        return false;
    }
}