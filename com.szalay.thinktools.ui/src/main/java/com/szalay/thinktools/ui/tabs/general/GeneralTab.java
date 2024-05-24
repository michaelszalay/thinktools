package com.szalay.thinktools.ui.tabs.general;

import com.szalay.thinktools.model.Comment;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.ApplicationDocumentListener;
import com.szalay.thinktools.ui.TextUtil;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GeneralTab extends Tab implements ApplicationDocumentListener {
    private final TextField projectNameTextField;

    private final Button addCommentButton;

    private final VBox commentsMainBox;

    public GeneralTab(final ApplicationDocument document) {
        super(TextUtil.getText("generalTabName"));

        final VBox layout = new VBox();
        layout.setSpacing(20);

        final HBox projectName = new HBox();
        projectName.getChildren().add(new Label(TextUtil.getText("projectNameLabel")));

        projectNameTextField = new TextField();
        projectNameTextField.setPrefColumnCount(30);
        projectNameTextField.setEditable(false);
        projectName.getChildren().add(this.projectNameTextField);

        layout.getChildren().add(projectName);

        commentsMainBox = new VBox();
        addCommentButton = new Button(TextUtil.getText("addCommentButton"));
        addCommentButton.setOnAction(new AddCommentAction(document));
        layout.getChildren().add(commentsMainBox);
        layout.getChildren().add(addCommentButton);

        setContent(layout);
    }

    @Override
    public void changed(ApplicationDocument document) {
        addCommentButton.setDisable(document.getProject() == null);

        if (document.getProject() == null) {
            projectNameTextField.setText("");
            commentsMainBox.getChildren().removeAll();
            return;
        }

        if (document.getProject() != null && document.getProject().getName() != null) {
            projectNameTextField.setText(document.getProject().getName());
        }

        commentsMainBox.getChildren().removeAll();
        for (final Comment c : document.getProject().getComments()) {
            commentsMainBox.getChildren().add(new CommentPanel(c, document));
        }
    }
}
