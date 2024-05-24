package com.szalay.thinktools.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Multiple contigency table.
 */
public class MultipleContingencyTable {
    private final List<MultipleContingencyTableLine> lines = new ArrayList<MultipleContingencyTableLine>();
    private final List<Individual> anomalies = new ArrayList<Individual>();
    
    /**
     *  adds a line to this table
     */
    public void addLine(final MultipleContingencyTableLine line){
        lines.add(line);
    }
    
    /**
     *  returns all lines
     */
    public List<MultipleContingencyTableLine> getLines(){
        return lines;
    }  
    
    /**
     *  toString
     */
    @Override
    public String toString(){
        final StringBuilder result = new StringBuilder();
        final Iterator<MultipleContingencyTableLine> i = lines.iterator();
        while (i.hasNext()){
            MultipleContingencyTableLine line = i.next();
            result.append(line.toString());
            result.append(";\n");
        }    
        return result.toString();
    }
    
    /**
     *  adds a anomal row
     */
    public void addAnomalRows(List<Individual> rows){
        anomalies.addAll(rows);
    }
    
    /**
     *  returns all anomalies
     */
    public List<Individual> getAnomalies(){
        return anomalies;
    }
    
    /**
     *  set anomalies
     */
    public void setAnomalies(List<Individual> a){
        anomalies.clear();
        anomalies.addAll(a);
    }
    
    /**
     *  returns the number of all cases
     */
    public int getNumberOfCases(){
        int cases = 0;
        final Iterator<MultipleContingencyTableLine> i = lines.iterator();
        while (i.hasNext()){
            MultipleContingencyTableLine line = i.next();
            cases += line.getA() + line.getB();
        }//while
        return cases;
    }
    
    /**
     *  returns the average value for a cell
     */
    public int averageCellValue(){
        final int countCells = lines.size() * 2;
        return (getNumberOfCases() / countCells);
    }
    
    /**
     * returns true if the first line has a 0 in the second cell, all others a 0 in the first cell
     */
    public boolean isIdeal() {
        boolean ideal = true;
        for (int i = 0; i < lines.size(); i++) {
            MultipleContingencyTableLine line = lines.get(i);
            if (i == 0) {
                ideal = (line.getB() == 0);
            } else {
                ideal = ideal && (line.getA() == 0);
            }
        }//for

        return ideal;
    }
}
