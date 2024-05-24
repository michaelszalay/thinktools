package com.szalay.thinktools.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A project is a container for everything needed to work.
 */
public class Project implements Serializable {
    private static final long serialVersionUID = 4429752876477217526L;
    
    /** Factors. */
    private final List<Factor> factors = new ArrayList<Factor>();
    
    /** Data model. */
    private final DataModel dataModel = new DataModel();
    
    /** Causal model. */
    private final CausalModel causalModel = new CausalModel();
    
    /** Name of the project. */
    private final String name;
    
    /** Description. */
    private String description;

    /** Comments. */
    private final List<Comment> comments = new ArrayList<Comment>();
    
    /**
     * Create a new project for a specific name.
     */
    public Project(String name) {
        this.name = name;
    }

    /**
     * @return the factors
     */
    public List<Factor> getFactors() {
        return factors;
    }

    /**
     * @return the dataModel
     */
    public DataModel getDataModel() {
        return dataModel;
    }

    /**
     * @return the causalModel
     */
    public CausalModel getCausalModel() {
        return causalModel;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the comments
     */
    public List<Comment> getComments() {
        Collections.sort(comments);
        return comments;
    }
}
