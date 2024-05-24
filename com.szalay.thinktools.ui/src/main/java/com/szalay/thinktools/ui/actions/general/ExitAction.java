package com.szalay.thinktools.ui.actions.general;

import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Objects;
import java.util.Optional;

public class ExitAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument doc;

    public ExitAction(ApplicationDocument doc) {
        Objects.requireNonNull(doc);

        this.doc = doc;
    }

    @Override
    public void handle(ActionEvent event) {
        if (doc.hasUnsavedChanges()) {
            //Ask...
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(TextUtil.getText("questionTitle"));
            alert.setContentText(TextUtil.getText("unsavedChangesQuestion"));

            final Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                doc.save();
            } else {
                return;
            }
        }

        if (doc.getApplicationSettings().isAskOnExit()) {
            //Ask for exit...
            //Ask...
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(TextUtil.getText("questionTitle"));
            alert.setContentText(TextUtil.getText("reallyExit"));

            final Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                doc.exit();
                System.exit(0);
            }
        }
        else {
            System.exit(0);
        }
    }
}
