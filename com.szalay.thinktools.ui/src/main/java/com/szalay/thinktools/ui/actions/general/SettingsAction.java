package com.szalay.thinktools.ui.actions.general;

import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import com.szalay.thinktools.ui.UIUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;

import java.util.Objects;
import java.util.Optional;

public class SettingsAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument doc;

    public SettingsAction(ApplicationDocument doc) {
        Objects.requireNonNull(doc);

        this.doc = doc;
    }

    @Override
    public void handle(ActionEvent event) {
        final SettingsDialog dialog = new SettingsDialog(doc.getApplicationSettings());
        final Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                dialog.writeToSettings();

                doc.setProjectModified(true);
                doc.getApplicationSettings().save();
            } catch (Exception e) {
                UIUtil.showError(TextUtil.getText("errorSavingSettings"), e.getMessage());
            }
        }
    }
}
