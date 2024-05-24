package com.szalay.thinktools.ui.tabs.generator;

import com.szalay.thinktools.model.ModelHistory;
import com.szalay.thinktools.model.ModelHistoryItem;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.tabs.hypothesis.HypothesisTab;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public final class ModelHistoryBox extends ScrollPane {

    private final VBox layout;
    private final ApplicationDocument document;

    ModelHistoryBox(ApplicationDocument document) {
        super();

        this.document = document;

        setPrefSize(1200, 800);

        layout = new VBox();
        setContent(layout);
    }

    void showModelHistory(final ModelHistory modelHistory) {

        layout.getChildren().clear();

        for (final ModelHistoryItem item : modelHistory.getHistory()) {
            final TitledPane panel = new TitledPane();
            panel.setPrefSize(1200, 800);
            panel.setExpanded(true);
            panel.setText(item.toString());

            panel.setContent(HypothesisTab.createTree(item.getModel(), document));
            layout.getChildren().add(panel);
        }
    }
}
