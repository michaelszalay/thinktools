package com.szalay.thinktools.ui.actions.general;

import com.szalay.thinktools.ui.ApplicationSettings;
import com.szalay.thinktools.ui.TextUtil;
import javafx.scene.control.Accordion;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TitledPane;

import java.util.Objects;

final class SettingsDialog extends Dialog<ButtonType> {

    private final GeneralSettingsPane generalSettingsPane;
    private final GeneratorSettingPane generatorSettingPane;
    private final GraphSettingPane graphSettingPane;

    SettingsDialog(ApplicationSettings settings) {
        super();
        Objects.requireNonNull(settings);
        getDialogPane().setPrefSize(1200, 800);

        setTitle(TextUtil.getText("settingsTitle"));

        generalSettingsPane = new GeneralSettingsPane(settings);
        generatorSettingPane = new GeneratorSettingPane(settings);
        graphSettingPane = new GraphSettingPane(settings);

        final TitledPane generalSettings = new TitledPane(TextUtil.getText("generalSettingsPane"), generalSettingsPane);
        final TitledPane generatorSettings = new TitledPane(TextUtil.getText("generatorSettingsPane"), generatorSettingPane);
        final TitledPane graphSettings = new TitledPane(TextUtil.getText("graphSettingsPane"), graphSettingPane);

        final Accordion accordion = new Accordion();
        accordion.getPanes().addAll(generalSettings, generatorSettings, graphSettings);
        getDialogPane().setContent(accordion);

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    void writeToSettings() {
        generalSettingsPane.saveToSettings();
        generatorSettingPane.saveToSettings();
        graphSettingPane.saveToSettings();
    }
}
