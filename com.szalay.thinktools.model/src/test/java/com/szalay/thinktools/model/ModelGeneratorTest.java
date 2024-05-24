package com.szalay.thinktools.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Contains tests for the model generator class.
 */
public class ModelGeneratorTest {   
    @Test
    public void testModelGenerator1() {
        final Factor a = new Factor("a", 0, 1);
        final Factor b = new Factor("b", 0, 1);
        
        final Factor c = new Factor("c", 0, 2);
        final List<Factor> potentialFactors = new ArrayList<Factor>(3);
        potentialFactors.add(a);
        potentialFactors.add(b);
        potentialFactors.add(c);
        
        final DataModel dataModel = new DataModel();
        
        //add individuals...
        for (int i = 0; i < 1000; i++) {
            final Individual ind = new Individual();
            ind.add(new DataProperty("a", 1));
            ind.add(new DataProperty("b", 0));
            ind.add(new DataProperty("c", 2));
            ind.add(new DataProperty("d", 0));
            dataModel.add(ind);
        }
        
        final Factor effect = new Factor("d", 0, 4);
        final ComplexCause simpleComplexCause = new ComplexCause();
        simpleComplexCause.add(a);
        simpleComplexCause.add(b);
        
        final CausalHypothesis startHypothesis = new CausalHypothesis("my start hypothesis");
        startHypothesis.getComplexCauses().add(simpleComplexCause);
        startHypothesis.setEffect(effect);
        
        final ModelGenerator generator = new ModelGenerator(startHypothesis, potentialFactors, dataModel, 100, false, 
                new BigDecimal("0.40"));
        generator.addOberserver(new CausalModelOberserver() {
            @Override
            public void print(String msg) {
                System.out.println(msg);
            }

            @Override
            public void pause() {
                //Nothing
            }

            @Override
            public void update(CausalModel model) {
                System.out.println("New model: " + model);
            }
        });
        generator.start();
    }
    
    @Test
    public void testModelGenerator2() {
        final Factor a = new Factor("a", 0, 1);
        final Factor b = new Factor("b", 0, 1);
        
        final Factor c = new Factor("c", 0, 2);
        final List<Factor> potentialFactors = new ArrayList<Factor>(3);
        potentialFactors.add(a);
        potentialFactors.add(b);
        potentialFactors.add(c);
        
        final DataModel dataModel = new DataModel();
        
        //add individuals...
        for (int i = 0; i < 1000; i++) {
            final Individual ind = new Individual();
            if ((i % 2) == 0) {
                ind.add(new DataProperty("a", 1));
                ind.add(new DataProperty("d", 0));
            }
            else {
                ind.add(new DataProperty("a", 0));
                ind.add(new DataProperty("d", 1));
            }    
            ind.add(new DataProperty("b", 0));
            ind.add(new DataProperty("c", 2));
            dataModel.add(ind);
        }
        
        final Factor effect = new Factor("d", 0, 4);
        final ComplexCause simpleComplexCause = new ComplexCause();
        simpleComplexCause.add(a);
        simpleComplexCause.add(b);
        
        final CausalHypothesis startHypothesis = new CausalHypothesis("my start hypothesis");
        startHypothesis.getComplexCauses().add(simpleComplexCause);
        startHypothesis.setEffect(effect);
        
        final ModelGenerator generator = new ModelGenerator(startHypothesis, potentialFactors, dataModel, 100, false, 
                new BigDecimal("0.40"));
        generator.addOberserver(new CausalModelOberserver() {
            @Override
            public void print(String msg) {
                System.out.println(msg);
            }

            @Override
            public void pause() {
                //Nothing
            }

            @Override
            public void update(CausalModel model) {
                System.out.println("New model: " + model);
            }
        });
        generator.start();
    }
}
