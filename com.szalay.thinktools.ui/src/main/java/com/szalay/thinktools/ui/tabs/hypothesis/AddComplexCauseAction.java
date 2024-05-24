package com.szalay.thinktools.ui.tabs.hypothesis;

import com.szalay.thinktools.model.CausalHypothesis;
import com.szalay.thinktools.model.ComplexCause;
import com.szalay.thinktools.ui.ApplicationDocument;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

final class AddComplexCauseAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument document;
    private final CausalHypothesis hypothesis;

    public AddComplexCauseAction(ApplicationDocument document, CausalHypothesis hypothesis) {
        this.document = document;
        this.hypothesis = hypothesis;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        final ComplexCause c = new ComplexCause();
        this.hypothesis.getComplexCauses().add(c);
        this.document.setProjectModified(true);
    }
}
