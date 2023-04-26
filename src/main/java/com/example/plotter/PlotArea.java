package com.example.plotter;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.mariuszgromada.math.mxparser.*;

import java.util.ArrayList;


public class PlotArea extends Group {
    private double width, plotCoordinatesWidth = 200;
    private double height, plotCoordinatesHeight = 200;
    private Line[] axisLines = new Line[2];
    private ArrayList<Line> gridLines = new ArrayList<>();

    private double[] offset = new double[2];
    //private ArrayList<Circle> points = new ArrayList<>(); // tba
    private ArrayList<Label> labelsForAxis = new ArrayList<>(); //todo in drawPlotArea
    private ArrayList<PlotFunction> functions = new ArrayList<>();
    private ArrayList<Argument> variables = new ArrayList<>();

    public PlotArea(double beginWidth, double beginHeight) {
        super();
        this.height = beginHeight;
        this.width = beginWidth;
        this.offset[0] = -beginWidth / 2;
        this.offset[1] = -beginHeight / 2;
        this.getChildren().add(new Rectangle(beginWidth, beginHeight, Color.LIGHTGRAY));
        axisLines[0] = new Line(0, beginHeight / 2, beginWidth, beginHeight / 2);
        axisLines[0].setStrokeWidth(beginHeight / 100);
        axisLines[0].setFill(Color.WHITE);
        this.getChildren().add(axisLines[0]);
        axisLines[1] = new Line(beginWidth / 2, 0, beginWidth / 2, beginHeight);
        axisLines[1].setStrokeWidth(beginWidth / 100);
        axisLines[1].setFill(Color.WHITE);
        this.getChildren().add(axisLines[1]);
        for (int i = 0; i < 20; i++) {
            Line tempLine = new Line();
            tempLine.setFill(Color.DARKGRAY);
            gridLines.add(tempLine);
            if (i < 10) {
                tempLine.setStartY(0);
                tempLine.setEndY(height);
            } else {
                tempLine.setStartX(0);
                tempLine.setEndX(width);
            }
            this.getChildren().add(tempLine);
        }
        drawPlotArea();
    }

    public void addFunction(Function function) {
        for (Argument a : variables) {
            function.addArguments(a);
        }
        for (PlotFunction f : functions) {
            function.addFunctions(f.getFunction());
        }
        functions.add(new PlotFunction(function));
    }

    public void removeFunction(int index) {
        //todo
        // remove it from all other functions as well
        // also remove the lines from this.getchildren
    }

    public void addVariable(Argument argument) {
        //todo
    }

    public void removeVariable(int index) {
        //todo
        // remove it from all functions as well
    }

    private void drawPlotArea() {
        // reset Axis; later also reset Labels
        double xAxisPos = -offset[1];
        double yAxisPos = offset[0] + width;
        if (xAxisPos < 0) {
            xAxisPos = 0;
        } else if (xAxisPos > height) {
            xAxisPos = height;
        }
        if (yAxisPos < 0) {
            yAxisPos = 0;
        } else if (yAxisPos > width) {
            yAxisPos = width;
        }
        axisLines[0].setStartY(xAxisPos);
        axisLines[0].setEndY(xAxisPos);
        axisLines[1].setStartX(yAxisPos);
        axisLines[1].setEndX(yAxisPos);

        // GridLines
        double distanceBetweenGridlinesX = plotCoordinatesWidth, distanceToFirstLineX;
        double distanceBetweenGridlinesY = plotCoordinatesHeight, distanceToFirstLineY;
        while (distanceBetweenGridlinesX > 10) {
            distanceBetweenGridlinesX /= 10;
        }
        while (distanceBetweenGridlinesX < 0) {
            distanceBetweenGridlinesX *= 10;
        }
        while (distanceBetweenGridlinesY > 10) {
            distanceBetweenGridlinesY /= 10;
        }
        while (distanceBetweenGridlinesY < 0) {
            distanceBetweenGridlinesY *= 10;
        }

        distanceToFirstLineX = (-offset[0]) % distanceBetweenGridlinesX;
        distanceToFirstLineX = distanceToFirstLineX / plotCoordinatesWidth * width;
        distanceBetweenGridlinesX = distanceBetweenGridlinesX / plotCoordinatesWidth * width;
        distanceToFirstLineY = (-offset[1]) % distanceBetweenGridlinesY;
        distanceToFirstLineY = distanceToFirstLineY / plotCoordinatesHeight * height;
        distanceBetweenGridlinesY = distanceBetweenGridlinesY / plotCoordinatesHeight * height;
        for (double i = 0, d = distanceToFirstLineX; d < width && i < 10; d += distanceBetweenGridlinesX, i++) {
            gridLines.get((int) i).setStartX(d);
            gridLines.get((int) i).setEndX(d);
        }
        for (double i = 10, d = distanceToFirstLineY; d < height && i < 20; d += distanceBetweenGridlinesY, i++) {
            gridLines.get((int) i).setStartY(d);
            gridLines.get((int) i).setEndY(d);
        }
        //todo
    }

    private void drawFunction(Function f) {

    }

    public void changePlottedArea(double deltaX, double deltaY, double plotCoordsWidthChangeFactor, double plotCoordsHeightChangeFactor) {
        this.offset[0] += deltaX;
        this.offset[1] += deltaY;
        this.plotCoordinatesWidth *= plotCoordsWidthChangeFactor;
        this.plotCoordinatesHeight *= plotCoordsHeightChangeFactor;
        drawPlotArea();
    }

    public void resize(double newWidth, double newHeight) {
        // todo
    }

    public void changeFunctionVisibility(int index) {
        functions.get(index).changeFunctionVisibility();
    }
/*
    public void removeVariable(Argument argument) {
        removeVariable(argument.getArgumentExpressionString());
    }
    public void removeVariable(String argument) {
        for (Argument a : variables) {
            if (a.getArgumentExpressionString().equals(argument)) {
                variables.remove(a);
            }
        }
    }
    public void removeFunction(Function function) {
        removeFunction(function.getFunctionExpressionString());
    }
    public void removeFunction(String function) {
        for (int i = 0; i < functions.size(); i++) {
            if (functions.get(i).getFunctionExpressionString().equals(function)) {
                functions.remove(i);
            }
        }
    }

 */
}
