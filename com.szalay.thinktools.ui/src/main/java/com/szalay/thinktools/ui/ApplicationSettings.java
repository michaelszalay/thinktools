package com.szalay.thinktools.ui;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Properties;

public class ApplicationSettings {

    public static final int DEFAULT_NODE_MARGIN = 400;
    public static final int DEFAULT_NODE_HEIGHT = 40;
    public static final int DEFAULT_NODE_EFFECT_MARGIN = 100;
    public static final Color DEFAULT_NODE_TEXT_COLOR = Color.BLACK;
    public static final Color DEFAULT_OVAL_COLOR = Color.RED;
    public static final Color DEFUALT_LINE_COLOR = Color.RED;
    public static final int DEFAULT_NODE_WIDTH = 300;
    public static final String DEFAULT_ACCEPTANCE_VALUE = "0.3";
    private final Properties properties;

    public ApplicationSettings() throws Exception {
        properties = new Properties();

        final File file = new File("~/.thinktools/settings.properties");
        if (file.exists()) {
            properties.load(new FileInputStream(file));
        }
    }

    public boolean isAskOnExit() {
        final String strValue = properties.getProperty("askOnExit");
        if (strValue == null) {
            return true;
        }

        return strValue.equals(Boolean.TRUE.toString());
    }

    public void setAskOnExit(boolean ask) {
        properties.setProperty("askOnExit", Boolean.valueOf(ask).toString());
    }

    public void setLastProjectFile(final File file) {
        if (!file.exists()) {
            return;
        }

        properties.put("lastProjectFile", file.getAbsolutePath());
    }

    public File getLastProjectFile() {
        final String path = (String) properties.get("lastProjectFile");
        if (path != null) {
            return new File(path);
        }

        return null;
    }

    public void save() throws Exception {
        final File dir = new File("~/.thinktools/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final FileOutputStream fout = new FileOutputStream("~/.thinktools/settings.properties");
        properties.store(fout, "");
        fout.close();
    }

    public int getMaxGeneratorRunCount() {
        final String prop = (String) properties.get("maxGeneratorRunCount");
        if (prop == null) {
            return 100;
        }

        return Integer.parseInt(prop);
    }

    public void setMaxGeneratorRunCount(int val) {
        properties.setProperty("maxGeneratorRunCount", Integer.valueOf(val).toString());
    }

    public boolean getPauseGenerator() {
        final String prop = (String) properties.get("pauseGenerator");
        if (prop == null) {
            return false;
        }

        return Boolean.parseBoolean(prop);
    }

    public void setPauseGenerator(boolean pause) {
        properties.setProperty("pauseGenerator", Boolean.valueOf(pause).toString());
    }

    public BigDecimal getAcceptanceValue() {
        final String prop = (String) properties.get("acceptanceValue");
        return new BigDecimal(Objects.requireNonNullElse(prop, DEFAULT_ACCEPTANCE_VALUE));

    }

    public void setAcceptanceValue(final BigDecimal acceptanceValue) {
        properties.setProperty("acceptanceValue", acceptanceValue.toString());
    }

    public int getNodeWidth() {
        final String prop = (String) properties.get("nodeWidth");
        if (prop == null) {
            return DEFAULT_NODE_WIDTH;
        }

        return Integer.parseInt(prop);
    }

    public void setNodeWidth(int width) {
        properties.put("nodeWidth", Integer.valueOf(width).toString());
    }

    public int getNodeHeight() {
        final String prop = (String) properties.get("nodeHeight");
        if (prop == null) {
            return DEFAULT_NODE_HEIGHT;
        }

        return Integer.parseInt(prop);
    }

    public void setNodeHeight(int h) {
        properties.put("nodeHeight", Integer.valueOf(h).toString());
    }

    public int getNodeMarginInSameCause() {
        final String prop = (String) properties.get("nodeMarginInSameCause");
        if (prop == null) {
            return DEFAULT_NODE_MARGIN;
        }

        return Integer.parseInt(prop);
    }

    public void setNodeMarginInSameCause(int h) {
        properties.put("nodeMarginInSameCause", Integer.valueOf(h).toString());
    }

    public int getNodeEffectMargin() {
        final String prop = (String) properties.get("nodeEffectMargin");
        if (prop == null) {
            return ApplicationSettings.DEFAULT_NODE_EFFECT_MARGIN;
        }

        return Integer.parseInt(prop);
    }

    public void setNodeEffectMargin(int h) {
        properties.put("nodeEffectMargin", Integer.valueOf(h).toString());
    }

    public Color getGraphNodeTextColor() {
        final String prop = (String) properties.get("graphNodeTextColor");
        if (prop == null) {
            return DEFAULT_NODE_TEXT_COLOR;
        }

        return Color.valueOf(prop);
    }

    public void setGraphNodeTextColor(Color c) {
        properties.put("graphNodeTextColor", c.toString());
    }

    public Color getGraphNodeOvalColor() {
        final String prop = (String) properties.get("graphNodeOvalColor");
        if (prop == null) {
            return DEFAULT_OVAL_COLOR;
        }

        return Color.valueOf(prop);
    }

    public void setGraphNodeOvalColor(Color c) {
        properties.put("graphNodeOvalColor", c.toString());
    }

    public Color getGraphNodeLineColor() {
        final String prop = (String) properties.get("graphNodeLineColor");
        if (prop == null) {
            return DEFUALT_LINE_COLOR;
        }

        return Color.valueOf(prop);
    }

    public void setGraphNodeLineColor(Color c) {
        properties.put("graphNodeLineColor", c.toString());
    }
}
