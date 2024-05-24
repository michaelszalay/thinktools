package com.szalay.thinktools.ui.tabs.factor;

import com.szalay.thinktools.model.Factor;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class NewFactorAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument document;

    public NewFactorAction(ApplicationDocument document) {
        this.document = document;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        final Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(TextUtil.getText("newFactorDialogTitle"));

        final GridPane layout = new GridPane();
        dialog.getDialogPane().setContent(layout);

        layout.add(new Label(TextUtil.getText("factorNameLabel")), 1, 0);
        final TextField nodeNameField = new TextField();
        layout.add(nodeNameField, 2, 0);

        layout.add(new Label(TextUtil.getText("lowerRangeLabel")), 1, 1);
        final TextField lowerRangeField = new TextField();
        layout.add(lowerRangeField, 2, 1);

        layout.add(new Label(TextUtil.getText("upperRangeLabel")), 1, 2);
        final TextField upperRangeField = new TextField();
        layout.add(upperRangeField, 2, 2);

        layout.add(new Label(TextUtil.getText("logicalValueLabel")), 1, 3);
        final CheckBox logicalValueField = new CheckBox();
        layout.add(logicalValueField, 2, 3);

        final ButtonType addFactorButtonType = new ButtonType(TextUtil.getText("addFactorButton"), ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(addFactorButtonType);

        final Optional<String> comment = dialog.showAndWait();
        if (comment.isPresent()) {
            final Factor factor = new Factor(nodeNameField.getText(), lowerRangeField.getText(), upperRangeField.getText());
            factor.setLogicalValue(logicalValueField.isSelected());

            document.getProject().getFactors().add(factor);
            document.setProjectModified(true);
        }
    }
}
