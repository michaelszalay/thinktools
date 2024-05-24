package com.szalay.thinktools.ui.tabs.hypothesis;

import com.szalay.thinktools.model.CausalHypothesis;
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

public class SetEffectAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument document;
    private final CausalHypothesis hypothesis;
    public SetEffectAction(ApplicationDocument document, CausalHypothesis hypothesis) {
        this.document = document;
        this.hypothesis = hypothesis;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        final Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(TextUtil.getText("setEffectDialogTitle"));

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
                hypothesis.setEffect(f);
                document.setProjectModified(true);
                dialog.close();
            }
        }
    }
}

