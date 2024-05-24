package com.szalay.thinktools.ui.tabs.hypothesis;

import com.szalay.thinktools.model.CausalHypothesis;
import com.szalay.thinktools.ui.ApplicationDocument;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class RemoveHypothesisAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument document;
    private final CausalHypothesis hypothesis;

    public RemoveHypothesisAction(ApplicationDocument document, CausalHypothesis hypothesis) {
        this.document = document;
        this.hypothesis = hypothesis;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        document.getProject().getCausalModel().remove(hypothesis);
        document.setProjectModified(true);
    }
}
