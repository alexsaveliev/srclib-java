package com.sourcegraph.javagraph;


import java.util.LinkedList;
import java.util.List;

/**
 * SourceUnit represents a source unit expected by srclib. A source unit is a
 * build-system- and language-independent abstraction of a Maven repository or
 * Gradle project.
 */
public class SourceUnit extends Key {

    /**
     * Default source unit type
     */
    public static final String DEFAULT_TYPE = "JavaArtifact";

    /**
     * List of files that produce source units
     */
    List<String> Files;


    /**
     * Globs is a list of patterns that match files that make up this source
     * unit. It is used to detect when the source unit definition is out of date
     * (e.g., when a file matches the glob but is not in the Files list).
     */
    List<String> Globs;

    /**
     * Source unit directory
     */
    String Dir;

    /**
     * Source unit dependencies
     */
    List<Key> Dependencies = new LinkedList<>();

    /**
     * Source unit data
     */
    SourceUnitData Data = new SourceUnitData();

    private Project project;

    public SourceUnit() {

    }

    /**
     * @return project (aka compiler settings) based on source unit data
     */
    public Project getProject() {
        if (project == null) {
            if (MavenProject.is(this)) {
                project = new MavenProject(this);
            } else if (GradleProject.is(this)) {
                project = new GradleProject(this);
            } else if (AntProject.is(this)) {
                project = new AntProject(this);
            } else if (AndroidSDKProject.is(this)) {
                project = new AndroidSDKProject(this);
            } else if (AndroidSupportProject.is(this)) {
                project = new AndroidSupportProject(this);
            } else if (AndroidCoreProject.is(this)) {
                project = new AndroidCoreProject(this);
            } else if (JDKProject.is(this)) {
                project = new JDKProject(this);
            } else {
                project = new GenericProject(this);
            }
        }
        return project;
    }
}
