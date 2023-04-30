package com.example.plotter;

import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;
import java.util.Random;

public class PlotFunction {
    private Function function;
    private boolean isFunctionVisible = true;
    private ArrayList<Line> lines = new ArrayList<>();
    private final int POINTS = 100;
    Random random = new Random();

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void recalculateLinesPosition(double[] offset, double graphWidth, double graphHeight, double plotAreaWidth, double plotAreaHeight) {
        if (!isFunctionVisible) {
            return;
        }
        lines.get(0).setStartX(0);
        lines.get(0).setStartY(plotAreaHeight - (function.calculate(offset[0]) - offset[1]) / graphHeight * plotAreaHeight);

        double v = offset[0] + graphWidth / POINTS;
        for (int i = 1; i < POINTS; v += graphWidth / POINTS, i++) {
            double posY = plotAreaHeight - (function.calculate(v) - offset[1]) / graphHeight * plotAreaHeight;
            Line l = lines.get(i - 1);
            l.setEndX(i * 0.01 * plotAreaWidth);
            l.setEndY(posY);
            l = lines.get(i);
            l.setStartX(i * plotAreaWidth / POINTS);
            l.setStartY(posY);

        }
        lines.get(POINTS - 1).setEndX(plotAreaWidth);
        lines.get(POINTS - 1).setEndY(plotAreaHeight - (function.calculate(offset[0] + graphWidth) - offset[1]) / graphHeight * plotAreaHeight);
    }

    public Function getFunction() {
        return function;
    }

    public boolean isFunctionVisible() {
        return isFunctionVisible;
    }

    public void changeFunctionVisibility(boolean visibility) {
        if (visibility == isFunctionVisible) {
            return;
        }
        isFunctionVisible = visibility;
        for (Line l : lines) {
            l.setVisible(isFunctionVisible);
        }
    }

    public void changeFunctionVisibility() {
        changeFunctionVisibility(!isFunctionVisible);
    }

    public PlotFunction(Function function, PlotArea root) {
        this.function = function;
        Color color = Color.color(random.nextDouble(0, 1), random.nextDouble(0, 1), random.nextDouble(0, 1));
        for (int i = 0; i < POINTS; i++) {
            Line temp = new Line();
            temp.setFill(color);
            temp.setStrokeWidth(root.getWidth() / 200);
            temp.setStroke(color);
            lines.add(temp);
            root.getChildren().add(temp);
        }
    }
}
