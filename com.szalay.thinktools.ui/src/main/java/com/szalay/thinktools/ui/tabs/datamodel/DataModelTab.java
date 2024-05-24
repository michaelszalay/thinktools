package com.szalay.thinktools.ui.tabs.datamodel;

import com.szalay.thinktools.model.DataModel;
import com.szalay.thinktools.model.DataProperty;
import com.szalay.thinktools.model.Individual;
import com.szalay.thinktools.model.Project;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.ApplicationDocumentListener;
import com.szalay.thinktools.ui.TextUtil;
import com.szalay.thinktools.ui.ToolbarFactory;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.Serializable;

@SuppressWarnings("rawtypes")
public class DataModelTab extends Tab implements ApplicationDocumentListener {

    private final Stage stage;

    public DataModelTab(Stage stage) {
        super(TextUtil.getText("dataModelTab"));
        this.stage = stage;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void changed(ApplicationDocument document) {
        final Project project = document.getProject();
        if (project == null) {
            return;
        }

        final DataModel dataModel = project.getDataModel();
        if (dataModel == null) {
            return;
        }

        final TableView<Individual> tableView = new TableView<>();
        tableView.setItems(FXCollections.observableList(dataModel));
        tableView.setEditable(true);

        if (!dataModel.isEmpty()) {
            final Individual i = dataModel.get(0);
            for (final DataProperty p : i) {
                final TableColumn column = new TableColumn(p.getName());
                column.setCellValueFactory((Callback<TableColumn.CellDataFeatures, ObservableValue>) cellDataFeatures -> new ObservableStringValue() {
                    @Override
                    public String get() {
                        return cellDataFeatures.getValue().toString();
                    }

                    @Override
                    public void addListener(ChangeListener<? super String> changeListener) {

                    }

                    @Override
                    public void removeListener(ChangeListener<? super String> changeListener) {

                    }

                    @Override
                    public String getValue() {
                        final Individual i1 = (Individual) cellDataFeatures.getValue();
                        final String name = p.getName();
                        if (name == null) {
                            return null;
                        }

                        final DataProperty propertyByName = i1.getPropertyByName(name);
                        if (propertyByName == null) {
                            return null;
                        }

                        final Serializable value = propertyByName.getValue();
                        if (value == null) {
                            return null;
                        }

                        return String.valueOf(value);
                    }

                    @Override
                    public void addListener(InvalidationListener invalidationListener) {

                    }

                    @Override
                    public void removeListener(InvalidationListener invalidationListener) {

                    }
                });
                tableView.getColumns().add(column);
            }
        }

        final TableView.TableViewSelectionModel<Individual> selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        final ObservableList<Individual> selectedItems = selectionModel.getSelectedItems();
        selectedItems.addListener((ListChangeListener<Individual>) change -> {

            final ContextMenu contextMenu = new ContextMenu();

            final MenuItem deleteRows = new MenuItem(TextUtil.getText("deleteRows"));
            deleteRows.setOnAction(new DeleteRowsAction(selectionModel, document));
            contextMenu.getItems().add(deleteRows);

            tableView.setContextMenu(contextMenu);
        });

        final VBox layout = new VBox();
        layout.getChildren().add(createDataToolbar(document, stage, tableView));
        layout.getChildren().add(tableView);
        setContent(layout);
    }

    private Node createDataToolbar(final ApplicationDocument doc, final Stage stage, final TableView<Individual> tableView) {
        final ToolBar toolBar = new ToolBar();

        final Button refreshButton = new Button();
        refreshButton.setGraphic(ToolbarFactory.createImage("refresh.png"));
        refreshButton.setTooltip(new Tooltip(TextUtil.getText("refresh")));
        refreshButton.setOnAction(actionEvent -> tableView.setItems(FXCollections.observableList(doc.getProject().getDataModel())));
        toolBar.getItems().add(refreshButton);

        final Button openCSVButton = new Button();
        openCSVButton.setGraphic(ToolbarFactory.createImage("load_table.gif"));
        openCSVButton.setTooltip(new Tooltip(TextUtil.getText("importCSVButton")));
        openCSVButton.setOnAction(new ImportCSVDataAction(doc, stage));
        toolBar.getItems().add(openCSVButton);

        final Button countButton = new Button();
        countButton.setGraphic(ToolbarFactory.createImage("count.gif"));
        countButton.setTooltip(new Tooltip(TextUtil.getText("countButton")));
        countButton.setOnAction(new CountAction(doc));
        toolBar.getItems().add(countButton);

        final Button loadFromDbButton = new Button();
        loadFromDbButton.setGraphic(ToolbarFactory.createImage("dboptions.gif"));
        loadFromDbButton.setTooltip(new Tooltip(TextUtil.getText("importFromDatabaseButton")));
        loadFromDbButton.setOnAction(new ImportDatabaseAction(stage, doc));
        toolBar.getItems().add(loadFromDbButton);

        final Button addDataRowButton = new Button();
        addDataRowButton.setGraphic(ToolbarFactory.createImage("add_hyp.gif"));
        addDataRowButton.setTooltip(new Tooltip(TextUtil.getText("addDataRow")));
        addDataRowButton.setOnAction(new AddRowAction(doc));
        toolBar.getItems().add(addDataRowButton);

        return toolBar;
    }
}
