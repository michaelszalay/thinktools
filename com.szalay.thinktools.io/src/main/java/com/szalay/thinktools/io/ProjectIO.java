package com.szalay.thinktools.io;

import com.szalay.thinktools.model.Project;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Utility to load / save projects.
 */
public final class ProjectIO {
    private ProjectIO() {
    }
    
    /**
     *  Saves a causal model into a file.
     * @param model
     * @param file
     */
    public static void saveProject(final Project project, final File file) throws FileNotFoundException, IOException {
        if (project == null) {
            throw new IllegalArgumentException("Illegal project: null.");
        }
        if (file == null) {
            throw new IllegalArgumentException("Illegal file: null.");
        }
        final FileOutputStream fout = new FileOutputStream(file, false);
        final ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(project);
        out.flush();
        out.close();
    }
    
    /**
     *  Loads project from a file.
     * @param file
     * @return 
     */
    public static Project loadProject(final File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException("Illegal file: null.");
        }
        final FileInputStream fis = new FileInputStream(file);
        final ObjectInputStream in = new ObjectInputStream(fis);
        final Project project = (Project) in.readObject();
        return project;
    }
}
