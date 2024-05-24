package com.szalay.thinktools.ui;

import com.szalay.thinktools.ui.actions.general.AboutAction;
import com.szalay.thinktools.ui.actions.general.ExitAction;
import com.szalay.thinktools.ui.actions.general.SettingsAction;
import com.szalay.thinktools.ui.actions.project.NewProjectAction;
import com.szalay.thinktools.ui.actions.project.OpenProjectAction;
import com.szalay.thinktools.ui.actions.project.SaveProjectAction;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public final class ToolbarFactory {

    static VBox createToolbar(final ApplicationDocument doc, final Stage stage) {

        final ToolBar toolBar = new ToolBar();

        final Button newButton = new Button();
        newButton.setGraphic(createImage("new.gif"));
        newButton.setTooltip(new Tooltip(TextUtil.getText("newButton")));
        newButton.setOnAction(new NewProjectAction(doc));
        toolBar.getItems().add(newButton);

        final Button openButton = new Button();
        openButton.setGraphic(createImage("open.gif"));
        openButton.setTooltip(new Tooltip(TextUtil.getText("openButton")));
        openButton.setOnAction(new OpenProjectAction(doc, stage));
        toolBar.getItems().add(openButton);

        final Button saveButton = new Button();
        saveButton.setGraphic(createImage("save.gif"));
        saveButton.setTooltip(new Tooltip(TextUtil.getText("saveButton")));
        saveButton.setOnAction(new SaveProjectAction(doc));
        toolBar.getItems().add(saveButton);

        toolBar.getItems().add(new Separator());

        final Button settingsButton = new Button();
        settingsButton.setGraphic(createImage("settings.gif"));
        settingsButton.setTooltip(new Tooltip(TextUtil.getText("settingsButton")));
        settingsButton.setOnAction(new SettingsAction(doc));
        toolBar.getItems().add(settingsButton);
        toolBar.getItems().add(new Separator());

        final Button aboutButton = new Button();
        aboutButton.setGraphic(createImage("about.gif"));
        aboutButton.setTooltip(new Tooltip(TextUtil.getText("aboutButton")));
        aboutButton.setOnAction(new AboutAction());
        toolBar.getItems().add(aboutButton);

        final Button exitButton = new Button();
        exitButton.setGraphic(createImage("exit.gif"));
        exitButton.setTooltip(new Tooltip(TextUtil.getText("exitButton")));
        exitButton.setOnAction(new ExitAction(doc));
        toolBar.getItems().add(exitButton);

        return new VBox(toolBar);
    }

    public static ImageView createImage(final String name) {
        return new ImageView(new Image(Objects.requireNonNull(ToolbarFactory.class.getResourceAsStream("/images/" + name))));
    }
}
