package com.szalay.thinktools.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The model generator.
 */
public class ModelGenerator {
    private CausalHypothesis currentHypothesis;
    private List<Factor> potentialFactors = new ArrayList<Factor>();
    private final List<Factor> allFactors = new ArrayList<Factor>();
    private final ModelHistory history = new ModelHistory();
    private CausalModel currentModel = new CausalModel();
    private final int maxCount;
    private final List<Factor> explainables = new ArrayList<Factor>();
    private final DataModel dataModel;
    private final List<Individual> anomalies = new ArrayList<Individual>();
    private boolean pause = false;
    private final BigDecimal acceptanceValue;
    private final List<CausalModelOberserver> observers = new ArrayList<CausalModelOberserver>();

    public ModelGenerator(CausalHypothesis startHypothesis, List<Factor> potentialFactors, DataModel dataModel,
    int maxCount, boolean pause, BigDecimal acceptanceValue) {
        this.currentHypothesis = startHypothesis;
        allFactors.addAll(potentialFactors);
        currentModel.add(currentHypothesis);
        history.add(currentModel, "start Model");
        explainables.add(currentHypothesis.getEffect());
        this.maxCount = maxCount;
        this.dataModel = dataModel;
        this.pause = pause;
        this.acceptanceValue = acceptanceValue;
    }
    
    /** this method starts the model generating process */
    public void start() {
        //show start model
        update(currentModel);
        
        //start with the start hypothesis
        print("Starting process...");
        print("Start hypothesis: " + currentHypothesis.toBigString());
        print("Required acceptance value: " + acceptanceValue.toString());
        
        //iterate over all explainables
        while (explainables.size() > 0) {
            
            //get the next explainable
            Factor effectToExplain = explainables.get(0);
            
            //get potential alternative factors
            potentialFactors = getFactorsForTask(effectToExplain);
            
            //solve inhomogenities
            print("Solving inhomogenities....");
            List<CausalHypothesis> oldHypotheses = currentModel;
            solveInhomogenities();
            
            //add possibly new explainables to the list
            if (currentModel.size() > oldHypotheses.size()){
                for (int i = 0; i < currentModel.size(); i++){
                    CausalHypothesis h = currentModel.get(i);
                    if (!oldHypotheses.contains(h)){
                        explainables.add(h.getEffect());
                    }
                }//for
            }
            
            update(currentModel);
            print("Resolved model: " + currentModel.toString());
            
            //reset anomalies set
            anomalies.clear();
            
            print("New explainable: " + effectToExplain.toString());
            
            final List<ComplexCause> checkedAlternatives = new ArrayList<ComplexCause>();
            //check all alternatives
            while (getUncheckedAlternatives(checkedAlternatives).size() > 0) {
                
                //get current alternative
                ComplexCause checkAlternative = getUncheckedAlternatives(checkedAlternatives).get(0);
                print("Evaluating alternative: " + checkAlternative.toString());
                
                //search for potential factors for this explainable
                potentialFactors = getFactorsForTask(effectToExplain, checkAlternative);
                print("Potential factors for this alternative " + potentialFactors.toString());
               
                //init limit counter
                int counter = 0;
                
                //iterate over all potential factors
                while (potentialFactors.size() > 0) {
                    
                    //get next potential factor
                    Factor testFactor = potentialFactors.get(0);
                    print("step: " + counter);
                    print("testing factor " + testFactor.toString());
                    
                    //do the difference test to determine the role of this potential factor
                    DifferenceTest newTest = TestDesigner.createGeneralDifferenceTest(currentHypothesis,
                        checkAlternative, testFactor);
                    print("new test design: " + newTest.toString());
                    MultipleContingencyTable testResult = newTest.perform(dataModel);
                    
                    //store anomalies
                    updateAnomalies(testResult);
                    print("result: \n" + testResult.toString());
                    
                    //create causal table
                    MultipleCausalTable causalTable = TestInterpreter.createMultipleCausalTable(testResult,
                    testResult.averageCellValue());
                    print("interpretation: \n" + causalTable.toString());
                    
                    //generate new hypothesis
                    currentHypothesis = TestInterpreter.evaluate(causalTable, currentHypothesis, checkAlternative);
                    //fetch checked alternative back
                    checkAlternative = currentHypothesis.getComplexCauses().get(0);
                    
                    print("new hypothesis " + currentHypothesis.toBigString());
                    pause();
                    
                    //iterate limit counter
                    counter++;
                    
                    //mark this factor as tested
                    removeFromList(potentialFactors, testFactor);
                    
                    //check limit counter
                    if (counter > maxCount) {
                        print("Break because of limit reached.");
                        break;
                    }
                } //while has more factors
                
                print("No more factors.");
                print("Check whole hypothesis...");
                
                //test hypothesis for acceptance value
                ContingencyTable hypTestResult = DifferenceTest.testHypothesis(currentHypothesis, dataModel);
                int anomalRows = hypTestResult.getB() + hypTestResult.getC();
                BigDecimal acceptance = new BigDecimal((double)anomalRows);
                BigDecimal rows = new BigDecimal(dataModel.size());
                acceptance = acceptance.divide(rows, 10, BigDecimal.ROUND_HALF_UP);
                print("Result:\n");
                print(hypTestResult.toString());
                print("Acceptance value: " + acceptance.toString());
                if (acceptance.doubleValue() <= acceptanceValue.doubleValue()) {
                    print("Break because of acceptance value reached.");
                    break;
                }
                
                //test hypothesis for an ideal result
                if (hypTestResult.isIdeal()) {
                    currentModel.add(currentHypothesis);
                    update(currentModel);
                    print("Break because hypothesis is ideal.");
                    break;
                }
                
                print(checkAlternative.toString() + " check finished.");
                
                //mark this alternative as checked
                checkedAlternatives.add(checkAlternative);
                print(checkedAlternatives.toString());
            } //while has more alternatives
            
            print("No more alternatives.");
            
            //add this hypothesis to the model
            currentModel.add(currentHypothesis);
            update(currentModel);
            
            //mark this explainable as explained
            removeFromList(explainables, currentHypothesis.getEffect());
            
        } //while has more explainables
        print("No more explainables.");
        print("Model: " + currentModel.toString());
        update(currentModel);
    } //start
    
    /**
     *  Add an observer.
     * @param o 
     */
    public void addOberserver(final CausalModelOberserver o) {
        observers.add(o);
    }
    
    /**
     *  Remove an observer.
     * @param o 
     */
    public void removeOberserver(final CausalModelOberserver o) {
        observers.remove(o);
    }
    
     /** returns the model history */
    public ModelHistory getHistory() {
        return history;
    }
    
    /** print string to observer */
    private void print(String message) {
        for (final CausalModelOberserver o : observers) {
            o.print(message);
        }
    }
    
    /** pause */
    private void pause() {
        if (pause) {
            for (final CausalModelOberserver o : observers) {
                o.pause();
            }
        }
    }
    
    /** update */
    private void update(CausalModel model){
       for (final CausalModelOberserver o : observers) {
            o.update(model);
       }
    }
    
    /** returns the current model */
    public CausalHypothesis getHypothesis() {
        return currentHypothesis;
    }
    
    /** returns all potential factors for an effect */
    private List<Factor> getFactorsForTask(final Factor effect) {
        final List<Factor> result = new ArrayList<Factor>(allFactors.size());
        final Iterator<Factor> i = allFactors.iterator();
        while (i.hasNext()) {
            Factor factor = i.next();
            if (factor.equalsIgnoreNegation(effect)) {
                continue;
            }
            result.add(factor);
        } //while
        return result;
    }
    
    /** this method checks the result for anomalies and adds them to the list */
    private void updateAnomalies(final MultipleContingencyTable t) {
       anomalies.addAll(t.getAnomalies());
    }
    
     /** returns all potential factors for an effect */
    private List<Factor> getFactorsForTask(final Factor effect, final ComplexCause c) {
        final List<Factor> result = new ArrayList<Factor>(allFactors.size());
        final Iterator<Factor> i = allFactors.iterator();
        while (i.hasNext()) {
            Factor factor = i.next();
            if (factor.equalsIgnoreNegation(effect)) {
                continue;
            }
            if (c.contains(factor)) {
                continue;
            }
            result.add(factor);
        } //while
        return result;
    }
    
    /** removes a Factor from a list */
    private void removeFromList(final List<Factor> l, final Factor o) {
        final Iterator<Factor> i = l.iterator();
        while (i.hasNext()) {
            Factor node = i.next();
            if (node.getName().equals(o.getName())) {
                i.remove();
            }
        } //while
    }
    
    /** this method checks if there are complex causes in the current hypothesis which have never been checked */
    private List<ComplexCause> getUncheckedAlternatives(List<ComplexCause> checkedCauses) {
        final List<ComplexCause> result = new ArrayList<ComplexCause>();
        for (int j = 0; j < currentHypothesis.getComplexCauses().size(); j++) {
            final ComplexCause f = currentHypothesis.getComplexCauses().get(j);
            boolean found = false;
            for (int i = 0; i < checkedCauses.size(); i++) {
                ComplexCause c = checkedCauses.get(i);
                if (c.equals(f)) {
                    found = true;
                }
            } //for
            if (!found) {
                result.add(f);
            }
        } //for
        return result;
    }
    
    /** returns the model */
    public CausalModel getModel() {
        return currentModel;
    }
    
    /** this method starts the inhomogentity solver */
    private void solveInhomogenities(){
        for (int i = 0; i < potentialFactors.size(); i++){
            Factor factor = potentialFactors.get(i);
            currentModel = InhomogenitySolver.solveInhomogenities(currentModel, currentHypothesis, factor, dataModel);
        }
    }
}
