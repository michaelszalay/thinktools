package com.szalay.thinktools.io;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

import com.szalay.thinktools.model.DataModel;
import com.szalay.thinktools.model.DataProperty;
import com.szalay.thinktools.model.Individual;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.xml.crypto.Data;

/**
 * DataModelIO contains helper method to load and store {@link DataModel}s.
 */
public final class DataModelIO {
    private DataModelIO() {
    }
    
     /**
     *  Saves a causal model into a file.
     * @param model
     * @param file
     */
    public static void saveModel(final DataModel model, final File file) throws FileNotFoundException, IOException {
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
    public static DataModel loadModel(final File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException("Illegal file: null.");
        }
        final FileInputStream fis = new FileInputStream(file);
        final ObjectInputStream in = new ObjectInputStream(fis);
        final DataModel model = (DataModel) in.readObject();
        return model;
    }

    public static DataModel importFromCSV(final File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("Illegal file: null.");
        }

        final Reader in = new FileReader(file);
        final Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
        if (!records.iterator().hasNext()) {
            //Empty...
            return new DataModel();
        }

        final Iterator<CSVRecord> iterator = records.iterator();
        final CSVRecord first = iterator.next();
        final Iterator<String> columnNames = first.iterator();
        final List<String> columnNamesSet = new ArrayList<String>();
        columnNames.forEachRemaining(new Consumer<String>() {
            @Override
            public void accept(String s) {
                columnNamesSet.add(s);
            }
        });

        final DataModel dataModel = new DataModel();
        while (iterator.hasNext()) {
            final Iterator<String> values = iterator.next().iterator();
            final Individual e = new Individual();

            int valueIndex = 0;
            while (values.hasNext()) {
                final DataProperty p = new DataProperty(columnNamesSet.get(valueIndex), values.next());
                e.add(p);
                valueIndex++;
            }

            dataModel.add(e);
        }

        return dataModel;
    }
}
