package com.szalay.thinktools.ui;

import com.szalay.thinktools.ui.actions.general.ExitAction;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Command line entry point.
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(TextUtil.getText("mainWindowTitle"));
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/hirn.gif"))));

        try {
            final Label statusLabel = new Label();

            final ApplicationDocument doc = new ApplicationDocument(primaryStage, statusLabel);
            addStageTitleListener(doc, primaryStage);

            final VBox root = new VBox();
            root.getChildren().add(ToolbarFactory.createToolbar(doc, primaryStage));

            final ScrollPane scrollPane = new ScrollPane();
            scrollPane.setPrefViewportHeight(1200);
            scrollPane.setPrefViewportWidth(800);
            scrollPane.setContent(TabFactory.createTabPane(doc, primaryStage));
            root.getChildren().add(scrollPane);
            root.getChildren().add(statusLabel);

            primaryStage.setScene(new Scene(root, 1600, 1200));
            primaryStage.show();

            primaryStage.setOnCloseRequest(windowEvent -> {
                final ExitAction action = new ExitAction(doc);
                action.handle(null);
            });

            doc.triggerListeners();
        }
        catch (Throwable e) {
            e.printStackTrace();
            UIUtil.showError(TextUtil.getText("errorTitle"), e.getMessage());
        }
    }

    private static void addStageTitleListener(final ApplicationDocument doc, final Stage stage) {
        doc.addListener(document -> {
            if (document.getProject() != null) {
                String name = document.getProject().getName();
                if (name != null) {
                    stage.setTitle(TextUtil.getText("mainWindowTitle") + ", Project: " + name);
                }
                else {
                    stage.setTitle(TextUtil.getText("mainWindowTitle"));
                }
            }
            else {
                stage.setTitle(TextUtil.getText("mainWindowTitle"));
            }
        });
    }
}
