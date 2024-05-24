package com.szalay.thinktools.ui.tabs.hypothesis;

import com.szalay.thinktools.model.CausalHypothesis;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

final class CreateHypothesisAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument document;

    CreateHypothesisAction(ApplicationDocument document) {
        this.document = document;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        final TextInputDialog td = new TextInputDialog(TextUtil.getText("enterCausalHypothesisName"));
        final Optional<String> hypName = td.showAndWait();
        if (hypName.isPresent()) {
            final CausalHypothesis hypothesis = new CausalHypothesis(hypName.get());
            document.getProject().getCausalModel().add(hypothesis);
            document.setProjectModified(true);
        }
    }
}
