package com.szalay.thinktools.io;

import com.szalay.thinktools.model.DataModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Contains tests for the ModelIO class.
 */
public class DataModelIOTest {
    @Test
    public void testLoadAndSave() throws FileNotFoundException, IOException, ClassNotFoundException {
        final DataModel model = new DataModel();
        DataModelIO.saveModel(model, new File("target/dataModelTest.out"));
        final DataModel reloaded = DataModelIO.loadModel(new File("target/dataModelTest.out"));
        assertEquals(reloaded.toString(), model.toString());
    }

    @Test
    public void testImportCSV() throws Exception {
        DataModelIO.importFromCSV(new File("src/test/resources/username.csv"));
    }
}
