package com.szalay.thinktools.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Dummy football test.
 * @author szalay
 */
public class FootballTest {
    @Test
    public void testFootball() {
        //Define factor strength
        final Factor homeStrength = new Factor("homeStrength", 0, 1);
        final Factor homeWin = new Factor("homeWin", 0, 1);
        final Factor awayStrength = new Factor("awayStrength", 0, 1);
        final Factor awayWin = new Factor("awayWin", 0, 1);
        final Factor dummy = new Factor("dummy", 0, 1);
        
        final List<Factor> potentialFactors = new ArrayList<Factor>(4);
        potentialFactors.add(homeStrength);
        potentialFactors.add(homeWin);
        potentialFactors.add(awayStrength);
        potentialFactors.add(awayWin);
        potentialFactors.add(dummy);
        
        //Model the data
        final DataModel dataModel = new DataModel();
        
        //add some games
        for (int i = 0; i < 1000; i++) {
            addGame(dataModel, (i % 2) == 0);
        }
        
        //Model the hypothesis that that the weaker home team wins...
        final ComplexCause originalCause = new ComplexCause();
        originalCause.add(awayStrength);
        final CausalHypothesis startHypothesis = new CausalHypothesis("my start hypothesis");
        startHypothesis.getComplexCauses().add(originalCause);
        startHypothesis.setEffect(homeWin);
        
         final ModelGenerator generator = new ModelGenerator(startHypothesis, potentialFactors, dataModel, 100, false, 
                new BigDecimal("1"));
        generator.addOberserver(new CausalModelOberserver() {
            @Override
            public void print(String msg) {
            }

            @Override
            public void pause() {
                //Nothing
            }

            @Override
            public void update(CausalModel model) {
                //Nothing
            }
        });
        generator.start();
        
        System.out.println("Start hypothesis: " + startHypothesis.getComplexCauses() + " -> " + startHypothesis.getEffect());
        System.out.println("Result: " + generator.getModel());
    }
    
    private void addGame(final DataModel dataModel, final boolean homeWins) {
        final Individual i = new Individual();
        if (homeWins) {
            i.add(new DataProperty("homeStrength", 1));
            i.add(new DataProperty("homeWin", 1));
            i.add(new DataProperty("awayStrength", 0));
            i.add(new DataProperty("awayWin", 0));
            i.add(new DataProperty("dummy", 0));
        }
        else {
            i.add(new DataProperty("homeStrength", 0));
            i.add(new DataProperty("homeWin", 0));
            i.add(new DataProperty("awayStrength", 1));
            i.add(new DataProperty("awayWin", 1));
            i.add(new DataProperty("dummy", 0));
        }
        dataModel.add(i);
    }
}
