package com.szalay.thinktools.model;

import java.io.Serializable;
import java.util.List;

/**
 * Cell in a mupltiple causal table.
 */
public class MultipleCausalTableCell implements Serializable {
    private static final long serialVersionUID = 4220915159585855290L;
    private final int x;
    private final int y;
    private final List<Factor> ands;
    private final List<Factor> ors;
    private final Factor top;
    private final boolean value;

    public MultipleCausalTableCell(int x, int y, List<Factor> ands, List<Factor> ors, Factor top, boolean value) {
        this.x = x;
        this.y = y;
        this.ands = ands;
        this.ors = ors;
        this.top = top;
        this.value = value;
    }

    /**
     * Gets the and factors.
     * @return 
     */
    public List<Factor> getAnds() {
        return ands;
    }

    /**
     *  Gets the or factors.
     */
    public List<Factor> getOrs() {
        return ors;
    }

    /**
     *  Gets the top factor.
     * @return 
     */
    public Factor getTop() {
        return top;
    }

    /**
     *  Gets the value.
     * @return 
     */
    public boolean isValue() {
        return value;
    }

    /**
     *  Gets the x.
     * @return 
     */
    public int getX() {
        return x;
    }

    /**
     *  Gets the y.
     * @return 
     */
    public int getY() {
        return y;
    }
    
    /**
     *  toBigString
     */
    @Override
    public String toString(){
        String s = "Pos: " + x + ", " + y + "\n";
        s += "Value: " + value + "\n";
        s += "Ands: " + ands + "\n";
        s += "Ors: " + ors + "\n";
        s += "Top factor: " + top + "\n";
        return s;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.x;
        hash = 53 * hash + this.y;
        hash = 53 * hash + (this.value ? 1 : 0);
        return hash;
    }
      
    /**
     *  equals
     */
    @Override
    public boolean equals(Object o){
        if (!(o instanceof MultipleCausalTableCell)) {
            return false;
        }
        MultipleCausalTableCell m = (MultipleCausalTableCell) o;
        return ((x == m.getX()) && (y == m.getY()) && (value == m.isValue()));
    }
    
    /**
     *  Checks wether are or factors are true.
     * @return 
     */
    public boolean orsNotRealized(){
        for (int i = 0; i < ors.size(); i++){
            Factor f = ors.get(i);
            if (f.isLogicalValue()){
                return false;
            }
        }
        return true;
    }
}
