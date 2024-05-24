package com.szalay.thinktools.model;

/**
 * Designs difference tests.
 */
public class TestDesigner {
    
    /**
     *  creates a general difference test that test the role of testfactor for the effect in testhypothesis and the testcause
     */
    public static DifferenceTest createGeneralDifferenceTest
            (final CausalHypothesis testHypothesis, final ComplexCause testCause, final Factor testFactor){
        final DifferenceTest test = new DifferenceTest();
        
        //add left side factors
        for (int testcauses = 0; testcauses < testCause.size(); testcauses++){
            test.addLeftSideFactor(testCause.get(testcauses));
        }
      
        //set homogen factors
        for (int i = 0; i < testHypothesis.getComplexCauses().size(); i++){
            final ComplexCause c = testHypothesis.getComplexCauses().get(i);
            
            if (!c.equals(testCause)){
                for (int j = 0; j < c.size(); j++){
                    final Factor f = c.get(j);
                    test.addHomogenFactor(f);
                }//fot
            }
        }
        
        //set top factor
        test.setTopFactor(testFactor);
        return test;
    }

    
    /**
     *  this method generates a simple difference test
     */
    public static DifferenceTest simpleDifferenceTest(final CausalHypothesis hyp, final ComplexCause selectedCause){
        final DifferenceTest test = new DifferenceTest();
        for (int i = 0; i < selectedCause.size(); i++){
            Factor factor = selectedCause.get(i);
            test.addLeftSideFactor(factor);
        }
        
        test.setTopFactor(hyp.getEffect());
        return test;
    }
    
    /**
     *  creates a difference test for a complete hypothesis
     */
    public static DifferenceTest designHypothesisTest(final CausalHypothesis hyp){
        final DifferenceTest test = new DifferenceTest();
        test.setTopFactor(hyp.getEffect());
        
        for (int i = 0; i < hyp.getComplexCauses().size(); i++){
            final ComplexCause c = hyp.getComplexCauses().get(i);
            for (int j = 0; j < c.size(); j++){
                final Factor factor = c.get(j);
                test.addLeftSideFactor(factor);
            }//for
        }//for 
        
        return test;
    }    
}
