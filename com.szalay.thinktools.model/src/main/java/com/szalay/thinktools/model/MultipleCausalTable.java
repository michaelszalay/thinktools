package com.szalay.thinktools.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The multiple causal table.
 */
public class MultipleCausalTable implements Serializable {
    private static final long serialVersionUID = 5020439540327449659L;
    
    private final List<MultipleCausalTableLine> lines = new ArrayList<MultipleCausalTableLine>();
    
    /**
     *  adds a line to this table
     */
    public void addLine(MultipleCausalTableLine line){
        lines.add(line);
    }
    
    /**
     *  returns all lines
     */
    public List<MultipleCausalTableLine> getLines(){
        return lines;
    }
    
    /**
     *  toString
     */
    @Override
    public String toString(){
        final StringBuilder result = new StringBuilder();
        final Iterator<MultipleCausalTableLine> i = lines.iterator();
        while (i.hasNext()){
            final MultipleCausalTableLine line = i.next();
            result.append(line.toString());
        }
        return result.toString();
    }
    
    /**
     *  equals
     */
    @Override
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }
        if (!(o instanceof MultipleCausalTable)) {
            return false;
        }
        MultipleCausalTable table = (MultipleCausalTable) o;
        return (table.getLines().equals(lines));
    }

    /**
     *  returns a list of all cells
     */
    public List<MultipleCausalTableCell> getCells(){
        final List<MultipleCausalTableCell> cells = new ArrayList<MultipleCausalTableCell>(lines.size() * 2);
        final Iterator<MultipleCausalTableLine> linesIterator = lines.iterator();
        while (linesIterator.hasNext()){
            final MultipleCausalTableLine line = linesIterator.next();
            cells.add(line.getA());
            cells.add(line.getB());
        }//while
        return cells;
    }
    
    /**
     *  returns a list of cells with a given value
     */
    public List<MultipleCausalTableCell> getCells(boolean value){
        final List<MultipleCausalTableCell> cells = new ArrayList<MultipleCausalTableCell>(lines.size() * 2);
        final Iterator<MultipleCausalTableLine> linesIterator = lines.iterator();
        while (linesIterator.hasNext()){
            final MultipleCausalTableLine line = linesIterator.next();
            if (line.getA().isValue() == value){
                cells.add(line.getA());
            }
            
            if (line.getB().isValue() == value){
                cells.add(line.getB());
            }
        }
        return cells;
    }
    
    /**
     *  returns a list of cells with a given value
     */
    public List<MultipleCausalTableCell> getTrueCellsWithOrsNotRealized(){
        final List<MultipleCausalTableCell> cells = new ArrayList<MultipleCausalTableCell>(lines.size() * 2);
        final Iterator<MultipleCausalTableLine> linesIterator = lines.iterator();
        while (linesIterator.hasNext()){
            final MultipleCausalTableLine line = linesIterator.next();
            if (line.getA().isValue() && line.getA().orsNotRealized()){
                cells.add(line.getA());
            }
            
            if (line.getB().isValue() && line.getB().orsNotRealized()){
                cells.add(line.getB());
            }
        }
        return cells;
    }
    
    /**
     *  returns a cell with the specific coordinates
     */
    public MultipleCausalTableCell getCell(int x, int y){
        if (x < 0){
            return null;
        }
        
        if (x >= lines.size()){
            return null;
        }
        
        if (y < 0){
            return null;
        }
        
        if (y > 1){
            return null;
        }
        
        MultipleCausalTableLine line = lines.get(x);
        MultipleCausalTableCell cell;
        if (y == 0){
            cell =  line.getA();
        }
        else {
            cell = line.getB();
        }
        return cell;
    }
    
    /**
     *  returns a list of all neighbors for a cell
     */
    public List<MultipleCausalTableCell> getNeighborsForCell(final MultipleCausalTableCell cell){
        final List<MultipleCausalTableCell> n = new ArrayList<MultipleCausalTableCell>();
        final int x = cell.getX();
        final int y = cell.getY();
        
        for (int i = 0; i < lines.size(); i++){
               final MultipleCausalTableLine line = lines.get(i);
               final MultipleCausalTableCell a = line.getA();
               final MultipleCausalTableCell b = line.getB();
               
               if (getDifferenceFactors(cell, a).size() == 1){
                   n.add(a);
               }
               
               if (getDifferenceFactors(cell, b).size() == 1){
                   n.add(b);
               }           
        }
        
        return n;
    }
    
    /**
     *  Get all difference factors for two cells.
     * @param a
     * @param b
     * @return 
     */
    public List<Factor> getDifferenceFactors(MultipleCausalTableCell a, MultipleCausalTableCell b){
        final List<Factor> list = new ArrayList<Factor>();
        final List<Factor> aAnds = a.getAnds();
        final List<Factor> bAnds = b.getAnds();
        final Iterator<Factor> i = aAnds.iterator();
        while (i.hasNext()){
            Factor node = i.next();
            
            if (!bAnds.contains(node)){
               list.add(node.copy());
            }
        }//while
        
        //difference could be in the top factor.
        if (!a.getTop().equals(b.getTop())){
            list.add(a.getTop().copy());
        }
        
        return list;
    }
    
     /**
     *  this method returns the different factor for two neighbors
     */
    public Factor getDifferentFactor(final MultipleCausalTableCell a, final MultipleCausalTableCell b){
        Factor differentFactor = null;
        
        //variante 1, in den ands von a, aber nicht in den ands von b
        final List<Factor> aAnds = a.getAnds();
        final List<Factor> bAnds = b.getAnds();
        final Iterator<Factor> i = aAnds.iterator();
        while (i.hasNext()){
            final Factor node = i.next();
            if (!bAnds.contains(node)){
                differentFactor = node.copy();
            }
        }//while
        
        if (differentFactor != null){
            return differentFactor;
        }
        
        if (!a.getTop().equals(b.getTop())){
            differentFactor = a.getTop().copy();
        }
        
        return differentFactor;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.lines != null ? this.lines.hashCode() : 0);
        return hash;
    }
}
