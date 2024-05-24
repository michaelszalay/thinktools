package com.szalay.thinktools.ui.tabs.datamodel;

import com.szalay.thinktools.io.DataModelIO;
import com.szalay.thinktools.model.DataModel;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import com.szalay.thinktools.ui.UIUtil;
import com.szalay.thinktools.ui.components.ProgressLogger;
import com.szalay.thinktools.ui.components.ProgressWindow;
import com.szalay.thinktools.ui.components.ProgressWindowService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ImportCSVDataAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument document;
    private final Stage stage;

    public ImportCSVDataAction(ApplicationDocument document, Stage stage) {
        this.document = document;
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent event) {
        if (document.getProject() == null) {
            return;
        }

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(TextUtil.getText("selectCSVDataFile"));
        final File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                final ProgressWindowService importService = new ProgressWindowService() {
                    @Override
                    public void start(ProgressLogger logger) {
                        try {
                            logger.logInfo("Importing " + file.getAbsolutePath());

                            final DataModel dataModel = DataModelIO.importFromCSV(file);
                            logger.setProgress(50L);

                            Platform.runLater(() -> {
                                document.getProject().getDataModel().clear();
                                document.getProject().getDataModel().addAll(dataModel);
                                document.setProjectModified(true);
                            });

                            logger.setProgress(100L);
                            logger.logInfo("Done");
                        }
                        catch (Exception e) {
                            logger.logError(e.toString());
                        }
                    }

                    @Override
                    public String getTitle() {
                        return TextUtil.getText("selectCSVDataFile");
                    }
                };

                final ProgressWindow window = new ProgressWindow(importService, stage);
                window.run(() -> {});
            }
            catch (Exception e) {
                UIUtil.showError(TextUtil.getText("errorImportingCSV"), e.getMessage());
            }
        }
    }
}
