package com.example.plotter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;

/**
 * PlotArea is a custom JavaFX Group that represents a 2D plotting area for mathematical functions.
 * It provides functionality to add, remove and update functions, variables, and grid lines.
 * It also supports scrolling, zooming, and dragging the plotting area.
 *
 * @author Plotter
 * @version 1.0
 */
public class PlotArea extends Group {
    private final int MAX_NUMBER_OF_GRIDLINES = 20;
    private double width;
    private double plotCoordinatesWidth = 200;
    private double height;
    private double plotCoordinatesHeight = 200;
    private final Line[] axisLines = new Line[2];
    private final ArrayList<Line> gridLines = new ArrayList<>();
    private final Rectangle background;

    private final double[] offset = {-100.0, -100.0};
    //private ArrayList<Circle> points = new ArrayList<>(); // tba
    private final ArrayList<Label> labelsForAxis = new ArrayList<>(); //todo in drawPlotArea
    private final ArrayList<PlotFunction> functions = new ArrayList<>();
    private final ArrayList<Argument> variables = new ArrayList<>();
    private Label[] plotScaleEditLabels = new Label[4];
    private TextField[] plotScaleEditTextFields = new TextField[4];

    /**
     * This method returns the width of the object.
     *
     * @return The width of the object.
     */
    public double getWidth() {
        return width;
    }

    /**
     * This method returns the height of the object.
     *
     * @return The height of the object.
     */
    public double getHeight() {
        return height;
    }


    /**
     * Creates a new PlotArea with the specified dimensions.
     *
     * @param beginWidth  The initial width of the plotting area.
     * @param beginHeight The initial height of the plotting area.
     */
    public PlotArea(double beginWidth, double beginHeight) {
        super();
        this.height = beginHeight;
        this.width = beginWidth;
        // to enable dragging:
        double[] initialMouseDragCoords = new double[2];
        this.setOnMousePressed(mouse -> {
            initialMouseDragCoords[0] = mouse.getX();
            initialMouseDragCoords[1] = mouse.getY();

        });
        this.setOnMouseDragged(mouse -> {
            changePlottedArea((initialMouseDragCoords[0] - mouse.getX()) / width * plotCoordinatesWidth, (mouse.getY() - initialMouseDragCoords[1]) / height * plotCoordinatesHeight, 1, 1);
            initialMouseDragCoords[0] = mouse.getX();
            initialMouseDragCoords[1] = mouse.getY();
        });

        // to enable scrolling:
        this.setOnScroll(scrollEvent -> changePlottedArea(-0.01 * plotCoordinatesWidth * (scrollEvent.getX() / width) * ((scrollEvent.getDeltaY() == 0) ? 0.1 : scrollEvent.getDeltaY()), -0.01 * plotCoordinatesHeight * (1 - scrollEvent.getY() / height) * ((scrollEvent.getDeltaY() == 0) ? 0.1 : scrollEvent.getDeltaY()), 1 + 0.01 * ((scrollEvent.getDeltaY() <= -100) ? -99 : scrollEvent.getDeltaY()), 1 + 0.01 * ((scrollEvent.getDeltaY() <= -100) ? -99 : scrollEvent.getDeltaY())));
        // to add background:
        background = new Rectangle(beginWidth, beginHeight, Color.LIGHTGRAY);
        this.getChildren().add(background);
        // to create TextFields that let the user input a specific area of the plot area:
        plotScaleEditLabels[0] = new Label("Min X:");
        plotScaleEditLabels[1] = new Label("Width:");
        plotScaleEditLabels[2] = new Label("Min Y:");
        plotScaleEditLabels[3] = new Label("Height:");
        plotScaleEditTextFields[0] = new TextField("-50");
        plotScaleEditTextFields[1] = new TextField("100");
        plotScaleEditTextFields[2] = new TextField("-50");
        plotScaleEditTextFields[3] = new TextField("100");
        for (int i = 0; i < 4; i++) {
            plotScaleEditTextFields[i].setStyle("-fx-font: 10px \"Serif\"");
            plotScaleEditTextFields[i].setOnAction(actionEvent -> {
                try {
                    double deltaX = Double.parseDouble(plotScaleEditTextFields[0].getText());
                    double width = Double.parseDouble(plotScaleEditTextFields[1].getText());
                    double deltaY = Double.parseDouble(plotScaleEditTextFields[2].getText());
                    double height = Double.parseDouble(plotScaleEditTextFields[3].getText());
                    if (width <= 0 || height <= 0) {
                        throw new NumberFormatException();
                    }
                    setPlottedArea(deltaX, deltaY, width, height);
                } catch (NumberFormatException e) {
                    showErrorWindow();
                }
            });
            this.getChildren().addAll(plotScaleEditLabels[i], plotScaleEditTextFields[i]);
        }
        // to add background axis lines:
        axisLines[0] = new Line(0, beginHeight / 2, beginWidth, beginHeight / 2);
        axisLines[0].setStrokeWidth(beginHeight / 400);
        axisLines[0].setStroke(Color.DARKGRAY);
        this.getChildren().add(axisLines[0]);
        axisLines[1] = new Line(beginWidth / 2, 0, beginWidth / 2, beginHeight);
        axisLines[1].setStrokeWidth(beginWidth / 400);
        axisLines[1].setStroke(Color.DARKGRAY);
        this.getChildren().add(axisLines[1]);
        // to add background grid lines:
        for (int i = 0; i < 2 * MAX_NUMBER_OF_GRIDLINES; i++) {
            Label tempLabel = new Label();
            labelsForAxis.add(tempLabel);

            this.getChildren().add(tempLabel);
            Line tempLine = new Line();
            tempLine.setStroke(Color.DARKGRAY);
            tempLine.setStrokeWidth(0.2);
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
        // to display the grid lines and axis lines in the beginning
        drawPlotArea();
    }

    /**
     * Adds a new function at the specified index in the list of functions.
     *
     * @param index    The index at which to add the function.
     * @param function The function to add.
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
            showErrorWindow();
            changeFunctionVisibility(index, false);
        }
        drawPlotArea();
    }

    /**
     * Adds a new function at the end of the list of functions.
     *
     * @param function The function to add.
     */
    public void addFunction(Function function) {
        addFunction(functions.size(), function);
    }

    /**
     * Removes a function from the list of functions at the specified index.
     *
     * @param index The index of the function to remove.
     */
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

    /**
     * Adds a variable at the specified index in the list of variables.
     *
     * @param index    The index at which to add the variable.
     * @param argument The variable to add.
     */
    public void addVariable(int index, Argument argument) {
        if (!argument.checkSyntax()) {
            showErrorWindow();
            argument = new Argument("rxeydtcugvbih = 1"); // no clue what the error could be, but better save than sorry
        }
        for (PlotFunction f : functions) {
            f.getFunction().addArguments(argument);
        }
        variables.add(index, argument);
        drawPlotArea();
    }

    /**
     * Removes a variable from the list of variables at the specified index.
     *
     * @param index The index of the variable to remove.
     */
    public void removeVariable(int index) {
        for (PlotFunction f : functions) {
            f.getFunction().removeArguments(variables.get(index));

        }
        variables.remove(index);
        drawPlotArea();
        //todo
        // remove it from all functions as well
    }

    /**
     * Updates the plotting area by redrawing the grid lines, axes, and functions.
     */
    private void drawPlotArea() {
        // to calculate and set the new start and end positions of the axis lines:
        double xAxisPos = height + offset[1] / plotCoordinatesHeight * height;
        double yAxisPos = -offset[0] / plotCoordinatesWidth * width;
        yAxisPos = (yAxisPos > width) ? -20 : yAxisPos;
        axisLines[0].setStartY(xAxisPos);
        axisLines[0].setEndY(xAxisPos);
        axisLines[1].setStartX(yAxisPos);
        axisLines[1].setEndX(yAxisPos);

        // to calculate and set the new start and end positions of the background grid lines (the calculated Positions are also used for the grid line labels):
        double distanceBetweenGridlinesX = getNumberOfGridLinesWithCurrentZoomFactor(plotCoordinatesWidth), distanceToFirstLineX;
        double distanceBetweenGridlinesY = getNumberOfGridLinesWithCurrentZoomFactor(plotCoordinatesHeight), distanceToFirstLineY;
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
                if (i != MAX_NUMBER_OF_GRIDLINES - 1) {
                    labelsForAxis.get(i).setText(Double.toString(Math.round((offset[0] + distanceToFirstLineX / width * plotCoordinatesWidth + distanceBetweenGridlinesX / width * plotCoordinatesWidth * i) * 100000000) / 100000000.0));
                    labelsForAxis.get(i).setLayoutX(d - 2);
                    labelsForAxis.get(i).setLayoutY(height - 20);
                } else {
                    labelsForAxis.get(i).setLayoutX(-100);
                }
            } else {
                gridLines.get(i).setStartX(-10);
                gridLines.get(i).setEndX(-10);
                labelsForAxis.get(i).setLayoutX(-100);
            }
        }
        d = distanceToFirstLineY;
        for (int i = MAX_NUMBER_OF_GRIDLINES; i < 2 * MAX_NUMBER_OF_GRIDLINES; d += distanceBetweenGridlinesY, i++) {
            if (d < height) {
                gridLines.get(i).setStartY(height - d);
                gridLines.get(i).setEndY(height - d);
                if (i != MAX_NUMBER_OF_GRIDLINES) {
                    labelsForAxis.get(i).setText(Double.toString(Math.round((offset[1] + distanceToFirstLineY / height * plotCoordinatesHeight + distanceBetweenGridlinesY / height * plotCoordinatesHeight * (i - MAX_NUMBER_OF_GRIDLINES)) * 10000000) / 10000000.0));
                    labelsForAxis.get(i).setLayoutX(20);
                    labelsForAxis.get(i).setLayoutY(height - d);
                } else {
                    labelsForAxis.get(i).setLayoutX(-100);
                }
            } else {
                gridLines.get(i).setStartY(-10);
                gridLines.get(i).setEndY(-10);
                labelsForAxis.get(i).setLayoutX(-100);
            }
        }
        // recalculate and redraw all functions
        for (PlotFunction f : functions) {
            f.recalculateLinesPosition(offset, plotCoordinatesWidth, plotCoordinatesHeight, width, height);
        }
    }

    /**
     * Calculates the number of grid lines that need to be displayed with the current zoom-factor
     *
     * @param coordinatesWidth  The span of the interval which gets displayed.
     * @return                  the number of grid lines that should be displayed
     */
    private double getNumberOfGridLinesWithCurrentZoomFactor(double coordinatesWidth) {
        while (coordinatesWidth > MAX_NUMBER_OF_GRIDLINES) {
            coordinatesWidth *= 0.1;
        }
        while (coordinatesWidth < MAX_NUMBER_OF_GRIDLINES * 0.1) {
            coordinatesWidth *= 10;
        }
        if (coordinatesWidth < MAX_NUMBER_OF_GRIDLINES * 0.2) {
            coordinatesWidth *= 5;
        } else if (coordinatesWidth < MAX_NUMBER_OF_GRIDLINES >> 1) {
            coordinatesWidth *= 2;
        }
        return coordinatesWidth;
    }

    /**
     * Changes the plotted area by translating and scaling the coordinate system.
     * It also calls the drawPlotArea() method to redraw the plot area.
     *
     * @param deltaX                       The translation amount in the x-direction.
     * @param deltaY                       The translation amount in the y-direction.
     * @param plotCoordsWidthChangeFactor  The scaling factor for the width of the plotting area.
     * @param plotCoordsHeightChangeFactor The scaling factor for the height of the plotting area.
     */
    public void changePlottedArea(double deltaX, double deltaY, double plotCoordsWidthChangeFactor, double plotCoordsHeightChangeFactor) {
        this.offset[0] += deltaX;
        this.offset[1] += deltaY;
        this.plotCoordinatesWidth *= plotCoordsWidthChangeFactor;
        this.plotCoordinatesHeight *= plotCoordsHeightChangeFactor;
        plotScaleEditTextFields[0].setText(String.format("%f", offset[0]));
        plotScaleEditTextFields[1].setText(String.format("%f", plotCoordinatesWidth));
        plotScaleEditTextFields[2].setText(String.format("%f", offset[1]));
        plotScaleEditTextFields[3].setText(String.format("%f", plotCoordinatesHeight));
        drawPlotArea();
    }

    /**
     * This method sets the plotted area of the graph with the given parameters.
     * It also calls the drawPlotArea() method to redraw the plot area.
     *
     * @param deltaX           The change in X direction from the origin (starts from 0 from left to right if 0).
     * @param deltaY           The change in Y direction from the origin (starts from 0 from bottom to top if 0).
     * @param plotCoordsWidth  The width of the plot coordinates.
     * @param plotCoordsHeight The height of the plot coordinates.
     */
    public void setPlottedArea(double deltaX, double deltaY, double plotCoordsWidth, double plotCoordsHeight) {
        this.offset[0] = deltaX;
        this.offset[1] = deltaY;
        this.plotCoordinatesWidth = plotCoordsWidth;
        this.plotCoordinatesHeight = plotCoordsHeight;
        drawPlotArea();
    }

    /**
     * Resizes all elements of the plot area to the new specified dimensions.
     *
     * @param newWidth  The new width of the plot area.
     * @param newHeight The new height of the plot area.
     */
    public void resize(double newWidth, double newHeight) {
        this.width = newWidth;
        this.height = newHeight * 0.95;
        this.background.setWidth(newWidth);
        this.background.setHeight(newHeight);
        this.axisLines[0].setEndX(width);
        this.axisLines[1].setEndY(height);

        for (int i = 0; i < 4; i++) {
            plotScaleEditLabels[i].setLayoutY(newHeight * 0.96);
            plotScaleEditLabels[i].setLayoutX(0.25 * i * width);
            plotScaleEditLabels[i].setPrefHeight(0.03 * newHeight - 2);
            plotScaleEditTextFields[i].setLayoutY(newHeight * 0.96);
            plotScaleEditTextFields[i].setLayoutX(40 + 0.25 * i * width);
            plotScaleEditTextFields[i].setPrefWidth(0.25 * width - 42);
            plotScaleEditTextFields[i].setPrefHeight(0.03 * newHeight - 2);
        }

        for (int i = 0; i < 2 * MAX_NUMBER_OF_GRIDLINES; i++) {
            Line tempLine = gridLines.get(i);
            if (i < MAX_NUMBER_OF_GRIDLINES) {
                tempLine.setEndY(height);
            } else {
                tempLine.setEndX(width);
            }
        }
        drawPlotArea();
    }

    /**
     * Changes the visibility of a function at the specified index.
     *
     * @param index      The index of the function whose visibility to change.
     * @param visibility The new visibility state of the function (true for visible, false for hidden).
     */
    public void changeFunctionVisibility(int index, boolean visibility) {
        functions.get(index).changeFunctionVisibility(visibility);
    }

    /**
     * This method shows an error window with a message "Syntax error!".
     * The window contains an "OK" button that closes the window when clicked.
     */
    public void showErrorWindow() {

        Stage stage = new Stage();
        Button b = new Button("ok");
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });
        Label l = new Label("Syntax error!");
        l.setPadding(new Insets(10));
        b.setPadding(new Insets(10, 20, 10, 20));
        VBox vb = new VBox(l, b);
        vb.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vb, 200, 100);
        stage.setTitle("Error");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.setAlwaysOnTop(true);

        stage.show();
    }

    /**
     * Toggles the visibility of a function at the specified index.
     *
     * @param index The index of the function whose visibility to toggle.
     */
    public void changeFunctionVisibility(int index) {
        functions.get(index).changeFunctionVisibility();
    }

    /**
     * This method toggles the dark background of the plot area.
     * It changes the color of the background rectangle based on the darkMode parameter.
     *
     * @param darkMode A boolean value indicating whether dark mode should be enabled.
     */
    public void toggleDarkMode(boolean darkMode) {
        if (darkMode) {
            background.setFill(Color.rgb(30, 30, 30));
        } else {
            background.setFill(Color.LIGHTGRAY);

        }
    }
}
