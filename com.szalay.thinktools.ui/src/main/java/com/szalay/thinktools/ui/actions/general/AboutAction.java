package com.szalay.thinktools.ui.actions.general;

import com.szalay.thinktools.ui.TextUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class AboutAction implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(TextUtil.getText("aboutTitle"));
        alert.setContentText(TextUtil.getText("aboutMainContentText"));
        alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(AboutAction.class.getResourceAsStream("/images/hirn.gif")))));

        alert.showAndWait();
    }
}
