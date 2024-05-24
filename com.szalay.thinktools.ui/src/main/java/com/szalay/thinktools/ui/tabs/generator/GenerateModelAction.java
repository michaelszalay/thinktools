package com.szalay.thinktools.ui.tabs.generator;

import com.szalay.thinktools.model.*;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import com.szalay.thinktools.ui.UIUtil;
import com.szalay.thinktools.ui.components.ProgressLogger;
import com.szalay.thinktools.ui.components.ProgressWindow;
import com.szalay.thinktools.ui.components.ProgressWindowService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.util.Objects;

public class GenerateModelAction implements EventHandler<ActionEvent> {

    private final ApplicationDocument document;
    private final Stage stage;
    private final ModelHistoryBox modelHistoryBox;

    public GenerateModelAction(ApplicationDocument document, Stage stage, ModelHistoryBox modelHistoryBox) {
        Objects.requireNonNull(document);
        Objects.requireNonNull(stage);
        Objects.requireNonNull(modelHistoryBox);

        this.document = document;
        this.stage = stage;
        this.modelHistoryBox = modelHistoryBox;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (document.getProject().getCausalModel().isEmpty()) {
            return;
        }

        final CausalHypothesis startHypothesis = document.getProject().getCausalModel().get(0);
        final ModelGenerator generator = new ModelGenerator(startHypothesis, document.getProject().getFactors(),
                document.getProject().getDataModel(),
                document.getApplicationSettings().getMaxGeneratorRunCount(),
                document.getApplicationSettings().getPauseGenerator(),
                document.getApplicationSettings().getAcceptanceValue());

        final ProgressWindowService service = new ProgressWindowService() {
            @Override
            public void start(ProgressLogger logger) {

                generator.addOberserver(new CausalModelOberserver() {
                    @Override
                    public void print(String msg) {
                        logger.logInfo(msg);
                    }

                    @Override
                    public void pause() {
                        Platform.runLater(() -> UIUtil.showInfo(TextUtil.getText("generatorPaused"), TextUtil.getText("generatorPaused")));
                    }

                    @Override
                    public void update(CausalModel model) {

                    }
                });
                generator.start();
            }

            @Override
            public String getTitle() {
                return TextUtil.getText("generatorStarted");
            }
        };
        final ProgressWindow window = new ProgressWindow(service, stage);
        window.run(() -> {
            final ModelHistory history = generator.getHistory();
            modelHistoryBox.showModelHistory(history);
            document.setStatus(TextUtil.getText("generatorFinished"));
        });
    }
}
