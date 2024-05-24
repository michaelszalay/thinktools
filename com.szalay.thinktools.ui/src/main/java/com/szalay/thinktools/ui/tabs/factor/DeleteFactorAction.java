package com.szalay.thinktools.ui.tabs.factor;

import com.szalay.thinktools.model.Factor;
import com.szalay.thinktools.ui.ApplicationDocument;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;

public class DeleteFactorAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument document;
    private final TableView<Factor> factorTable;

    public DeleteFactorAction(ApplicationDocument doc, TableView<Factor> factorTable) {
        this.document = doc;
        this.factorTable = factorTable;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (document.getProject() == null) {
            return;
        }

        final ObservableList<Factor> selectedItems = factorTable.getSelectionModel().getSelectedItems();
        if (selectedItems == null) {
            return;
        }

        for (final Factor f : selectedItems) {
            document.getProject().getFactors().remove(f);
        }

        document.setProjectModified(true);
    }
}
