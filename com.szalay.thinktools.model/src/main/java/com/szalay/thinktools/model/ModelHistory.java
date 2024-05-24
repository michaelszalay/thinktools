package com.szalay.thinktools.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * The history of a model.
 */
public class ModelHistory implements Serializable {
    private static final long serialVersionUID = -473253958244748497L;
    
    private final List<ModelHistoryItem> items = new ArrayList<ModelHistoryItem>();
    private int lastVersion = 0;
    
     /**
     *  add a model to the history
     */
    public void add(final CausalModel model, final String comment){
        ModelHistoryItem item = new ModelHistoryItem(model.copy(), new Date(), lastVersion, comment);
        items.add(item);
        lastVersion++;
    }
    
    /**
     *  Gets the history.
     * @return 
     */
    public List<ModelHistoryItem> getHistory() {
        return items;
    }
    
    /**
     *  deletes all items
     */
    public void emptyHistory(){
        items.clear();
    }
    
     /**
     *  deletes a single item
     */
    public void remove(final ModelHistoryItem item){
        if (item == null){
            return;
        }
        
        final Iterator<ModelHistoryItem> i = items.iterator();
        while (i.hasNext()) {
            final ModelHistoryItem it = i.next();
            if (it.getVersion() == item.getVersion()){
                i.remove();
            }
        }//while
    }
}
