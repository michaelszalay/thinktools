package com.szalay.thinktools.ui.tabs.datamodel;

import com.szalay.thinktools.model.Individual;
import com.szalay.thinktools.ui.ApplicationDocument;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;

public class DeleteRowsAction implements EventHandler<ActionEvent> {

    private final TableView.TableViewSelectionModel<Individual> selectionModel;
    private final ApplicationDocument document;

    public DeleteRowsAction(TableView.TableViewSelectionModel<Individual> selectionModel, ApplicationDocument document) {
        this.selectionModel = selectionModel;
        this.document = document;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        final ObservableList<Individual> selectedItems = selectionModel.getSelectedItems();

        document.getProject().getDataModel().removeAll(selectedItems);
        document.setProjectModified(true);
    }
}
