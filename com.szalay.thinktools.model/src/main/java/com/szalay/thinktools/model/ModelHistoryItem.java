package com.szalay.thinktools.model;

import java.io.Serializable;
import java.util.Date;

/**
 * An element in the model history.
 */
public class ModelHistoryItem implements Serializable {
    private static final long serialVersionUID = 3994894613397401619L;
    private final CausalModel model;
    private final Date timestamp;
    private final int version;
    private final String comment;

    public ModelHistoryItem(CausalModel model, Date timestamp, int version, String comment) {
        this.model = model;
        this.timestamp = timestamp;
        this.version = version;
        this.comment = comment;
    }

    /**
     * @return the model
     */
    public CausalModel getModel() {
        return model;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }
    
    @Override
    public String toString() {
        return version + ", " + comment;
    }
}
