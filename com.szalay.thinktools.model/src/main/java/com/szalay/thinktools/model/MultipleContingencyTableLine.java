package com.szalay.thinktools.model;

import java.util.List;

/**
 * Line in a MultipleContingencyTable.
 */
public class MultipleContingencyTableLine {
    private final int a;
    private final int b;
    private final List<Individual> aCases;
    private final List<Individual> bCases;
    private final List<Factor> aAnds;
    private final List<Factor> bAnds;
    private final List<Factor> aOrs;
    private final List<Factor> bOrs;
    private final Factor aTop;
    private final Factor bTop;

    public MultipleContingencyTableLine(List<Individual> a, List<Individual> b, List<Factor> _aAnds, List<Factor> _bAnds, 
            List<Factor> _aOrs, List<Factor> _bOrs, Factor _aTop, Factor _bTop) {
        this.a = a.size();
        this.b = b.size();
        this.aCases = a;
        this.bCases = b;
        this.aAnds = _aAnds;
        this.bAnds = _bAnds;
        this.aOrs = _aOrs;
        this.bOrs = _bOrs;
        this.aTop = _aTop;
        this.bTop = _bTop;
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
     * @return the aCases
     */
    public List<Individual> getaCases() {
        return aCases;
    }

    /**
     * @return the bCases
     */
    public List<Individual> getbCases() {
        return bCases;
    }

    /**
     * @return the aAnds
     */
    public List<Factor> getaAnds() {
        return aAnds;
    }

    /**
     * @return the bAnds
     */
    public List<Factor> getbAnds() {
        return bAnds;
    }

    /**
     * @return the aOrs
     */
    public List<Factor> getaOrs() {
        return aOrs;
    }

    /**
     * @return the bOrs
     */
    public List<Factor> getbOrs() {
        return bOrs;
    }

    /**
     * @return the aTop
     */
    public Factor getaTop() {
        return aTop;
    }

    /**
     * @return the bTop
     */
    public Factor getbTop() {
        return bTop;
    }
    
    /**
     *  toString
     */
    @Override
    public String toString(){
        return a + ", " + b;
    }
}
