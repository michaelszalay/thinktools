package com.szalay.thinktools.ui.tabs.datamodel;

import com.szalay.thinktools.io.db.DBSettings;
import com.szalay.thinktools.io.db.DataModelDBIO;
import com.szalay.thinktools.model.Factor;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import com.szalay.thinktools.ui.UIUtil;
import com.szalay.thinktools.ui.components.ProgressLogger;
import com.szalay.thinktools.ui.components.ProgressWindow;
import com.szalay.thinktools.ui.components.ProgressWindowService;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class ImportDatabaseDialog extends Dialog<ButtonType> {

    private final ComboBox<String> driverClassNames;
    private final TextField urlTextField;
    private final TextField usernameTextField;
    private final TextField pwdTextField;
    private final Map<String, ComboBox<String>> factorColumnMapping = new HashMap<>();
    private final ScrollPane factorsScrollPane = new ScrollPane();
    private final CheckBox overrideCheckbox;
    private final TextField directoryOfJarsTextField;
    private final TextField queryTextField;

    public ImportDatabaseDialog(final DataModelDBIO dbio, final Stage stage, final ApplicationDocument document) {
        super();

        getDialogPane().setPrefSize(1200, 800);
        setTitle(TextUtil.getText("importFromDatabaseButton"));

        final GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        final Label driverLabel = new Label(TextUtil.getText("driverLabel"));
        driverClassNames = new ComboBox<>();
        final Label jarsTextFieldLabel = new Label(TextUtil.getText("directoryLabel"));
        directoryOfJarsTextField = new TextField();
        final Button updateJarsComboboxButton = new Button();
        updateJarsComboboxButton.setText("Search for drivers");
        updateJarsComboboxButton.setOnAction(actionEvent -> {

            final String text = directoryOfJarsTextField.getText();
            if (text != null) {
                final List<String> list = new ArrayList<>();
                final ProgressWindowService service = new ProgressWindowService() {
                    @Override
                    public void start(ProgressLogger logger) {
                        final Set<String> jdbcDriverClasses = dbio.getJDBCDriverClasses(text, logger::logInfo);
                        list.addAll(jdbcDriverClasses);
                    }

                    @Override
                    public String getTitle() {
                        return TextUtil.getText("importFromDatabaseButton");
                    }
                };

                final ProgressWindow w = new ProgressWindow(service, stage);
                w.run(() -> driverClassNames.setItems(FXCollections.observableList(list)));
            }
        });

        grid.add(driverLabel, 0, 0);
        grid.add(driverClassNames, 1, 0);
        grid.add(jarsTextFieldLabel, 2, 0);
        grid.add(directoryOfJarsTextField, 3, 0);
        grid.add(updateJarsComboboxButton, 4, 0);

        urlTextField = new TextField();

        grid.add(new Label(TextUtil.getText("urlLabel")), 0, 1);
        grid.add(urlTextField, 1, 1);

        usernameTextField = new TextField();

        grid.add(new Label(TextUtil.getText("userLabel")), 0, 2);
        grid.add(usernameTextField, 1, 2);

        pwdTextField = new TextField();
        grid.add(new Label(TextUtil.getText("passwordLabel")), 0, 3);
        grid.add(pwdTextField, 1, 3);

        queryTextField = new TextField();
        grid.add(new Label(TextUtil.getText("queryLabel")), 0, 4);
        grid.add(queryTextField, 1, 4);

        overrideCheckbox = new CheckBox();
        grid.add(new Label(TextUtil.getText("overrideLabel")), 0, 5);
        grid.add(overrideCheckbox, 1, 5);

        final List<Factor> factors = document.getProject().getFactors();

        final Button connectButton = new Button(TextUtil.getText("connectLabel"));
        connectButton.setOnAction(actionEvent -> {

            final DBSettings settings = getDatabaseSettings();
            final Set<String> columnNames = dbio.getColumnNames(directoryOfJarsTextField.getText(), settings, queryTextField.getText());
            updateColumnNames(columnNames, factors, queryTextField, directoryOfJarsTextField);
            grid.add(factorsScrollPane, 1, 6);
        });
        grid.add(connectButton, 0, 6);

        getDialogPane().setContent(grid);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    private void updateColumnNames(final Set<String> columnNames, List<Factor> factors, TextField queryTextField,
                           TextField directoryOfJarsTextField) {

        final VBox vBox = new VBox();
        if (!columnNames.isEmpty()) {
            for (final Factor f : factors) {

                final HBox factorBox = new HBox();
                factorBox.setSpacing(20);

                final Label label = new Label(f.toString());
                final ComboBox<String> columns = new ComboBox<>();
                try {
                    if (queryTextField.getText() != null && directoryOfJarsTextField.getText() != null) {
                        columns.setItems(FXCollections.observableList(new ArrayList<>(columnNames)));
                    }
                } catch (Exception e) {
                    UIUtil.showError(TextUtil.getText("error"), e.toString());
                }

                factorColumnMapping.put(f.getName(), columns);

                factorBox.getChildren().add(label);
                factorBox.getChildren().add(columns);

                vBox.getChildren().add(factorBox);
            }
        }

        factorsScrollPane.setContent(vBox);
    }

    DBSettings getDatabaseSettings() {

        final DBSettings settings = new DBSettings();
        settings.setUsername(usernameTextField.getText());
        settings.setDriverClassName(driverClassNames.getValue());
        settings.setUrl(urlTextField.getText());
        settings.setPassword(pwdTextField.getText());
        return settings;
    }

    Map<String, String> getFactorColumnMapping() {
        final Map<String, String> result = new HashMap<>();
        for (final Map.Entry<String, ComboBox<String>> f : factorColumnMapping.entrySet()) {
            result.put(f.getKey(), f.getValue().getValue());
        }

        return result;
    }

    boolean isOverride() {
        return overrideCheckbox.isSelected();
    }

    String getDirectory() {
        return directoryOfJarsTextField.getText();
    }

    String getQuery() {
        return queryTextField.getText();
    }
}
