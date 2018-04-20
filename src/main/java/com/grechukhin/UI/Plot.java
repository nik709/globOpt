package com.grechukhin.UI;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

class Plot extends Pane {

    private Axes mAxes;

    public Plot(Function<Double, Double> f,
                double xMin, double xMax, double xInc, Axes axes) {
        mAxes = axes;
        Path path = new Path();
        path.setStroke(Color.ORANGE.deriveColor(0, 1, 1, 0.6));
        path.setStrokeWidth(2);

        path.setClip(
                new Rectangle(
                        0, 0,
                        axes.getPrefWidth(),
                        axes.getPrefHeight()
                )
        );

        double x = xMin;
        double y = f.apply(x);

        path.getElements().add(
                new MoveTo(
                        mapX(x, axes), mapY(y, axes)
                )
        );

        x += xInc;
        while (x < xMax) {
            try {
                y = f.apply(x);

                path.getElements().add(
                        new LineTo(
                                mapX(x, axes), mapY(y, axes)
                        )
                );
            } catch (Exception ignore) {

            }
            x += xInc;
        }

        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        getChildren().setAll(axes, path);
    }

    private double mapX(double x, Axes axes) {
        double tx = axes.getPrefWidth() / 2;
        double sx = axes.getPrefWidth() /
                (axes.getXAxis().getUpperBound() -
                        axes.getXAxis().getLowerBound());

        return x * sx + tx;
    }

    private double mapY(double y, Axes axes) {
        double ty = axes.getPrefHeight() / 2;
        double sy = axes.getPrefHeight() /
                (axes.getYAxis().getUpperBound() -
                        axes.getYAxis().getLowerBound());

        return -y * sy + ty;
    }

    public void addRedPoint(double x, double y) {
        addPoint(x, y, Color.RED);
    }

    public void addBluePoint(double x, double y) {
        addPoint(x, y, Color.BLUE);
    }

    private void addPoint(double x, double y, Paint paint) {
        Circle point = new Circle(mapX(x, mAxes), mapY(y, mAxes), 1.0);
        point.setStroke(paint);
        getChildren().add(point);

        Line line = new Line(mapX(x, mAxes), mapY(-0.1, mAxes), mapX(x, mAxes), mapY(0.1, mAxes));
        point.setStroke(paint);
        getChildren().add(line);
    }
}
