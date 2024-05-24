package com.szalay.thinktools.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A data individual.
 */
public class Individual extends ArrayList<DataProperty> implements List<DataProperty> {
    private static final long serialVersionUID = -1752668135912620438L;
    
    /**
     *  Get a data property by name.
     * @param name
     * @return 
     */
    public DataProperty getPropertyByName(final String name) {
        final Iterator<DataProperty> properties = iterator();
        while (properties.hasNext()) {
            final DataProperty p = properties.next();
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }
}
