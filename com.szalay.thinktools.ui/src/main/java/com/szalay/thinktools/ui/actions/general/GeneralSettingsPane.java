package com.szalay.thinktools.ui.actions.general;


import com.szalay.thinktools.ui.ApplicationSettings;
import com.szalay.thinktools.ui.TextUtil;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

final class GeneralSettingsPane extends GridPane {

    private final ApplicationSettings settings;

    private final CheckBox askOnExitField;

    GeneralSettingsPane(ApplicationSettings settings) {
        this.settings = settings;

        setPrefSize(1200, 800);

        add(new Label(TextUtil.getText("askOnExitSettings")), 1, 0);
        askOnExitField = new CheckBox();
        askOnExitField.setSelected(settings.isAskOnExit());
        add(askOnExitField, 2, 0);
    }

    void saveToSettings() {
        settings.setAskOnExit(askOnExitField.isSelected());
    }
}
