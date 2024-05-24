package com.szalay.thinktools.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * The test interpreter contains algorithms to interpret the results
 * of difference tests.
 */
public class TestInterpreter {
    
    //MAGIC STRINGS FOR INTERPRETATIONS
    public static final String FACTOR_IS_PART_OF_HYP = "Factor is part of the hypothesis";
    public static final String FACTOR_IS_PART_OF_ALTERNATIVE_CAUSE = "Factor is part of an alternative cause";
    public static final String NEGATIVE_RELEVANCE = "Factor has negative relevance";
    public static final String POSITIVE_RELEVANCE = "Factor has positive relevance";
    public static final String IRRELEVANCE = "Factor is irrelevant";
    public static final String ANOMAL_PHENOMENS = "There are anormal phenomens";
    public static final String UNKOWN_ALTERNATIVES = "There are unkown alternative causes";
    public static final String OVERDETERMINATION = "There are hints for an overdetermination";
    public static final String ILLEGAL_RESULT = "Illegal Test Result";
    
     /***
     *  this method calculates a CausalTable from a MultipleCausalTable
     */
    public static CausalTable getTogether(final MultipleCausalTable table){
        boolean a = false;
        boolean b = false;
        boolean c = false;
        boolean d = false;
        
        final List<MultipleCausalTableLine> lines = table.getLines();
        final MultipleCausalTableLine firstLine = lines.get(0);
        a = firstLine.getA().isValue();
        b = firstLine.getB().isValue();
        
        final Iterator<MultipleCausalTableLine> i = lines.iterator();
        i.next();
        while (i.hasNext()){
            final MultipleCausalTableLine nextLine = i.next();
            c = c || (nextLine.getA().isValue());
            d = d || (nextLine.getB().isValue());
        }
        return new CausalTable(a, b, c, d);
    }  
    
     /**
     *  this method creates a list of CausalTable objects from a MultipleCausalTable
     */
    public static List<CausalTable> breakDown(MultipleCausalTable table){
        final List<CausalTable> result = new ArrayList<CausalTable>();
        final List<MultipleCausalTableLine> lines = table.getLines();
        
        MultipleCausalTableLine firstLine = lines.get(0);
        for (int i = 1; i < lines.size(); i++){
            final MultipleCausalTableLine line = lines.get(i);
            final CausalTable newTable = new CausalTable(
                    firstLine.getA().isValue(), firstLine.getB().isValue(), line.getA().isValue(), line.getB().isValue());
            result.add(newTable);
        }//for
        
        return result;
    }
    
    /**
     *  this method creates a MultipleCausalTable from a MultipleContingencyTable
     */
    public static MultipleCausalTable createMultipleCausalTable(MultipleContingencyTable table, int schwelle){
        final MultipleCausalTable result = new MultipleCausalTable();
        int counter = 0;
        final Iterator<MultipleContingencyTableLine> lines = table.getLines().iterator();
        while (lines.hasNext()){
            final MultipleContingencyTableLine line = lines.next();
            
            boolean a = false;
            boolean b = false;
            
            if (line.getA() > schwelle){
                a = true;
            }
            
            if (line.getB() > schwelle){
                b = true;
            }
            
            MultipleCausalTableCell aCell = new MultipleCausalTableCell(counter, 0, line.getaAnds(), line.getaOrs(), 
                    line.getaTop(), a);
            MultipleCausalTableCell bCell = new MultipleCausalTableCell(counter, 1, line.getbAnds(), line.getbOrs(), 
                    line.getbTop(), b);
            
            final MultipleCausalTableLine newLine = new MultipleCausalTableLine(aCell, bCell);
            result.addLine(newLine);
            counter++;
        }//while
        
        return result;
    }
    
      /**
     *  this method breaks down a ContingencyTable to a CausalTable
     */
    public static CausalTable createCausalTable(ContingencyTable contTable, int schwelle){
        boolean a = false;
        boolean b = false;
        boolean c = false;
        boolean d = false;
        
        if (contTable.getA() > schwelle){
            a = true;
        }
        
        if (contTable.getB() > schwelle){
            b = true;
        }
        
        if (contTable.getC() > schwelle){
            c = true;
        }
        
        if (contTable.getD() > schwelle){
            d = true;
        }
        
        return new CausalTable(a, b, c, d);
    }
    
    /**
     * this method returns the test code for the causal table
     *
     * see May1999 for code definitions
     */
    public static int getTestCode(CausalTable t) {
        boolean a = t.isA();
        boolean b = t.isB();
        boolean c = t.isC();
        boolean d = t.isD();

        if ((a == true) && (b == false) && (c == false) && (d == false)) {
            return 1;
        } else if ((a == false) && (b == true) && (c == false) && (d == false)) {
            return 2;
        } else if ((a == true) && (b == true) && (c == true) && (d == false)) {
            return 3;
        } else if ((a == true) && (b == true) && (c == false) && (d == true)) {
            return 4;
        } else if ((a == true) && (b == true) && (c == false) && (d == false)) {
            return 5;
        } else if ((a == true) && (b == false) && (c == true) && (d == false)) {
            return 6;
        } else if ((a == false) && (b == true) && (c == false) && (d == true)) {
            return 7;
        } else if ((a == true) && (b == false) && (c == false) && (d == true)) {
            return 8;
        } else if ((a == false) && (b == true) && (c == true) && (d == false)) {
            return 9;
        } else if ((a == true) && (b == false) && (c == true) && (d == true)) {
            return 10;
        } else if ((a == false) && (b == true) && (c == true) && (d == true)) {
            return 11;
        } else if ((a == false) && (b == false) && (c == true) && (d == false)) {
            return 12;
        } else if ((a == false) && (b == false) && (c == false) && (d == true)) {
            return 13;
        } else if ((a == false) && (b == false) && (c == true) && (d == true)) {
            return 14;
        } else if ((a == false) && (b == false) && (c == false) && (d == false)) {
            return 15;
        } else if ((a == true) && (b == true) && (c == true) && (d == true)) {
            return 16;
        } else {
            return -1;
        }
    }
    
       /**
     *  returns a list of all interpretations that follow from a test code
     */
    public static List<String> getInterpretations(int test_code){
        final List<String> interpretations = new ArrayList<String>();
        
        if ((test_code < 1) || (test_code > 16)){
            interpretations.add(ILLEGAL_RESULT);
        }
        
        if (test_code == 1){
            interpretations.add(FACTOR_IS_PART_OF_HYP);
            interpretations.add(NEGATIVE_RELEVANCE);
        }
        
        if (test_code == 2){
            interpretations.add(FACTOR_IS_PART_OF_HYP);
            interpretations.add(POSITIVE_RELEVANCE);
        }
        
        if (test_code == 3){
            interpretations.add(FACTOR_IS_PART_OF_ALTERNATIVE_CAUSE);
            interpretations.add(NEGATIVE_RELEVANCE);
            interpretations.add(OVERDETERMINATION);
        }
        
        if (test_code == 4){
            interpretations.add(FACTOR_IS_PART_OF_ALTERNATIVE_CAUSE);
            interpretations.add(POSITIVE_RELEVANCE);
            interpretations.add(OVERDETERMINATION);
        }
        
        if (test_code == 5){
            interpretations.add(FACTOR_IS_PART_OF_ALTERNATIVE_CAUSE);
            interpretations.add(IRRELEVANCE);
        }
        
        if (test_code == 6){
            interpretations.add(FACTOR_IS_PART_OF_ALTERNATIVE_CAUSE);
            interpretations.add(NEGATIVE_RELEVANCE);
            interpretations.add(ANOMAL_PHENOMENS);
        }
        
        if (test_code == 7){
            interpretations.add(FACTOR_IS_PART_OF_ALTERNATIVE_CAUSE);
            interpretations.add(POSITIVE_RELEVANCE);
            interpretations.add(ANOMAL_PHENOMENS);
        }
        
        if (test_code == 8){
            interpretations.add(FACTOR_IS_PART_OF_HYP);
            interpretations.add(FACTOR_IS_PART_OF_ALTERNATIVE_CAUSE);
            interpretations.add(NEGATIVE_RELEVANCE);
            interpretations.add(POSITIVE_RELEVANCE);
        }
        
        if (test_code == 9){
            interpretations.add(FACTOR_IS_PART_OF_HYP);
            interpretations.add(FACTOR_IS_PART_OF_ALTERNATIVE_CAUSE);
            interpretations.add(NEGATIVE_RELEVANCE);
            interpretations.add(POSITIVE_RELEVANCE);
            interpretations.add(ANOMAL_PHENOMENS);
        }
        
        if (test_code == 10){
            interpretations.add(FACTOR_IS_PART_OF_ALTERNATIVE_CAUSE);
            interpretations.add(POSITIVE_RELEVANCE);
            interpretations.add(ANOMAL_PHENOMENS);
            interpretations.add(OVERDETERMINATION);
        }
        
        if (test_code == 11){
            interpretations.add(FACTOR_IS_PART_OF_ALTERNATIVE_CAUSE);
            interpretations.add(POSITIVE_RELEVANCE);
            interpretations.add(ANOMAL_PHENOMENS);
            interpretations.add(OVERDETERMINATION);
        }
        
        if (test_code == 12){
            interpretations.add(FACTOR_IS_PART_OF_ALTERNATIVE_CAUSE);
            interpretations.add(NEGATIVE_RELEVANCE);
            interpretations.add(POSITIVE_RELEVANCE);
            interpretations.add(ANOMAL_PHENOMENS);
        }
        
        if (test_code == 13){
            interpretations.add(FACTOR_IS_PART_OF_ALTERNATIVE_CAUSE);
            interpretations.add(POSITIVE_RELEVANCE);
            interpretations.add(ANOMAL_PHENOMENS);
        }
        
        if (test_code == 14){
            interpretations.add(POSITIVE_RELEVANCE);
            interpretations.add(ANOMAL_PHENOMENS);
            interpretations.add(IRRELEVANCE);
        }
        
        if (test_code == 15){
            interpretations.add(ANOMAL_PHENOMENS);
        }
        
        if (test_code == 16){
            interpretations.add(UNKOWN_ALTERNATIVES);
        }
        
        return interpretations;
    }
    
    /**
     *  this method evaluates the role of the test factor in the given hypothesis
     */
    public static CausalHypothesis causalReason(final CausalHypothesis testHypothesis, 
            final ComplexCause testCause, final Factor testFactor, final DataModel dataModel) {
        final DifferenceTest test = TestDesigner.createGeneralDifferenceTest(testHypothesis, testCause, testFactor);
        final MultipleContingencyTable mct = test.perform(dataModel);
        final MultipleCausalTable causalTable = createMultipleCausalTable(mct, mct.averageCellValue());
        return (evaluate(causalTable, testHypothesis, testCause));
    }
    
    /**
     *  this method measures how many percent of the datarows could be explained right
     */
    public static BigDecimal getExplainPower(final MultipleContingencyTable cTable, final DataModel dataModel) {
        final BigDecimal zaehler = new BigDecimal(cTable.getNumberOfCases() - cTable.getAnomalies().size());
        final BigDecimal nenner = new BigDecimal(dataModel.size());
        return zaehler.divide(nenner, 3, BigDecimal.ROUND_HALF_UP);
    }
    
       
    /**
     *  this method evaluates a test result for alternative causes and returns a new hypothesis
     */
    public static CausalHypothesis evaluate(final MultipleCausalTable causalTable, 
            final CausalHypothesis testHyp, final ComplexCause testedCause){
        CausalHypothesis newHyp = new CausalHypothesis("modified by TestInterpreter.evaluate() at " + new Date().toString());
        
        //set effect
        newHyp.setEffect(testHyp.getEffect().copy());
        
        final List<ComplexCause> K = new ArrayList<ComplexCause>();
        K.add(testedCause);
        final List<MultipleCausalTableCell> allCells = causalTable.getTrueCellsWithOrsNotRealized();
        
        for (int i = 0; i < allCells.size(); i++){
            final MultipleCausalTableCell cell = allCells.get(i);
            final List<Factor> m = new ArrayList<Factor>();
            final List<MultipleCausalTableCell> n = causalTable.getNeighborsForCell(cell);
            
            for (int j = 0; j < n.size(); j++){
                final MultipleCausalTableCell neighbor = n.get(j);
                
                if (neighbor.isValue() == false){
                    final Factor differentFactor = causalTable.getDifferentFactor(cell, neighbor);
                    if (!m.contains(differentFactor)){
                        m.add(differentFactor);
                    }
                }
            }//for
            
            final ComplexCause newC = new ComplexCause();
            for (int mCounter = 0; mCounter < m.size(); mCounter++){
                newC.add(m.get(mCounter));
            }
            
            K.add(newC);
        }//for
        
        //remove subset of of K
        final List<ComplexCause> newCauses = new ArrayList<ComplexCause>();
        Iterator<ComplexCause> i = K.iterator();
        while (i.hasNext()){
            ComplexCause cc = i.next();
            
            if (newCauses.isEmpty()){
                newCauses.add(cc);
                continue;
            }
            
            for (int i2 = 0; i2 < newCauses.size(); i2++){
                ComplexCause c = newCauses.get(i2);
                
                if (cc.containsAsSubset(c)){
                    newCauses.remove(i2);
                    newCauses.add(i2, cc);
                    continue;
                }else if (c.containsAsSubset(cc)){        
                    continue;
                }else{
                    newCauses.add(cc);
                }
                
            }//for
            
        }//while
        
        for (int iC = 0; iC < newCauses.size(); iC++){
            newHyp.getComplexCauses().add(newCauses.get(iC));
        }
        
        final List<ComplexCause> alternativeCauses = testHyp.getComplexCauses();
        for (int anotherI = 0; anotherI < alternativeCauses.size(); anotherI++){
            final ComplexCause c = alternativeCauses.get(anotherI);
            if (!c.equals(testedCause)){
                newHyp.getComplexCauses().add(c.copy());
            }
        }//for
        
        return newHyp;
    }
}
