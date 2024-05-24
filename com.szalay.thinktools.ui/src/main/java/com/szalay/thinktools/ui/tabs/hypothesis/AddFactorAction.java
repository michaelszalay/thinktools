package com.szalay.thinktools.ui.tabs.hypothesis;

import com.szalay.thinktools.model.ComplexCause;
import com.szalay.thinktools.model.Factor;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

import java.util.Optional;

final class AddFactorAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument document;
    private final ComplexCause complexCause;

    public AddFactorAction(ApplicationDocument document, ComplexCause complexCause) {
        this.document = document;
        this.complexCause = complexCause;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        final Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(TextUtil.getText("addFactorDialogTitle"));

        final VBox layout = new VBox();
        dialog.getDialogPane().setContent(layout);

        final ChoiceBox<Factor> choiceBox = new ChoiceBox<>();
        choiceBox.setItems(FXCollections.observableList(document.getProject().getFactors()));
        layout.getChildren().add(choiceBox);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);

        final Optional<String> comment = dialog.showAndWait();
        if (comment.isPresent()) {
            final Factor f = choiceBox.getValue();
            if (f != null) {
                complexCause.add(f);
                document.setProjectModified(true);
                dialog.close();
            }
        }
    }
}
