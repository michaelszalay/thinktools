package com.szalay.thinktools.io;

import com.szalay.thinktools.model.CausalHypothesis;
import com.szalay.thinktools.model.CausalModel;
import com.szalay.thinktools.model.ComplexCause;
import com.szalay.thinktools.model.Factor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Contains tests for the ModelIO class.
 */
public class ModelIOTest {
    @Test
    public void testLoadAndSave() throws FileNotFoundException, IOException, ClassNotFoundException {
        final CausalModel model = new CausalModel();
        final ComplexCause simpleComplexCause = new ComplexCause();
        final Factor a = new Factor("a", 0, 1);
        final Factor b = new Factor("b", 0, 1);
        simpleComplexCause.add(a);
        simpleComplexCause.add(b);
        
        final Factor effect = new Factor("d", 0, 4);
        final CausalHypothesis startHypothesis = new CausalHypothesis("my start hypothesis");
        startHypothesis.getComplexCauses().add(simpleComplexCause);
        startHypothesis.setEffect(effect);
        model.add(startHypothesis);
        
        ModelIO.saveModel(model, new File("target/test.out"));
        final CausalModel reloaded = ModelIO.loadModel(new File("target/test.out"));
        assertEquals(reloaded.toString(), model.toString());
    }
}
