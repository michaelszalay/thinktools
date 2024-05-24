package com.szalay.thinktools.model;

/**
 * Observer for a model.
 */
public interface CausalModelOberserver {
    /**
     *  this method is called when the model generator wants to print out a message
     */
    void print (String msg);
    
    /**
     *  this method is called when the model generator wants to pause
     */
    void pause();

    /** 
     *  this method is called when the model has changed
     */
    void update(CausalModel model);
}
