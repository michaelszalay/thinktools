package com.szalay.thinktools.ui.actions.project;

import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class OpenProjectAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument document;
    private final Stage stage;

    public OpenProjectAction(ApplicationDocument document, Stage stage) {
        this.document = document;
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(TextUtil.getText("selectProjectFileTitle"));
        final File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            document.open(file);
        }
    }
}
