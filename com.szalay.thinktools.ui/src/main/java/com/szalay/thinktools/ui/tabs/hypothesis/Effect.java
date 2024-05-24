package com.szalay.thinktools.ui.tabs.hypothesis;

import com.szalay.thinktools.model.Factor;

final class Effect {
    private final Factor factor;


    Effect(Factor factor) {
        this.factor = factor;
    }

    Factor getFactor() {
        return factor;
    }

    @Override
    public java.lang.String toString() {
        return "Effect: " + factor.toString();
    }
}
