package com.szalay.thinktools.model;

import java.io.Serializable;

/**
 * Consists of two cells.
 */
public class MultipleCausalTableLine implements Serializable {
    private static final long serialVersionUID = -7335496354252911405L;
    
    private final MultipleCausalTableCell a;
    private final MultipleCausalTableCell b;

    public MultipleCausalTableLine(MultipleCausalTableCell a, MultipleCausalTableCell b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MultipleCausalTableLine other = (MultipleCausalTableLine) obj;
        if (this.a != other.a && (this.a == null || !this.a.equals(other.a))) {
            return false;
        }
        if (this.b != other.b && (this.b == null || !this.b.equals(other.b))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.a != null ? this.a.hashCode() : 0);
        hash = 83 * hash + (this.b != null ? this.b.hashCode() : 0);
        return hash;
    }
    
    /**
     * @return the a
     */
    public MultipleCausalTableCell getA() {
        return a;
    }

    /**
     * @return the b
     */
    public MultipleCausalTableCell getB() {
        return b;
    }
    
    /**
     *  toString
     */
    @Override
    public String toString(){
        return a + ", " + b + ";\n";
    }
}
