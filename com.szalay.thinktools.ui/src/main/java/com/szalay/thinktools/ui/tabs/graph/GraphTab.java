package com.szalay.thinktools.ui.tabs.graph;

import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.ApplicationDocumentListener;
import com.szalay.thinktools.ui.TextUtil;
import com.szalay.thinktools.ui.ToolbarFactory;
import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class GraphTab extends Tab implements ApplicationDocumentListener {
    private final Stage stage;
    private ModelPainter painter;

    private Canvas canvas;

    public GraphTab(Stage stage, ApplicationDocument document) {
        super(TextUtil.getText("graphTab"));

        Objects.requireNonNull(stage);
        Objects.requireNonNull(document);

        this.stage = stage;

        final VBox layout = createLayout(document);
        final SplitPane splitPane = new SplitPane();
        splitPane.orientationProperty().set(Orientation.VERTICAL);
        splitPane.getItems().add(layout);

        final VBox positionBox = createPositionBox();
        splitPane.getItems().add(positionBox);

        setContent(splitPane);
    }

    private VBox createPositionBox() {
        final VBox vBox = new VBox();

        final HBox mouseXLine = new HBox();
        mouseXLine.getChildren().add(new Label("X"));
        final Label mouseXValue = new Label();
        mouseXLine.getChildren().add(mouseXValue);

        final HBox mouseYLine = new HBox();
        mouseYLine.getChildren().add(new Label("Y"));
        final Label mouseYValue = new Label();
        mouseYLine.getChildren().add(mouseYValue);

        canvas.setOnMouseMoved(event -> {
            mouseXValue.setText("\t" + event.getX());
            mouseYValue.setText("\t" + event.getY());
        });

        vBox.getChildren().add(mouseXLine);
        vBox.getChildren().add(mouseYLine);
        return vBox;
    }

    @Override
    public void changed(ApplicationDocument document) {
        painter.paintToGraphics();
    }

    private VBox createLayout(final ApplicationDocument document) {

        final VBox layout = new VBox();
        final ScrollPane scrollPane = new ScrollPane();

        canvas = new Canvas(1200, 800);
        final GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
        painter = new ModelPainter(document, graphicsContext2D, canvas);
        scrollPane.setContent(canvas);

        canvas.setOnMouseDragged(painter::handleMouseDragged);

        layout.getChildren().add(createToolbar(canvas, painter, document));
        layout.getChildren().add(scrollPane);
        return layout;
    }

    private ToolBar createToolbar(final Canvas canvas, final ModelPainter painter, final ApplicationDocument document) {
        final ToolBar toolBar = new ToolBar();

        final Button refreshButton = new Button();
        refreshButton.setGraphic(ToolbarFactory.createImage("refresh.png"));
        refreshButton.setTooltip(new Tooltip(TextUtil.getText("refresh")));
        refreshButton.setOnAction(actionEvent -> {
            final VBox layout = createLayout(document);
            changed(document);

            final SplitPane splitPane = (SplitPane) getContent();
            splitPane.getItems().clear();
            splitPane.getItems().add(layout);
            splitPane.getItems().add(createPositionBox());
            setContent(splitPane);
        } );

        final Button exportPngButton = new Button();
        exportPngButton.setGraphic(ToolbarFactory.createImage("save_doc.gif"));
        exportPngButton.setTooltip(new Tooltip(TextUtil.getText("exportPNGButton")));
        exportPngButton.setOnAction(new ExportPngAction(canvas, stage, painter));

        toolBar.getItems().add(exportPngButton);
        toolBar.getItems().add(refreshButton);

        return toolBar;
    }
}
