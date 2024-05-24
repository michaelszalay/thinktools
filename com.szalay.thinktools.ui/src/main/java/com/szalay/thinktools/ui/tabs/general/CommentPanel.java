package com.szalay.thinktools.ui.tabs.general;

import com.szalay.thinktools.model.Comment;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

final class CommentPanel extends VBox {
    public CommentPanel(Comment c, ApplicationDocument document) {
        final HBox hBox = new HBox();
        hBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: black;");

        hBox.getChildren().add(new Label(c.getDate().toString() + ": "));
        hBox.getChildren().add(new Label(c.getComment()));

        final Button deleteButton = new Button(TextUtil.getText("deleteCommentButton"));
        deleteButton.setOnAction(actionEvent -> {
            document.getProject().getComments().remove(c);
            document.setProjectModified(true);
        });
        hBox.getChildren().add(deleteButton);

        getChildren().add(hBox);
    }
}
