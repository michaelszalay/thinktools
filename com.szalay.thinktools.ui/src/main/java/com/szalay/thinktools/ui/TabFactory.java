package com.szalay.thinktools.ui;

import com.szalay.thinktools.ui.tabs.datamodel.DataModelTab;
import com.szalay.thinktools.ui.tabs.factor.FactorTab;
import com.szalay.thinktools.ui.tabs.general.GeneralTab;
import com.szalay.thinktools.ui.tabs.generator.GeneratorTab;
import com.szalay.thinktools.ui.tabs.graph.GraphTab;
import com.szalay.thinktools.ui.tabs.hypothesis.HypothesisTab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

final class TabFactory {

    static TabPane createTabPane(final ApplicationDocument document, final Stage stage) {
        final TabPane tabPane = new TabPane();
        tabPane.getSelectionModel().selectedItemProperty().addListener((observableValue, oldTab, newTab) -> {
            final ApplicationDocumentListener listener = (ApplicationDocumentListener) newTab;

            if (oldTab != null) {
                final ApplicationDocumentListener oldListener = (ApplicationDocumentListener) oldTab;
                document.removeListener(oldListener);
            }

            document.addListener(listener);
            document.triggerListeners();
        });
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        final GeneralTab generalTab = new GeneralTab(document);
        tabPane.getTabs().add(generalTab);

        final DataModelTab dataModelTab = new DataModelTab(stage);
        tabPane.getTabs().add(dataModelTab);

        final FactorTab factorTab = new FactorTab();
        tabPane.getTabs().add(factorTab);

        final HypothesisTab hypothesisTab = new HypothesisTab();
        tabPane.getTabs().add(hypothesisTab);

        final GeneratorTab generatorTab = new GeneratorTab(stage);
        tabPane.getTabs().add(generatorTab);

        final GraphTab graphTab = new GraphTab(stage, document);
        tabPane.getTabs().add(graphTab);

        return tabPane;
    }
}
