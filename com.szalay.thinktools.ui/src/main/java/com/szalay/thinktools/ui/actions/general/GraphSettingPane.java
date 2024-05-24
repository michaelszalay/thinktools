package com.szalay.thinktools.ui.actions.general;


import com.szalay.thinktools.ui.ApplicationSettings;
import com.szalay.thinktools.ui.TextUtil;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Objects;

final class GraphSettingPane extends GridPane {
    private final ApplicationSettings settings;

    private final TextField nodeWidth;
    private final TextField nodeHeight;
    private final TextField nodeMarginInSameCause;
    private final TextField nodeEffectMargin;
    private final ColorPicker graphNodeTextColor;
    private final ColorPicker graphNodeOvalColor;
    private final ColorPicker graphNodeLineColor;

    GraphSettingPane(ApplicationSettings settings) {
        super();
        Objects.requireNonNull(settings);
        this.settings = settings;

        setPrefSize(1200, 800);

        add(new Label(TextUtil.getText("nodeWidth")), 1, 0);
        nodeWidth = new TextField();
        nodeWidth.setText(String.valueOf(settings.getNodeWidth()));
        add(nodeWidth, 2, 0);

        add(new Label(TextUtil.getText("nodeHeight")), 1, 1);
        nodeHeight = new TextField();
        nodeHeight.setText(String.valueOf(settings.getNodeHeight()));
        add(nodeHeight, 2, 1);

        add(new Label(TextUtil.getText("nodeMarginInSameCause")), 1, 2);
        nodeMarginInSameCause = new TextField();
        nodeMarginInSameCause.setText(String.valueOf(settings.getNodeMarginInSameCause()));
        add(nodeMarginInSameCause, 2, 2);

        add(new Label(TextUtil.getText("nodeEffectMargin")), 1, 3);
        nodeEffectMargin = new TextField();
        nodeEffectMargin.setText(String.valueOf(settings.getNodeEffectMargin()));
        add(nodeEffectMargin, 2, 3);

        add(new Label(TextUtil.getText("graphNodeTextColor")), 1, 4);
        graphNodeTextColor = new ColorPicker();
        graphNodeTextColor.setValue(settings.getGraphNodeTextColor());
        add(graphNodeTextColor, 2, 4);

        add(new Label(TextUtil.getText("graphNodeOvalColor")), 1, 5);
        graphNodeOvalColor = new ColorPicker();
        graphNodeOvalColor.setValue(settings.getGraphNodeOvalColor());
        add(graphNodeOvalColor, 2, 5);

        add(new Label(TextUtil.getText("graphNodeLineColor")), 1, 6);
        graphNodeLineColor = new ColorPicker();
        graphNodeLineColor.setValue(settings.getGraphNodeLineColor());
        add(graphNodeLineColor, 2, 6);
    }

    void saveToSettings() {
        settings.setNodeWidth(Integer.parseInt(nodeWidth.getText()));
        settings.setNodeHeight(Integer.parseInt(nodeHeight.getText()));
        settings.setNodeMarginInSameCause(Integer.parseInt(nodeMarginInSameCause.getText()));
        settings.setNodeEffectMargin(Integer.parseInt(nodeEffectMargin.getText()));
        settings.setGraphNodeTextColor(graphNodeTextColor.getValue());
        settings.setGraphNodeOvalColor(graphNodeOvalColor.getValue());
        settings.setGraphNodeLineColor(graphNodeLineColor.getValue());
    }
}
