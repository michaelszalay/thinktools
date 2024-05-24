package com.szalay.thinktools.model;

/**
 * A causal table is a table with 4 elements which are true or false.
 */
public class CausalTable {
    private final boolean a;
    private final boolean b;
    private final boolean c;
    private final boolean d;

    public CausalTable(boolean a, boolean b, boolean c, boolean d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * @return the a
     */
    public boolean isA() {
        return a;
    }

    /**
     * @return the b
     */
    public boolean isB() {
        return b;
    }

    /**
     * @return the c
     */
    public boolean isC() {
        return c;
    }

    /**
     * @return the d
     */
    public boolean isD() {
        return d;
    }
    
    @Override
    public String toString() {
        int i_a = 0;
        int i_b = 0;
        int i_c = 0;
        int i_d = 0;

        if (a) {
            i_a = 1;
        }

        if (b) {
            i_b = 1;
        }

        if (c) {
            i_c = 1;
        }

        if (d) {
            i_d = 1;
        }

        String result = new String();
        result += i_a + ", " + i_b + ", ";
        result += i_c + ", " + i_d;
        return result;
    }
}
