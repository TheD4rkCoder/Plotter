package com.example.plotter;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.mariuszgromada.math.mxparser.*;

import java.util.ArrayList;


public class PlotArea extends Group {
    private final int MAX_NUMBER_OF_GRIDLINES = 100;
    private double width, plotCoordinatesWidth = 200;
    private double height, plotCoordinatesHeight = 200;
    private Line[] axisLines = new Line[2];
    private ArrayList<Line> gridLines = new ArrayList<>();

    private double[] offset = {-100.0, -100.0};
    //private ArrayList<Circle> points = new ArrayList<>(); // tba
    private ArrayList<Label> labelsForAxis = new ArrayList<>(); //todo in drawPlotArea
    private ArrayList<PlotFunction> functions = new ArrayList<>();
    private ArrayList<Argument> variables = new ArrayList<>();

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public PlotArea(double beginWidth, double beginHeight) {
        super();
        double[] initialMouseDragCoords = new double[2];
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouse) {
                initialMouseDragCoords[0] = mouse.getX();
                initialMouseDragCoords[1] = mouse.getY();

            }
        });
        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouse) {
                changePlottedArea((initialMouseDragCoords[0] - mouse.getX()) / width * plotCoordinatesWidth, (mouse.getY() - initialMouseDragCoords[1]) / height * plotCoordinatesHeight, 1, 1);
                initialMouseDragCoords[0] = mouse.getX();
                initialMouseDragCoords[1] = mouse.getY();
            }
        });
        this.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                changePlottedArea(0, 0, 1 + 0.01 * scrollEvent.getDeltaY(), 1 + 0.01 * scrollEvent.getDeltaY());
            }
        });
        this.height = beginHeight;
        this.width = beginWidth;
        this.getChildren().add(new Rectangle(beginWidth, beginHeight, Color.LIGHTGRAY));
        axisLines[0] = new Line(0, beginHeight / 2, beginWidth, beginHeight / 2);
        axisLines[0].setStrokeWidth(beginHeight / 200);
        axisLines[0].setFill(Color.WHITE);
        this.getChildren().add(axisLines[0]);
        axisLines[1] = new Line(beginWidth / 2, 0, beginWidth / 2, beginHeight);
        axisLines[1].setStrokeWidth(beginWidth / 200);
        axisLines[1].setFill(Color.WHITE);
        this.getChildren().add(axisLines[1]);
        for (int i = 0; i < 2 * MAX_NUMBER_OF_GRIDLINES; i++) {
            Line tempLine = new Line();
            tempLine.setFill(Color.DARKGRAY);
            gridLines.add(tempLine);
            if (i < MAX_NUMBER_OF_GRIDLINES) {
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

    /**
     * @param index
     * @param function
     */
    // Damit keine Rekursionen Entstehen können, kann man nur zu daroberstehende Funktionen referenzieren!!!
    public void addFunction(int index, Function function) {

        for (Argument a : variables) {
            function.addArguments(a);
        }
        for (int i = 0; i < index; i++) {
            function.addFunctions(functions.get(i).getFunction());
        }
        for (int i = index; i < functions.size(); i++) {
            functions.get(i).getFunction().addFunctions(function);
        }

        functions.add(index, new PlotFunction(function, this));
        if (!function.checkSyntax()) {
            changeFunctionVisibility(index, false);
        }
        drawPlotArea();
    }

    public void addFunction(Function function) {
        addFunction(functions.size(), function);
    }

    public void removeFunction(int index) {
        for (int i = index + 1; i < functions.size(); i++) {
            functions.get(i).getFunction().removeFunctions(functions.get(index).getFunction());
        }
        for (Line l : functions.get(index).getLines()) {
            this.getChildren().remove(l);
        }
        functions.remove(index);
        drawPlotArea();
    }

    public void addVariable(int index, Argument argument) {
        if (!argument.checkSyntax()) {
            argument = new Argument("rxeydtcugvbih = 1"); // no clue what the error is
        }
        for (PlotFunction f : functions) {
            f.getFunction().addArguments(argument);
        }
        variables.add(index, argument);
        drawPlotArea();
    }

    public void removeVariable(int index) {
        for (PlotFunction f : functions) {
            f.getFunction().removeArguments(variables.get(index));

        }
        variables.remove(index);
        drawPlotArea();
        //todo
        // remove it from all functions as well
    }

    private void drawPlotArea() {
        // reset Axis; later also reset Labels
        double xAxisPos = height + offset[1] / plotCoordinatesHeight * height;
        double yAxisPos = -offset[0] / plotCoordinatesWidth * width;
        yAxisPos = (yAxisPos > width) ? -20 : yAxisPos;
        axisLines[0].setStartY(xAxisPos);
        axisLines[0].setEndY(xAxisPos);
        axisLines[1].setStartX(yAxisPos);
        axisLines[1].setEndX(yAxisPos);

        // GridLines
        double distanceBetweenGridlinesX = plotCoordinatesWidth, distanceToFirstLineX;
        double distanceBetweenGridlinesY = plotCoordinatesHeight, distanceToFirstLineY;
        while (distanceBetweenGridlinesX > MAX_NUMBER_OF_GRIDLINES) {
            distanceBetweenGridlinesX /= 10;
        }
        while (distanceBetweenGridlinesX < MAX_NUMBER_OF_GRIDLINES * 0.1) {
            distanceBetweenGridlinesX *= 10;
        }
        while (distanceBetweenGridlinesY > MAX_NUMBER_OF_GRIDLINES) {
            distanceBetweenGridlinesY /= 10;
        }
        while (distanceBetweenGridlinesY < MAX_NUMBER_OF_GRIDLINES * 0.1) {
            distanceBetweenGridlinesY *= 10;
        }
        distanceBetweenGridlinesX = plotCoordinatesWidth / distanceBetweenGridlinesX;
        distanceToFirstLineX = (-offset[0]) % distanceBetweenGridlinesX;
        distanceToFirstLineX = distanceToFirstLineX / plotCoordinatesWidth * width;
        distanceBetweenGridlinesX = distanceBetweenGridlinesX / plotCoordinatesWidth * width;
        distanceBetweenGridlinesY = plotCoordinatesHeight / distanceBetweenGridlinesY;
        distanceToFirstLineY = (-offset[1]) % distanceBetweenGridlinesY;
        distanceToFirstLineY = distanceToFirstLineY / plotCoordinatesHeight * height;
        distanceBetweenGridlinesY = distanceBetweenGridlinesY / plotCoordinatesHeight * height;
        double d = distanceToFirstLineX;
        for (int i = 0; i < MAX_NUMBER_OF_GRIDLINES; d += distanceBetweenGridlinesX, i++) {
            if (d < width) {
                gridLines.get(i).setStartX(d);
                gridLines.get(i).setEndX(d);
            } else {
                gridLines.get(i).setStartX(-10);
                gridLines.get(i).setEndX(-10);
            }
        }
        d = distanceToFirstLineY;
        for (int i = MAX_NUMBER_OF_GRIDLINES; i < 2 * MAX_NUMBER_OF_GRIDLINES; d += distanceBetweenGridlinesY, i++) {
            if (d < height) {
                gridLines.get(i).setStartY(height - d);
                gridLines.get(i).setEndY(height - d);
            } else {
                gridLines.get(i).setStartY(-10);
                gridLines.get(i).setEndY(-10);

            }
        }
        for (PlotFunction f : functions) {
            f.recalculateLinesPosition(offset, plotCoordinatesWidth, plotCoordinatesHeight, width, height);
        }
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

    public void changeFunctionVisibility(int index, boolean visibility) {
        functions.get(index).changeFunctionVisibility(visibility);
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
