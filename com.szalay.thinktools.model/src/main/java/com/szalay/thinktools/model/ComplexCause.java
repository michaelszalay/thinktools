package com.szalay.thinktools.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A complex cause is a list of factors.
 */
public class ComplexCause extends ArrayList<Factor> implements List<Factor> {
    private static final long serialVersionUID = 7041731744847532090L;
    
    /**
     *  returns a copy of this complex cause
     */
    public ComplexCause copy(){
        final ComplexCause newComplexCause = new ComplexCause();
        final Iterator<Factor> i = iterator();
        while (i.hasNext()){
            final Factor o = i.next();
            final Factor copy = o.copy();
            newComplexCause.add(copy);
        }//while
        
        return newComplexCause;
    }
    
    /**
     *  containsAsSubject - returns true if the factor in the parameter are contained in the factor of this complex cause
     */
    public boolean containsAsSubset(final ComplexCause c){ 
        if (c.size() > size()){
            return false;
        }
        
        if (equals(c)){
            return true;
        }
        
        final Iterator<Factor> i = c.iterator();
        while (i.hasNext()){
            final Factor factor = i.next();
            if (!contains(factor)){
                return false;
            }
        }//while
        
        return true;
    }
    
    /**
     *  toString
     */
    @Override
    public String toString(){
        final StringBuilder s = new StringBuilder();
        s.append('[');
        for (int i = 0; i < size(); i++){
            final Factor fac = get(i);
            if (fac == null){
                continue;
            }
            
            s.append(fac.toString());
            if (i < (size() -1 )){
                s.append(" & ");
            }
        }//for
        
        s.append(']');
        return s.toString();
    }

}
