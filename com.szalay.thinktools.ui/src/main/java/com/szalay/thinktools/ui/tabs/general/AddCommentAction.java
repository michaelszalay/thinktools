package com.szalay.thinktools.ui.tabs.general;

import com.szalay.thinktools.model.Comment;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;

import java.util.Optional;

final class AddCommentAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument document;

    public AddCommentAction(ApplicationDocument document) {
        this.document = document;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (document.getProject() == null) {
            return;
        }

        final ButtonType addCommentButtonType = new ButtonType(TextUtil.getText("commentDialogButton"), ButtonBar.ButtonData.OK_DONE);
        final Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(TextUtil.getText("commentDialogTitle"));
        final TextArea textArea = new TextArea();

        dialog.getDialogPane().getButtonTypes().add(addCommentButtonType);
        dialog.getDialogPane().setContent(textArea);

        final Optional<String> comment = dialog.showAndWait();
        if (comment.isPresent()) {
            final String c = textArea.getText();
            if (c != null) {
                final Comment commentObj = new Comment(c);
                document.getProject().getComments().add(commentObj);
                document.setProjectModified(true);
            }
        }
    }
}
