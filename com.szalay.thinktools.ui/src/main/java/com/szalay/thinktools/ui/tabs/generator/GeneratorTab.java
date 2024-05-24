package com.szalay.thinktools.ui.tabs.generator;

import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.ApplicationDocumentListener;
import com.szalay.thinktools.ui.TextUtil;
import com.szalay.thinktools.ui.ToolbarFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GeneratorTab extends Tab implements ApplicationDocumentListener {

    private final Stage stage;

    public GeneratorTab(Stage stage) {
        super(TextUtil.getText("modelGenerationTab"));

        this.stage = stage;
    }

    @Override
    public void changed(ApplicationDocument document) {
        final VBox layout = new VBox();
        if (document.getProject() == null) {
            return;
        }

        final ModelHistoryBox box = new ModelHistoryBox(document);
        layout.getChildren().add(createGeneratorToolbar(document, stage, box));
        layout.getChildren().add(box);

        setContent(layout);
    }

    private ToolBar createGeneratorToolbar(ApplicationDocument document, Stage stage, ModelHistoryBox historyBox) {
        final ToolBar toolBar = new ToolBar();

        final Button startButton = new Button();
        startButton.setGraphic(ToolbarFactory.createImage("generate.gif"));
        startButton.setTooltip(new Tooltip(TextUtil.getText("runGeneratorAction")));
        startButton.setOnAction(new GenerateModelAction(document, stage, historyBox));
        toolBar.getItems().add(startButton);

        return toolBar;
    }
}
