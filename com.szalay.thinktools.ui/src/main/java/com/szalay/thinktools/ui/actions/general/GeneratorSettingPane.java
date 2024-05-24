package com.szalay.thinktools.ui.actions.general;


import com.szalay.thinktools.ui.ApplicationSettings;
import com.szalay.thinktools.ui.TextUtil;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;
import java.util.Objects;

final class GeneratorSettingPane extends GridPane {

    private final TextField maxGeneratorRunCount;
    private final CheckBox pauseGenerator;
    private final TextField acceptanceValue;

    private final ApplicationSettings settings;

    GeneratorSettingPane(final ApplicationSettings settings) {
        Objects.requireNonNull(settings);
        this.settings = settings;

        setPrefSize(1200, 800);

        add(new Label(TextUtil.getText("maxGeneratorRunCount")), 1, 0);
        maxGeneratorRunCount = new TextField();
        maxGeneratorRunCount.setText(String.valueOf(settings.getMaxGeneratorRunCount()));
        add(maxGeneratorRunCount, 2, 0);

        add(new Label(TextUtil.getText("pauseGenerator")), 1, 1);
        pauseGenerator = new CheckBox();
        pauseGenerator.setSelected(settings.getPauseGenerator());
        add(pauseGenerator, 2, 1);

        add(new Label(TextUtil.getText("acceptanceValue")), 1, 2);
        acceptanceValue = new TextField();
        acceptanceValue.setText(settings.getAcceptanceValue().toString());
        add(acceptanceValue, 2, 2);
    }

    void saveToSettings() {
        settings.setMaxGeneratorRunCount(Integer.parseInt(maxGeneratorRunCount.getText()));
        settings.setPauseGenerator(pauseGenerator.isSelected());
        settings.setAcceptanceValue(new BigDecimal(acceptanceValue.getText()));
    }
}
