package com.szalay.thinktools.ui.components;

import com.szalay.thinktools.ui.TextUtil;
import com.szalay.thinktools.ui.UIUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveLogAction implements EventHandler<ActionEvent> {

    private final TextArea textArea;
    private final Stage stage;

    public SaveLogAction(TextArea textArea, Stage stage) {
        this.textArea = textArea;
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(TextUtil.getText("selectLogFile"));

        final File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                final PrintWriter out = new PrintWriter(file.getAbsolutePath());
                out.println(textArea.getText());
                out.close();
            }
            catch (IOException ioe) {
                UIUtil.showError(TextUtil.getText("errorSavingLog"), ioe.toString());
            }
        }
    }
}
