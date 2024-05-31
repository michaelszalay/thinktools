package com.szalay.thinktools.ui.tabs.datamodel;

import com.szalay.thinktools.io.db.DataModelDBIO;
import com.szalay.thinktools.io.db.DataModelIOImpl;
import com.szalay.thinktools.model.DataModel;
import com.szalay.thinktools.model.Factor;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import com.szalay.thinktools.ui.UIUtil;
import com.szalay.thinktools.ui.components.ProgressLogger;
import com.szalay.thinktools.ui.components.ProgressWindow;
import com.szalay.thinktools.ui.components.ProgressWindowService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class ImportDatabaseAction implements EventHandler<ActionEvent> {

    private final Stage stage;
    private final ApplicationDocument document;

    public ImportDatabaseAction(Stage stage, ApplicationDocument document) {
        this.stage = stage;
        this.document = document;
    }

    @Override
    public void handle(ActionEvent actionEvent) {

        final List<Factor> factors = document.getProject().getFactors();
        if (factors.isEmpty()) {
            UIUtil.showError(TextUtil.getText("error"), TextUtil.getText("factorsErrorMessage"));
            return;
        }

        final DataModelDBIO dbio = new DataModelIOImpl();
        final ImportDatabaseDialog dlg = new ImportDatabaseDialog(dbio, stage, document);
        final Optional<ButtonType> buttonType = dlg.showAndWait();
        if (buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {

            final ProgressWindowService service = new ProgressWindowService() {
                @Override
                public void start(ProgressLogger logger) {
                    final DataModel dataModel = dbio.loadDataModelFromQuery(dlg.getDirectory(),
                            dlg.getDatabaseSettings(),
                            dlg.getQuery(),
                            dlg.getFactorColumnMapping());

                    if (dlg.isOverride()) {
                        document.getProject().getDataModel().clear();
                    }

                    document.getProject().getDataModel().addAll(dataModel);
                    document.setProjectModified(true);
                }

                @Override
                public String getTitle() {
                    return TextUtil.getText("importFromDatabaseButton");
                }
            };

            final ProgressWindow w = new ProgressWindow(service, stage);
            w.run(() -> {});
        }
    }
}
