package com.szalay.thinktools.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class models a DifferenceTest.
 */
public class DifferenceTest {
    private final List<Factor> leftSideFactors = new ArrayList<Factor>();
    private final List<Factor> homogenFactors = new ArrayList<Factor>();
    private Factor topFactor;
    
    /**
     *  add a factor to the left side
     */
    public void addLeftSideFactor(final Factor factor) {
        if (factor == null) {
            throw new IllegalArgumentException("Illegal factor: null.");
        }
        leftSideFactors.add(factor.copy());
    }
    
    /**
     *  returns true if the factor is in the left side
     */
    public boolean containsAsLeftSideFactor(final Factor factor) {
        if (factor == null) {
            throw new IllegalArgumentException("Illegal factor: null.");
        }
        for (final Factor f : leftSideFactors) {
            if (f.equalsIgnoreNegation(factor)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     *  remove a factor from the left side
     */
    public void removeLeftSideFactor(Factor factor) {
        final Iterator<Factor> i  = leftSideFactors.iterator();
        while (i.hasNext()){
            final Factor f = i.next();
            if (f.equals(factor)){
                i.remove();
            }
        }//while
    }
     
    /**
     *  returns all left side factors
     */
    public List<Factor> getLeftSideFactors() {
        return leftSideFactors;
    }
    
    /**
     *  sets the top factor
     */
    public void setTopFactor(final Factor factor) {
        if (factor == null) {
            throw new IllegalArgumentException("Illegal factor: null.");
        }
        topFactor = factor.copy();
    }
    
    /**
     *  return the top factor
     */
    public Factor getTopFactor() {
        return topFactor;
    }
    
    /**
     *  add a homogen factor
     */
    public void addHomogenFactor(final Factor factor) {
        if (factor == null) {
            throw new IllegalArgumentException("Illegal factor: null.");
        }
        
        homogenFactors.add(factor.copy());
    }
    
    /**
     *  removes a homogen factor
     */
    public void removeHomogenFactor(final Factor factor) {
        if (factor == null) {
            throw new IllegalArgumentException("Illegal factor: null.");
        }
        
        final Iterator<Factor> i = homogenFactors.iterator();
        while (i.hasNext()){
            final Factor f = i.next();
            if (f.equals(factor)){
                i.remove();
            }
        }//while
    }
    
    /**
     *  returns all homogen factors
     */
    public List<Factor> getHomogenFactors() {
        return homogenFactors;
    }
    
     /**
     *  performs a general difference test
     */
    public MultipleContingencyTable perform(final DataModel dataModel) throws IllegalStateException {
        //check settings
        if ((leftSideFactors.isEmpty()) || (topFactor == null)){
            throw new IllegalStateException("DifferenceTest, perform. No test factors set.");
        }
        
        if (dataModel == null){
            throw new IllegalStateException("DifferenceTest, perform. No data model set.");
        }
        
        //our new table
        final MultipleContingencyTable contingencyTable = new MultipleContingencyTable();
        
        //the first line remains as it is
        final List<Factor> allFactors = new ArrayList<Factor>();
        allFactors.add(topFactor);
        allFactors.addAll(leftSideFactors);
        allFactors.addAll(negateAllFactors(homogenFactors));
        
        final List<Individual> firstLineARows = getRowsWhereFactorsSet(allFactors, dataModel);
      
        allFactors.clear();
        Factor negTopFactor = topFactor.copy();
        negTopFactor.negate();
        
        allFactors.add(negTopFactor);
        allFactors.addAll(leftSideFactors);
        allFactors.addAll(homogenFactors);
        
        List<Individual> firstLineBRows = getRowsWhereFactorsSet(allFactors, dataModel);
        contingencyTable.addAnomalRows(firstLineBRows);
        
        MultipleContingencyTableLine line = 
                new MultipleContingencyTableLine(firstLineARows, firstLineBRows,  
                leftSideFactors, 
                leftSideFactors, 
                homogenFactors, 
                homogenFactors, 
                topFactor, 
                negTopFactor);
        contingencyTable.addLine(line);
        
        //iterate over all left side factors
        for (int i = 0; i < leftSideFactors.size(); i++){
            final Factor factor = leftSideFactors.get(i);
            final Factor negFactor = factor.copy();
            negFactor.negate();
            leftSideFactors.remove(factor);
            leftSideFactors.add(i, negFactor);
            
            final List<Factor> leftSideFactorsALine = new ArrayList<Factor>();
            leftSideFactorsALine.addAll(leftSideFactors);
            leftSideFactorsALine.add(topFactor);
            leftSideFactorsALine.addAll(homogenFactors);
            
            final List<Individual> thisLineARows = getRowsWhereFactorsSet(leftSideFactorsALine, dataModel);
            contingencyTable.addAnomalRows(thisLineARows);
            
            final List<Factor> leftSideFactorsBLine = new ArrayList<Factor>();
            leftSideFactorsBLine.addAll(leftSideFactors);
            leftSideFactorsBLine.add(negTopFactor);
            leftSideFactorsBLine.addAll(homogenFactors);
            
            final List<Individual> thisLineBRows = getRowsWhereFactorsSet(leftSideFactorsBLine, dataModel);
            
            final MultipleContingencyTableLine thisline = 
                    new MultipleContingencyTableLine(thisLineARows, thisLineBRows, 
                    copyList(leftSideFactors), copyList(leftSideFactors), 
                    homogenFactors, homogenFactors, topFactor, negTopFactor);
            contingencyTable.addLine(thisline);
            
            leftSideFactors.remove(negFactor);
            leftSideFactors.add(i, factor);
        }//for all left side factors
        return contingencyTable;
    }
    
    /**
     *  Performs a simple test.
     * @param dataModel
     * @return 
     */
     public ContingencyTable simplePerform(final DataModel dataModel) {
        if (dataModel == null){
            throw new IllegalStateException("DifferenceTest, parameter dataModel is null.");
        }
        
        if (leftSideFactors.isEmpty()){
            throw new IllegalStateException("DifferenceTest, no left side factors set.");
        }
        
        if (topFactor == null){
            throw new IllegalStateException("DifferenceTest, no top factor set.");
        }
        
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        final List<Individual> anomalies = new ArrayList<Individual>();
        final Iterator<Individual> rows = dataModel.iterator();
        while (rows.hasNext()){
            final Individual row = rows.next();
            boolean leftSideOK = true;
            
            for (int i = 0; i < leftSideFactors.size(); i++){
                leftSideOK = (leftSideOK && factorCorrectSet(leftSideFactors.get(i), row));
            }//for
            
            boolean topSideOK = factorCorrectSet(topFactor, row);
            
            if (leftSideOK){
                if (topSideOK){
                    a++;
                }else{
                    b++;
                    anomalies.add(row);
                }
            }
            
            leftSideOK = true;
            
            for (int i = 0; i < leftSideFactors.size(); i++){
                Factor f = leftSideFactors.get(i);
                f.negate();
                leftSideOK = (leftSideOK && factorCorrectSet(f, row));
                f.negate();
            }//for
            
            topSideOK = factorCorrectSet(topFactor, row);
            
            if (leftSideOK){
                if (topSideOK){
                    c++;
                    anomalies.add(row);
                }else{
                    d++;
                }
            }
            
            
        }//while has more rows
        
        final ContingencyTable cTable = new ContingencyTable(a, b, c, d);
        cTable.getAnomalies().addAll(anomalies);
        return cTable;
    }
     
     
    /**
     * toString
     */
    @Override
    public String toString() {
        return leftSideFactors.toString() + ", " + homogenFactors + ", " + topFactor;
    }
    
    /**
     *  this method checks a hypothesis as complete
     */
    public static ContingencyTable testHypothesis(CausalHypothesis hyp, final DataModel dataModel){
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        
        hyp = hyp.copy();
        for (int i = 0; i < dataModel.size(); i++){
            final Individual row = dataModel.get(i);
            boolean tested = false;
            List<ComplexCause> complexCauses = hyp.getComplexCauses();
            for (int j = 0; j < complexCauses.size(); j++){
                final ComplexCause cause = complexCauses.get(j);
                boolean applicable = true;
                
                for (int z = 0; z < cause.size(); z++){
                    final Factor f = cause.get(z);
                    applicable = applicable && factorCorrectSet(f, row);
                }//for all factors
                
                
                if (applicable){
                    final Factor effect = hyp.getEffect();
                    if (factorCorrectSet(effect, row)){
                        a++;
                    }else{
                        b++;
                    }
                    
                    tested = true;
                }
                
                if (tested){
                    break;
                }
            }//for all complex causes            
            
            if(!tested){
                 Factor effect = hyp.getEffect();
                 if (factorCorrectSet(effect, row)){
                        c++;
                 }else{
                        d++;
                 }
            }
                
                
        }//for all rows
        
        return new ContingencyTable(a, b, c, d);
    }
    
    private List<Factor> copyList(final List<Factor> input) {
        final List<Factor> output = new ArrayList<Factor>(input.size());
        for (final Factor f : input) {
            output.add(f.copy());
        }
        return output;
    }
    
    /**
     *  negates all factors in the list
     */
    private List<Factor> negateAllFactors(final List<Factor> factors){
        final List<Factor> negatedFactors = new ArrayList<Factor>(factors.size());
        for (int i = 0; i < factors.size(); i++){
            final Factor negFactor = factors.get(i);
            final Factor aFactor = negFactor.copy();
            aFactor.negate();
            negatedFactors.add(aFactor);
        }//for
        return negatedFactors;
    }
    
     /**
     *  returns all rows in the datamodel where the factors are set
     */
    private List<Individual> getRowsWhereFactorsSet(final List<Factor> factors, final DataModel dataModel){
        final List<Individual> dataRows = new ArrayList<Individual>();
        final Iterator<Individual> rows = dataModel.iterator();
        while (rows.hasNext()){
            final Individual row = rows.next();
            boolean isCorrect = true;
            for (int i = 0; i < factors.size(); i++){
                final Factor factor = factors.get(i);
                isCorrect = isCorrect && factorCorrectSet(factor, row);
            }//fot
            
            if (isCorrect){
                dataRows.add(row);
            }
        }//while
        return dataRows;
    }
    
    /**
     *  this helper method checks if a factor is set in a data row
     */
    public static boolean factorCorrectSet(final Factor factor, final Individual row){
        final String dataColumn = factor.getName();
        final DataProperty column = row.getPropertyByName(dataColumn);
        if (column == null) {
            return false;
        }
        
        if (!(column.getValue() instanceof Comparable)) {
            return false;
        }
        
        final Comparable value = (Comparable) column.getValue();
        if (value == null) {
            return false;
        }
        
        boolean result;
        if (factor.isLogicalValue()){
            result =  (factor.isInRange(value));
        }else{
            result = (!(factor.isInRange(value)));
        }
        return result;
    }
}
