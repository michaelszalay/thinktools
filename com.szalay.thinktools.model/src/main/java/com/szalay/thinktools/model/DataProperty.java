package com.szalay.thinktools.model;

import java.io.Serializable;

/**
 * A data property.
 */
public class DataProperty implements Serializable {
    private static final long serialVersionUID = 5446059648756265867L;
    private final String name;
    private final Serializable value;

    public DataProperty(String name, Serializable value) {
        this.name = name;
        this.value = value;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the value
     */
    public Serializable getValue() {
        return value;
    }
}
