package com.szalay.thinktools.ui.tabs.factor;

import com.szalay.thinktools.model.DataModel;
import com.szalay.thinktools.model.DataProperty;
import com.szalay.thinktools.model.Factor;
import com.szalay.thinktools.model.Individual;
import com.szalay.thinktools.ui.ApplicationDocument;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.HashSet;
import java.util.Set;

public class AddFromDataModelAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument document;

    public AddFromDataModelAction(ApplicationDocument doc) {
        this.document = doc;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (document.getProject() == null) {
            return;
        }

        if (document.getProject().getDataModel() == null) {
            return;
        }

        final DataModel dataModel = document.getProject().getDataModel();
        final Set<String> properties = new HashSet<>();
        for (final Individual i : dataModel) {
            for (final DataProperty p : i) {
                properties.add(p.getName());
            }
        }

        for (final String p : properties) {
            final Factor f = new Factor(p, 0, 1);
            f.setLogicalValue(false);
            document.getProject().getFactors().add(f);
        }

        document.setProjectModified(true);
    }
}
