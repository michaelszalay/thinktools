package com.szalay.thinktools.ui.tabs.graph;

import com.szalay.thinktools.model.CausalHypothesis;
import com.szalay.thinktools.model.CausalModel;
import com.szalay.thinktools.model.ComplexCause;
import com.szalay.thinktools.model.Factor;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.ApplicationSettings;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ModelPainter {
    private final ApplicationDocument document;

    private final Map<Factor, Position> positionOfFactors = new HashMap<>();
    private final Map<Factor, Position> desiredPositions = new HashMap<>();


    private double currentX;
    private double currentY;

    private final GraphicsContext graphics;
    private final Canvas canvas;

    ModelPainter(ApplicationDocument document, GraphicsContext graphics, Canvas canvas) {
        Objects.requireNonNull(document);
        Objects.requireNonNull(graphics);
        Objects.requireNonNull(canvas);

        this.document = document;
        this.graphics = graphics;
        this.canvas = canvas;
    }

    void paintToGraphics() {

        graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        currentX = 20;
        currentY = 50;

        final ApplicationSettings settings = document.getApplicationSettings();
        final double halfNodeWidth = (double) settings.getNodeWidth() / 2;

        @SuppressWarnings("IntegerDivisionInFloatingPointContext")
        final double halfNodeHeight = settings.getNodeHeight() / 2;
        final CausalModel model = document.getProject().getCausalModel();
        for (final CausalHypothesis hypothesis : model) {
            for (final ComplexCause cause : hypothesis.getComplexCauses()) {
                paintComplexCause(graphics, cause);
            }

            currentX = (double) ((hypothesis.getFactors().size() - 1) * settings.getNodeWidth()) / 2;
            currentY += settings.getNodeEffectMargin();

            paintNode(graphics, hypothesis.getEffect());
            for (final ComplexCause cause : hypothesis.getComplexCauses()) {
                for (final Factor f : cause) {
                    final Position p = positionOfFactors.get(f);
                    final Position effectP = positionOfFactors.get(hypothesis.getEffect());

                    if (!p.equals(effectP)) {
                        graphics.setStroke(settings.getGraphNodeLineColor());
                        graphics.strokeLine(p.getX() + halfNodeWidth, p.getY() + halfNodeHeight - 5,
                                effectP.getX() + halfNodeWidth, effectP.getY() - halfNodeHeight - 5);
                    }
                }
            }
        }
    }

    void handleMouseDragged(MouseEvent mouseEvent) {
        boolean hasChanges = false;
        final ApplicationSettings settings = document.getApplicationSettings();
        for (final Map.Entry<Factor, Position> pos : positionOfFactors.entrySet()) {

            final Rectangle r = new Rectangle(pos.getValue().getX() - 20, pos.getValue().getY() - 20, settings.getNodeWidth() + 40, settings.getNodeHeight() + 40);
            if (r.contains(mouseEvent.getX(), mouseEvent.getY())) {

                desiredPositions.put(pos.getKey(), new Position(mouseEvent.getX(), mouseEvent.getY()));
                hasChanges = true;
                break;
            }
        }

        if (hasChanges) {
            positionOfFactors.clear();
            paintToGraphics();
        }
    }

    public double getWidth() {
        return currentX * 5;
    }

    public double getHeight() {
        return currentY * 5;
    }

    private void paintNode(GraphicsContext graphics, Factor node) {
        if (positionOfFactors.containsKey(node)) {
            return;
        }

        Position position = desiredPositions.get(node);
        if (position != null) {
            currentX = position.getX();
            currentY = position.getY();
        }

        final ApplicationSettings settings = document.getApplicationSettings();
        graphics.setStroke(settings.getGraphNodeTextColor());
        graphics.strokeText(node.toString(), currentX, currentY);

        graphics.setStroke(settings.getGraphNodeOvalColor());
        graphics.strokeOval(currentX - 20, currentY - 25, settings.getNodeWidth(), settings.getNodeHeight());

        positionOfFactors.put(node, new Position(currentX, currentY));
    }

    private void paintComplexCause(GraphicsContext graphics, ComplexCause cause) {

        final ApplicationSettings settings = document.getApplicationSettings();
        for (final Factor f : cause) {
            paintNode(graphics, f);

            currentX += settings.getNodeMarginInSameCause();
        }
    }
}
