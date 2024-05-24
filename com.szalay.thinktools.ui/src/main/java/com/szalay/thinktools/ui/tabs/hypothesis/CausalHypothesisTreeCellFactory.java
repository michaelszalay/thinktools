package com.szalay.thinktools.ui.tabs.hypothesis;

import com.szalay.thinktools.model.CausalHypothesis;
import com.szalay.thinktools.model.ComplexCause;
import com.szalay.thinktools.model.Factor;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.TextFieldTreeCell;

final class CausalHypothesisTreeCellFactory extends TextFieldTreeCell<Object> {
    private final ApplicationDocument document;

    CausalHypothesisTreeCellFactory(ApplicationDocument document) {
        super();
        this.document = document;
    }

    @Override
    public void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            return;
        }

        if (item instanceof String) {
            //root
            setText((String) item);
            setContextMenu(createRootContextMenu(document));
        }
        else if (item instanceof CausalHypothesis) {
            setText(((CausalHypothesis) item).toBigString());
            setContextMenu(createCausalHypothesisContextMenu((CausalHypothesis) item));
        }
        else if (item instanceof ComplexCause) {
            setText(item.toString());
            setContextMenu(createComplexCauseContextMenu((ComplexCause) item));
        }
        else if (item instanceof Effect) {
            setText(((Effect) item).toString());
        }
        else if (item instanceof Factor) {
            //Factor in complex cause...
            setText(item.toString());
        }
    }

    private ContextMenu createComplexCauseContextMenu(final ComplexCause complexCause) {
        final  ContextMenu menu = new ContextMenu();
        menu.getItems().add(createAddFactorMenuItem(document, complexCause));
        return menu;
    }

    private MenuItem createSetEffectMenuItem(ApplicationDocument document, CausalHypothesis hypothesis) {
        final MenuItem item = new MenuItem();
        item.setText(TextUtil.getText("setEffect"));
        item.setOnAction(new SetEffectAction(document, hypothesis));
        return item;
    }

    private MenuItem createAddFactorMenuItem(ApplicationDocument document, ComplexCause complexCause) {
        final MenuItem item = new MenuItem();
        item.setText(TextUtil.getText("addFactor"));
        item.setOnAction(new AddFactorAction(document, complexCause));
        return item;
    }

    private ContextMenu createCausalHypothesisContextMenu(CausalHypothesis hypothesis) {
        final  ContextMenu menu = new ContextMenu();
        menu.getItems().add(createAddComplexCauseMenuItem(document, hypothesis));
        menu.getItems().add(createSetEffectMenuItem(document, hypothesis));
        menu.getItems().add(createRemoveHypothesisMenuItem(document, hypothesis));
        return menu;
    }

    private MenuItem createRemoveHypothesisMenuItem(ApplicationDocument document, CausalHypothesis hypothesis) {
        final MenuItem item = new MenuItem();
        item.setText(TextUtil.getText("removeHypothesis"));
        item.setOnAction(new RemoveHypothesisAction(document, hypothesis));
        return item;
    }

    private MenuItem createAddComplexCauseMenuItem(ApplicationDocument document, CausalHypothesis hyp) {
        final MenuItem item = new MenuItem();
        item.setText(TextUtil.getText("addComplexCause"));
        item.setOnAction(new AddComplexCauseAction(document, hyp));
        return item;
    }

    private ContextMenu createRootContextMenu(final ApplicationDocument document) {
        final  ContextMenu menu = new ContextMenu();
        menu.getItems().add(createAddHypothesisMenuItem(document));
        return menu;
    }

    private MenuItem createAddHypothesisMenuItem(final ApplicationDocument document) {
        final MenuItem item = new MenuItem();
        item.setText(TextUtil.getText("createNewHypothesis"));
        item.setOnAction(new CreateHypothesisAction(document));
        return item;
    }
}
