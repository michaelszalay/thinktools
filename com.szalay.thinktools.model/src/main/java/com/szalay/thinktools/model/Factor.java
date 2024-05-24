package com.szalay.thinktools.model;

import java.io.Serializable;

/**
 * A factor is a part of a hypothesis.
 */
public class Factor implements Serializable {
    private static final long serialVersionUID = -8648717461090158138L;
    
    private final String name;
    private final Comparable upperRangeValue;
    private final Comparable lowerRangeValue;
    private boolean logicalValue = true;

    /**
     *  Create a new factor.
     * @param name
     * @param upperRangeValue
     * @param lowerRangeValue 
     */
    public Factor(String name, Comparable upperRangeValue, Comparable lowerRangeValue) {
        if (name == null) {
            throw new IllegalArgumentException("Illegal factor name: null.");
        }
        if (upperRangeValue == null) {
            throw new IllegalArgumentException("Illegal upperRangeValue name: null.");
        }
        if (lowerRangeValue == null) {
            throw new IllegalArgumentException("Illegal lowerRangeValue name: null.");
        }
        this.name = name;
        this.upperRangeValue = upperRangeValue;
        this.lowerRangeValue = lowerRangeValue;
    }
    
    /**
     * negates the operator
     */
    public void negate(){
        setLogicalValue(!isLogicalValue());
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the upperRangeValue
     */
    public Comparable getUpperRangeValue() {
        return upperRangeValue;
    }

    /**
     * @return the lowerRangeValue
     */
    public Comparable getLowerRangeValue() {
        return lowerRangeValue;
    }

    /**
     * @return the logicalValue
     */
    public boolean isLogicalValue() {
        return logicalValue;
    }

    /**
     * @param logicalValue the logicalValue to set
     */
    public void setLogicalValue(boolean logicalValue) {
        this.logicalValue = logicalValue;
    }
    
    /**
     *  returns a copy of this node
     */
    public Factor copy() {
        final Factor copy = new Factor(name, lowerRangeValue, upperRangeValue);
        copy.setLogicalValue(logicalValue);
        return copy;
    }
    
    /**
     *  returns a readable string
     */
    @Override
    public String toString(){
        String content = lowerRangeValue + " <= " + name + " <= " + upperRangeValue;
        if (!(logicalValue)){
            String old = content;
            content = " NOT (" + old + " ) ";
        }
        return content;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Factor other = (Factor) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.upperRangeValue != other.upperRangeValue) {
            return false;
        }
        if (this.lowerRangeValue != other.lowerRangeValue) {
            return false;
        }
        if (this.logicalValue != other.logicalValue) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 53 * hash + this.upperRangeValue.hashCode();
        hash = 53 * hash + this.lowerRangeValue.hashCode();
        hash = 53 * hash + (this.logicalValue ? 1 : 0);
        return hash;
    }
    
    /** 
     *  equals ignore negation
     */
    public boolean equalsIgnoreNegation(Object o){
        if (!(o instanceof Factor)){
            return false;
        }
        
        final Factor f = (Factor) o;
        return (f.getName().equals(getName()));
    }
    
    /**
     *  returns true if the given value is in the ranges
     */
    public boolean isInRange(final Comparable value){
        if (value == null) {
            return false;
        }

        try {
            final boolean lowerRange = lowerRangeValue.compareTo(value) >= 0;
            final boolean upperRange = upperRangeValue.compareTo(value) <= 0;
            return lowerRange && upperRange;
        }
        catch (ClassCastException ce) {
            return false;
        }
    }
}
