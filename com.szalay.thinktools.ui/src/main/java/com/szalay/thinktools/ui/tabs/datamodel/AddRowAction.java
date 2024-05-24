package com.szalay.thinktools.ui.tabs.datamodel;

import com.szalay.thinktools.model.DataProperty;
import com.szalay.thinktools.model.Factor;
import com.szalay.thinktools.model.Individual;
import com.szalay.thinktools.ui.ApplicationDocument;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

final class AddRowAction implements EventHandler<ActionEvent> {

    private final ApplicationDocument doc;

    AddRowAction(ApplicationDocument doc) {
        Objects.requireNonNull(doc);

        this.doc = doc;
    }

    @Override
    public void handle(ActionEvent event) {

        final AddRowDialog dialog = new AddRowDialog(doc);
        final Optional<ButtonType> buttonType = dialog.showAndWait();
        if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {

            final Map<Factor, Supplier<String>> newRow = dialog.getNewRow();
            final Individual i = new Individual();

            for (final Map.Entry<Factor, Supplier<String>> entry : newRow.entrySet()) {

                final DataProperty p = new DataProperty(entry.getKey().getName(), entry.getValue().get());
                i.add(p);
            }

            doc.getProject().getDataModel().add(i);
            doc.setProjectModified(true);
        }

    }
}
