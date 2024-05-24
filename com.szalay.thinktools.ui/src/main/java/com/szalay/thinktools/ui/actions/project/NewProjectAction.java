package com.szalay.thinktools.ui.actions.project;

import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class NewProjectAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument document;

    public NewProjectAction(ApplicationDocument document) {
        this.document = document;
    }

    @Override
    public void handle(ActionEvent event) {

        final TextInputDialog td = new TextInputDialog();
        td.setTitle(TextUtil.getText("newButton"));
        td.setContentText(TextUtil.getText("enterProjectName"));
        td.setHeaderText(TextUtil.getText("newButton"));

        final Optional<String> projectName = td.showAndWait();
        projectName.ifPresent(document::doNew);
    }
}
