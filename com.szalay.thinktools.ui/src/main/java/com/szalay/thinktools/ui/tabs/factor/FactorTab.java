package com.szalay.thinktools.ui.tabs.factor;

import com.szalay.thinktools.model.Factor;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.ApplicationDocumentListener;
import com.szalay.thinktools.ui.TextUtil;
import com.szalay.thinktools.ui.ToolbarFactory;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class FactorTab extends Tab implements ApplicationDocumentListener {
    public FactorTab() {
        super(TextUtil.getText("factorTabTitle"));
    }

    @Override
    public void changed(ApplicationDocument document) {
        if (document.getProject() == null) {
            return;
        }

        final VBox layout = new VBox();

        final TableView<Factor> factors = new TableView<>();
        layout.getChildren().add(createFactorToolbar(factors, document));

        factors.setItems(FXCollections.observableList(document.getProject().getFactors()));

        final TableColumn<Factor, String> factorNameColumn = new TableColumn<>();
        factorNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        factorNameColumn.setText("Name");

        final TableColumn<Factor, String> upperRangeColumn = new TableColumn<>();
        upperRangeColumn.setCellValueFactory(new PropertyValueFactory<>("upperRangeValue"));
        upperRangeColumn.setText("Upper range");

        final TableColumn<Factor, String> lowerRangeColumn = new TableColumn<>();
        lowerRangeColumn.setCellValueFactory(new PropertyValueFactory<>("lowerRangeValue"));
        lowerRangeColumn.setText("Lower range");

        final TableColumn<Factor, Boolean> booleanRangeColumn = new TableColumn<>();
        booleanRangeColumn.setCellValueFactory(new PropertyValueFactory<>("logicalValue"));
        booleanRangeColumn.setText("Logical value");

        factors.getColumns().addAll(factorNameColumn, upperRangeColumn, lowerRangeColumn, booleanRangeColumn);
        layout.getChildren().add(factors);

        setContent(layout);
    }

    private ToolBar createFactorToolbar(final TableView<Factor> factorTable, final ApplicationDocument doc) {
        final ToolBar toolBar = new ToolBar();

        final Button newButton = new Button();
        newButton.setGraphic(ToolbarFactory.createImage("faktor.gif"));
        newButton.setTooltip(new Tooltip(TextUtil.getText("newFactorButton")));
        newButton.setOnAction(new NewFactorAction(doc));
        toolBar.getItems().add(newButton);

        final Button addFactorsFromDataModel = new Button();
        addFactorsFromDataModel.setGraphic(ToolbarFactory.createImage("createfactors.gif"));
        addFactorsFromDataModel.setTooltip(new Tooltip(TextUtil.getText("addFactorsFromModelButton")));
        addFactorsFromDataModel.setOnAction(new AddFromDataModelAction(doc));
        toolBar.getItems().add(addFactorsFromDataModel);

        final Button deleteButton = new Button();
        deleteButton.setGraphic(ToolbarFactory.createImage("removefactor.gif"));
        deleteButton.setTooltip(new Tooltip(TextUtil.getText("deleteFactorButton")));
        deleteButton.setOnAction(new DeleteFactorAction(doc, factorTable));
        toolBar.getItems().add(deleteButton);

        return toolBar;
    }
}
