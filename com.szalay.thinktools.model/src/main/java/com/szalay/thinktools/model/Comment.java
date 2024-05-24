package com.szalay.thinktools.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A comment on a project.
 */
public class Comment implements Serializable, Comparable<Comment> {
    private static final long serialVersionUID = -7560223259947675326L;
    
    /** The comment. */
    private final String comment;
    
    /** The date. */
    private final Date date;

    /**
     * Create a new comment.
     * @param comment
     * @param date 
     */
    public Comment(String comment) {
        this.comment = comment;
        this.date = new Date();
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(Comment o) {
        return -(date.compareTo(o.date));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment1 = (Comment) o;
        return Objects.equals(comment, comment1.comment) && Objects.equals(date, comment1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comment, date);
    }
}
