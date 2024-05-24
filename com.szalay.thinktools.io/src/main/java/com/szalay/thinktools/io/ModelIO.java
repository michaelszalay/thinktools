package com.szalay.thinktools.io;

import com.szalay.thinktools.model.CausalModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Model IO contains helper method to load and store {@link CausalModel}s.
 */
public final class ModelIO {
    private ModelIO() {    
    }
    
    /**
     *  Saves a causal model into a file.
     * @param model
     * @param file
     */
    public static void saveModel(final CausalModel model, final File file) throws FileNotFoundException, IOException {
        if (model == null) {
            throw new IllegalArgumentException("Illegal model: null.");
        }
        if (file == null) {
            throw new IllegalArgumentException("Illegal file: null.");
        }
        final FileOutputStream fout = new FileOutputStream(file, false);
        final ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(model);
        out.flush();
        out.close();
    }
    
    /**
     *  Loads a causal model from a file.
     * @param file
     * @return 
     */
    public static CausalModel loadModel(final File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException("Illegal file: null.");
        }
        final FileInputStream fis = new FileInputStream(file);
        final ObjectInputStream in = new ObjectInputStream(fis);
        final CausalModel model = (CausalModel) in.readObject();
        return model;
    }
}
