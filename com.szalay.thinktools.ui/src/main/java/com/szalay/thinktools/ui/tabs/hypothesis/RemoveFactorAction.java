package com.szalay.thinktools.ui.tabs.hypothesis;

import com.szalay.thinktools.model.ComplexCause;
import com.szalay.thinktools.model.Factor;
import com.szalay.thinktools.ui.ApplicationDocument;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class RemoveFactorAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument document;
    private final Factor f;
    private final ComplexCause c;

    public RemoveFactorAction(ApplicationDocument document, Factor f, ComplexCause c) {
        this.document = document;
        this.f = f;
        this.c = c;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        c.remove(f);
        document.setProjectModified(true);
    }
}
