/**
 * PlotFunction is a class for managing the graphical representation of a mathematical function.
 * It calculates the positions of line segments that approximate the function's curve and handles
 * function visibility.
 *
 * @author Plotter
 * @version 1.0
 */
package com.example.plotter;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;
import java.util.Random;

public class PlotFunction {
    private Function function;
    private boolean isFunctionVisible = true;
    private ArrayList<Line> lines = new ArrayList<>();
    private final int POINTS = 1000;
    private final int POINTS_FOR_DERIVATIVES = 100;
    Random random = new Random();

    /**
     * Returns the list of lines representing the function's curve.
     *
     * @return An ArrayList of Line objects.
     */
    public ArrayList<Line> getLines() {
        return lines;
    }

    /**
     * Recalculates the positions of the line segments that approximate the function's curve
     * based on the given parameters.
     *
     * @param offset         An array containing the X and Y offsets for the graph.
     * @param graphWidth     The width of the graph.
     * @param graphHeight    The height of the graph.
     * @param plotAreaWidth  The width of the plot area.
     * @param plotAreaHeight The height of the plot area.
     */
    public void recalculateLinesPosition(double[] offset, double graphWidth, double graphHeight, double plotAreaWidth, double plotAreaHeight) {

        if (!isFunctionVisible) {
            return;
        }

        int numOfPoints;
        if (function.getFunctionName().length() >= 3 && function.getFunctionName().substring(0, 3).equals("der")) {
            numOfPoints = POINTS_FOR_DERIVATIVES;
        } else {
            numOfPoints = POINTS;
        }
        lines.get(0).setStartX(0);
        lines.get(0).setStartY(plotAreaHeight - (function.calculate(offset[0]) - offset[1]) / graphHeight * plotAreaHeight);

        double v = offset[0] + graphWidth / numOfPoints;
        for (int i = 1; i < numOfPoints; v += graphWidth / numOfPoints, i++) {
            double posY = plotAreaHeight - (function.calculate(v) - offset[1]) / graphHeight * plotAreaHeight;
            Line l = lines.get(i - 1);
            l.setEndX((double) i / numOfPoints * plotAreaWidth);
            l.setEndY(posY);
            l = lines.get(i);
            l.setStartX(i * plotAreaWidth / numOfPoints);
            l.setStartY(posY);

        }
        lines.get(numOfPoints - 1).setEndX(plotAreaWidth);
        lines.get(numOfPoints - 1).setEndY(plotAreaHeight - (function.calculate(offset[0] + graphWidth) - offset[1]) / graphHeight * plotAreaHeight);
    }

    /**
     * Returns the Function object associated with this PlotFunction.
     *
     * @return A Function object.
     */
    public Function getFunction() {
        return function;
    }

    /**
     * Returns whether the function is visible on the graph.
     *
     * @return true if the function is visible, false otherwise.
     */
    public boolean isFunctionVisible() {
        return isFunctionVisible;
    }

    /**
     * Changes the visibility of the function based on the given parameter.
     *
     * @param visibility The new visibility of the function: true for visible, false for hidden.
     */
    public void changeFunctionVisibility(boolean visibility) {
        if (visibility == isFunctionVisible) {
            return;
        }
        isFunctionVisible = visibility;
        for (Line l : lines) {
            l.setVisible(isFunctionVisible);
        }
    }

    /**
     * Toggles the visibility of the function.
     */
    public void changeFunctionVisibility() {
        changeFunctionVisibility(!isFunctionVisible);
    }

    /**
     * Constructs a PlotFunction object with the given function and plot area.
     *
     * @param function The Function object to be plotted.
     * @param root     The PlotArea object where the function will be displayed.
     */
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
