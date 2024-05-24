package com.szalay.thinktools.ui;

import javafx.scene.control.Alert;

public final class UIUtil {

    public static void showError(final String title, final String error) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(error);

        alert.showAndWait();
    }

    public static void showInfo(final String title, final String info) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(info);

        alert.showAndWait();
    }
}
