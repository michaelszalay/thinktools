package com.szalay.thinktools.ui.tabs.datamodel;

import com.szalay.thinktools.model.Factor;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

final class AddRowDialog extends Dialog<ButtonType> {

    private final Map<Factor, Supplier<String>> result = new HashMap<>();

    AddRowDialog(final ApplicationDocument document) {
        super();

        getDialogPane().setPrefSize(600, 400);
        setTitle(TextUtil.getText("addDataRow"));

        final VBox layout = new VBox();

        final List<Factor> factors = document.getProject().getFactors();
        final GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        int row = 0;
        for (final Factor factor : factors) {

            final Label label = new Label(factor.getName());
            grid.add(label, 0, row);

            final TextField value = new TextField();
            grid.add(value, 1, row);

            result.put(factor, value::getText);
            row++;
        }
        layout.getChildren().add(grid);

        getDialogPane().setContent(layout);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    Map<Factor, Supplier<String>> getNewRow() {
        return result;
    }
}
