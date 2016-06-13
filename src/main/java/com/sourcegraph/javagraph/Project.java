package com.sourcegraph.javagraph;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

/**
 * Project is a set of rules per source unit that define the way srclib-java compiles java files to produce source
 * code graph. Shortly, it is the way to pass arguments to javac
 */
public interface Project {

    /**
     * Default source code version (language level)
     */
    String DEFAULT_SOURCE_CODE_VERSION = "1.8";

    /**
     * @return list of classpath elements to be passed to javac's -classpath.
     * Each element should denote existing file or directory, either absolute or relative to source unit directory.
     * Null or empty list indicate absence of classpath to use
     * @throws Exception
     */
    Collection<String> getClassPath() throws Exception;

    /**
     * @return list of bootstrap classpath elements to be passed to javac -Xbootclasspath command line argument.
     * Each element should denote existing file or directory, either absolute or relative to source unit directory.
     * Null instructs to use system property "sun.boot.class.path", empty list instructs to disable bootstrap class path
     * (for example, when you are compiling JDK source code)
     * @throws Exception
     */
    Collection<String> getBootClassPath() throws Exception;

    /**
     * @return list of directories where javac should look for java files (matches javac's -sourcepath). Each element
     * should denote existing directory, either absolute or relative to source unit directory.
     * @throws Exception
     */
    Collection<String> getSourcePath() throws Exception;

    /**
     * Translates path to JAR file to raw dependency if possible
     * @param jarFile location of JAR file
     * @return raw dependency matching given JAR file if possible to identify or null
     * @throws Exception
     */
    RawDependency getDepForJAR(Path jarFile) throws Exception;

    /**
     *
     * @return source code version (Java language level), matches javac's -source command line argument. Null or empty
     * source code version is not taken into account
     * @throws Exception
     */
    String getSourceCodeVersion() throws Exception;

    /**
     *
     * @return source code encoding, matches javac's -encoding command line argument. Null or empty
     * source code encoding is not taken into account and platform-dependent encoding is used
     * @throws Exception
     */
    String getSourceCodeEncoding() throws Exception;

    /**
     * @param file file to check
     * @return true if file was generated at the build step and defs from/refs to this file should not be emitted
     */
    public boolean isGenerated(String file);
}
