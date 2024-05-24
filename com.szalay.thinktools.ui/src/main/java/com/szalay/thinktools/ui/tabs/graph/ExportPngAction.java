package com.szalay.thinktools.ui.tabs.graph;

import com.szalay.thinktools.ui.TextUtil;
import com.szalay.thinktools.ui.components.ProgressLogger;
import com.szalay.thinktools.ui.components.ProgressWindow;
import com.szalay.thinktools.ui.components.ProgressWindowService;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class ExportPngAction implements EventHandler<ActionEvent> {

    private final Canvas canvas;
    private final Stage stage;
    private final ModelPainter painter;

    public ExportPngAction(Canvas canvas, Stage stage, ModelPainter painter) {
        this.canvas = canvas;
        this.stage = stage;
        this.painter = painter;
    }

    @Override
    public void handle(ActionEvent actionEvent) {

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(TextUtil.getText("selectPngFile"));

        final File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            final ProgressWindowService service = new ProgressWindowService() {
                @Override
                public void start(ProgressLogger logger) {

                    Platform.runLater(() -> {
                        logger.logInfo("Writing " + file.getAbsolutePath());

                        final WritableImage writableImage = new WritableImage((int) painter.getWidth(), (int) painter.getHeight());
                        canvas.snapshot(null, writableImage);

                        final RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        try {
                            ImageIO.write(renderedImage, "png", file);
                        } catch (IOException e) {
                            logger.logError(e.toString());
                        }

                        logger.logInfo("Done");
                    });
                }

                @Override
                public String getTitle() {
                    return TextUtil.getText("exportPNGButton");
                }
            };

            final ProgressWindow window = new ProgressWindow(service, stage);
            window.run(() -> {});
        }
    }
}
