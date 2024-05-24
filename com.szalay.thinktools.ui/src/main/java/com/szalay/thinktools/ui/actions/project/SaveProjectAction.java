package com.szalay.thinktools.ui.actions.project;

import com.szalay.thinktools.ui.ApplicationDocument;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SaveProjectAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument document;

    public SaveProjectAction(ApplicationDocument document) {
        this.document = document;
    }

    @Override
    public void handle(ActionEvent event) {
        document.save();
    }
}
