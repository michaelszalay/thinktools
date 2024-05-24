package com.szalay.thinktools.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A causal hypothesis is a list of complex causes with a name and an effect.
 */
public class CausalHypothesis implements Serializable {
    private static final long serialVersionUID = -2370637209353366814L;
    
    //attributes
    private final List<ComplexCause> complexCauses = new ArrayList<ComplexCause>();
    private final String name;
    private Factor effect;

    /**
     *  Create a new causal hypothesis.
     * @param name 
     */
    public CausalHypothesis(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Illegal name: null.");
        }
        this.name = name;
    }

    /**
     * @return the complexCauses
     */
    public List<ComplexCause> getComplexCauses() {
        return complexCauses;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CausalHypothesis other = (CausalHypothesis) obj;
        if (this.complexCauses != other.complexCauses && (this.complexCauses == null || !this.complexCauses.equals(other.complexCauses))) {
            return false;
        }
        if (this.effect != other.effect && (this.effect == null || !this.effect.equals(other.effect))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.complexCauses != null ? this.complexCauses.hashCode() : 0);
        hash = 53 * hash + (this.effect != null ? this.effect.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the effect
     */
    public Factor getEffect() {
        return effect;
    }

    /**
     * @param effect the effect to set
     */
    public void setEffect(Factor effect) {
        this.effect = effect;
    }
    
    /**
     *  returns a copy
     */
    public CausalHypothesis copy(){
        final CausalHypothesis copy = new CausalHypothesis(name);
        final Iterator<ComplexCause> myCauses = complexCauses.iterator();
        while (myCauses.hasNext()){
            final ComplexCause c = myCauses.next();
            copy.getComplexCauses().add(c.copy());
        }//while
        
        if (effect != null){
            copy.setEffect(effect);
        }
        return copy;
    }
    
    /**
     *  returns a big string
     */
    public String toBigString(){
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < complexCauses.size(); i++){
            final ComplexCause c = complexCauses.get(i);
            result.append(c.toString());
            if (i < (complexCauses.size()-1)){
                result.append(" v ");
            }
        }//for
        
        result.append(" -> ");
        if (effect != null) {
            result.append(effect.toString());
        }
        return result.toString();
    }
    
     /**
     *  returns all factors
     */
    public List<Factor> getFactors(){
        final List<Factor> allFactors = new ArrayList<Factor>();
        for (final ComplexCause c : complexCauses) {
            allFactors.addAll(c);
        }
        if (effect != null) {
            allFactors.add(effect);
        }
        return allFactors;
    }
}
