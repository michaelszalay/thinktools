package com.szalay.thinktools.ui.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class CopyLogAction implements EventHandler<ActionEvent> {

    private final TextArea textArea;

    public CopyLogAction(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(textArea.getText());
        clipboard.setContent(content);
    }
}
