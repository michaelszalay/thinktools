package com.szalay.thinktools.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A {@link CausalModel} is a list of {@link CausalHypothesis}.
 */
public class CausalModel extends ArrayList<CausalHypothesis> implements List<CausalHypothesis> {
    private static final long serialVersionUID = -4038559981739581508L;
    
    /** 
     *  this method returns a copy of this model
     */
    public CausalModel copy(){
        final CausalModel copy = new CausalModel();    
        final Iterator<CausalHypothesis> hyps = iterator();
        while (hyps.hasNext()){
            final CausalHypothesis hyp = hyps.next();
            copy.add(hyp.copy());
        }//while
        return copy;      
    }
   
    /**
     *  toString
     */
    @Override
    public String toString(){
        final StringBuilder result = new StringBuilder();
        final Iterator<CausalHypothesis> hyps = iterator();
        while (hyps.hasNext()){
            final CausalHypothesis hyp = hyps.next();
            result.append(hyp.toBigString());
            result.append('\n');
        }//while
        
        return result.toString();
    }
    
    /**
     *  this method returns all factors this model is containing
     */
    public List<Factor> getFactors(){
        final List<Factor> factors = new ArrayList<Factor>();
        final Iterator<CausalHypothesis> hyps = iterator();
        while (hyps.hasNext()){
            final CausalHypothesis hyp = hyps.next();
            factors.addAll(hyp.getFactors());
        }//while
        return factors;
    }
    
    /**
     *  this method returns true if the hypothesis is already contained in the model as a subset
     */
    public boolean containsAsSubset(CausalHypothesis h){
        if (contains(h)){
            return true;
        }
        
        boolean found = false;
        final Iterator<CausalHypothesis> hyps = iterator();
        while (hyps.hasNext()){
            final CausalHypothesis hyp = hyps.next();
            if (hyp.getEffect().equals(h.getEffect())){
                for (int j = 0; j < h.getComplexCauses().size(); j++){
                    final ComplexCause c = h.getComplexCauses().get(j);
                    for (int z = 0; z < hyp.getComplexCauses().size(); z++){
                        ComplexCause cc = hyp.getComplexCauses().get(z);
                        found = cc.containsAsSubset(c);
                    }//for
                    
                    if (found){
                        return true;
                    }
                }//for
            }//if
        }//for
        return found;
    }
}
