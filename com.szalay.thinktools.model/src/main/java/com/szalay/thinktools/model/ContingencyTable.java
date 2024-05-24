package com.szalay.thinktools.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Models a contingency table.
 */
public class ContingencyTable {
    private final int a;
    private final int b;
    private final int c;
    private final int d;
    
    private final List<Individual> anomalies = new ArrayList<Individual>();

    public ContingencyTable(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * @return the a
     */
    public int getA() {
        return a;
    }

    /**
     * @return the b
     */
    public int getB() {
        return b;
    }

    /**
     * @return the c
     */
    public int getC() {
        return c;
    }

    /**
     * @return the d
     */
    public int getD() {
        return d;
    }

    /**
     * @return the anomalies
     */
    public List<Individual> getAnomalies() {
        return anomalies;
    }
    
    /**
     * returns the chi-value of this table if possible, else 0
     */
    public BigDecimal getChi() {
        final int n = a + b + c + d;
        final BigDecimal stage1 = new BigDecimal(((a * d) - (b * c)));
        final BigDecimal stage2 = stage1.multiply(stage1);
        final BigDecimal zaehler = stage2.multiply(new BigDecimal(n));
        final BigDecimal nenner = new BigDecimal(((a + b) * (c + d) * (a + c) * (b + d)));

        BigDecimal chi = null;

        try {
            chi = zaehler.divide(nenner, 10, BigDecimal.ROUND_HALF_UP);
        } catch (ArithmeticException e) {
            chi = new BigDecimal(0.0);
        }

        return chi;
    }
    
    /**
     *  returns number of counted cases
     */
    public int getNumberOfCases(){
        return (a + b + c + d);
    }
    
    /**
     *  return the maximum value
     */
    public int getMaxValue(){
        final List<Integer> values = new ArrayList<Integer>(4);
        values.add(a);
        values.add(b);
        values.add(c);
        values.add(d);
        Integer maxint = Collections.max(values);
        return maxint.intValue();
    }
    
    
    /**
     *  toString
     */
    @Override
    public String toString(){
       String result = new String();
       result += a + ", " + b + ", ";
       result += c + ", " + d ;
       return result;
    }
    
    /**
     * a table is ideal if a and b are equals to number of cases
     */
    public boolean isIdeal(){
        //Check if illegal
        if (a == -1){
            return false;
        }
        
        if (getNumberOfCases() == 0){
            return false;
        }
        
        //check if ideal
        return ((a + d) == getNumberOfCases());
    }
}
