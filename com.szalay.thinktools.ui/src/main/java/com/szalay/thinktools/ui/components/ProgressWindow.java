package com.szalay.thinktools.ui.components;

import com.szalay.thinktools.ui.TextUtil;
import com.szalay.thinktools.ui.ToolbarFactory;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProgressWindow extends Dialog<ButtonType> implements ProgressLogger {

    private final ProgressWindowService service;
    private final ProgressBar progressBar;
    private final TextArea textArea;

    public ProgressWindow(final ProgressWindowService service, final Stage stage) {
        this.service = service;

        getDialogPane().setPrefSize(1200, 800);

        setTitle(service.getTitle());

        final VBox layout = new VBox();
        layout.setSpacing(50);

        this.progressBar = new ProgressBar();
        this.progressBar.setMinWidth(getDialogPane().getPrefWidth());
        this.textArea = new TextArea();

        final ScrollPane textAreaScroller = new ScrollPane();
        textAreaScroller.setContent(textArea);

        layout.getChildren().add(progressBar);
        layout.getChildren().add(textAreaScroller);
        layout.getChildren().add(createToolbar(stage));

        getDialogPane().setContent(layout);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK);
    }

    private Node createToolbar(final Stage stage) {
        final ToolBar toolBar = new ToolBar();

        final Button saveTextButton = new Button();
        saveTextButton.setGraphic(ToolbarFactory.createImage("save.gif"));
        saveTextButton.setTooltip(new Tooltip(TextUtil.getText("saveLogButton")));
        saveTextButton.setOnAction(new SaveLogAction(textArea, stage));
        toolBar.getItems().add(saveTextButton);

        final Button copyToClipboard = new Button();
        copyToClipboard.setGraphic(ToolbarFactory.createImage("clipboards.png"));
        copyToClipboard.setTooltip(new Tooltip(TextUtil.getText("copyToClipboard")));
        copyToClipboard.setOnAction(new CopyLogAction(textArea));
        toolBar.getItems().add(copyToClipboard);

        return toolBar;
    }

    public void run(final Runnable after) {
        show();

        final ProgressLogger logger = this;
        final Service<Void> serviceWrapper = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        service.start(logger);

                        Platform.runLater(() -> progressBar.setProgress(100));
                        Platform.runLater(after);
                        return null;
                    }
                };
            }
        };
        serviceWrapper.start();
    }

    @Override
    public void logWarning(String message) {
        Platform.runLater(() ->  textArea.appendText("WARNING: " + message + "\n"));
    }

    @Override
    public void logError(String error) {
        Platform.runLater(() ->  textArea.appendText("ERROR: " + error + "\n"));
    }

    @Override
    public void logInfo(String message) {
        Platform.runLater(() ->  textArea.appendText("INFO: " + message + "\n"));
    }

    @Override
    public void setProgress(long progress) {
        progressBar.setProgress(progress);
    }
}
