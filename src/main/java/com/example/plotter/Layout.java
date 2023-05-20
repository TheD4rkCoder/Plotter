package com.example.plotter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Layout is a custom JavaFX Group that organizes the UI components for the Plotter application.
 * It manages the placement of the plot area, scroll panes for functions and constants,
 * and buttons for various mathematical operations.
 *
 * @author Plotter
 * @version 1.0
 */
public class Layout extends Group {
    private TextField selectedTextField;
    private final VBox functions = new VBox();
    private final VBox constants = new VBox();
    private PlotArea plotArea;
    private boolean darkModeEnabled = false;
    private Rectangle background;
    private Button darkModeToggleButton;
    private Button[][] operation = new Button[3][4];
    private ScrollPane scrollPaneFunctions;
    private ScrollPane scrollPaneVariables;
    private Label functionTitle;
    private Label variableTitle;

    /**
     * Constructs a Layout object with the given dimensions.
     *
     * @param width  The width of the layout.
     * @param height The height of the layout.
     */
    public Layout(double width, double height) {
        // to add background:
        background = new Rectangle(0, 0, width, height);
        background.setFill(Color.WHITE);
        this.getChildren().add(background);
        // to add PlotArea (where the functions get plotted):
        plotArea = new PlotArea(width * 0.666 - 4, height * 0.95 - 4);
        this.getChildren().add(plotArea);
        plotArea.setLayoutX(2);
        plotArea.setLayoutY(2);
        // to add the Panel for inputting and editing functions:
        scrollPaneFunctions = new ScrollPane();
        scrollPaneFunctions.setLayoutX(width * 0.666 + 4);
        scrollPaneFunctions.setLayoutY(height * 0.05);
        scrollPaneFunctions.setContent(functions);
        scrollPaneFunctions.setPrefWidth(width * 0.334 - 8);
        scrollPaneFunctions.setPrefHeight(height * 0.2);
        this.getChildren().add(scrollPaneFunctions);
        // to add the Panel for inputting and editing variables:
        scrollPaneVariables = new ScrollPane();
        scrollPaneVariables.setLayoutX(width * 0.666 + 4);
        scrollPaneVariables.setLayoutY(height * 0.3);
        scrollPaneVariables.setContent(constants);
        scrollPaneVariables.setPrefWidth(width * 0.334 - 8);
        scrollPaneVariables.setPrefHeight(height * 0.2);
        this.getChildren().add(scrollPaneVariables);
        // to create the elements inside the panels:
        Button addFunctionButton = new Button("+");
        functions.getChildren().add(addFunctionButton);
        addFunctionButton.setOnAction(actionEvent -> {
            newFunction();
        });
        Button addVariableButton = new Button("+");
        constants.getChildren().add(addVariableButton);
        addVariableButton.setOnAction(actionEvent -> {
            HBox newRow = new ScrollPaneVariablesElement(constants.getChildren().size() - 1, plotArea, constants);
            newRow.getChildren().get(0).setOnMouseClicked(mouseEvent -> selectedTextField = (TextField) newRow.getChildren().get(0));
            constants.getChildren().add(constants.getChildren().size() - 1, newRow);
        });

        // to add the buttons with different mathematical calculations and constants:
        operation[0][0] = new Button("sin");
        operation[0][0].setOnAction(actionEvent -> {
            if (selectedTextField != null) {
                selectedTextField.setText(selectedTextField.getText() + "sin(");
            }
        });
        operation[1][0] = new Button("cos");
        operation[1][0].setOnAction(actionEvent -> {
            if (selectedTextField != null) {
                selectedTextField.setText(selectedTextField.getText() + "cos(");
            }
        });
        operation[2][0] = new Button("tan");
        operation[2][0].setOnAction(actionEvent -> {
            if (selectedTextField != null) {
                selectedTextField.setText(selectedTextField.getText() + "tan(");
            }
        });
        operation[0][1] = new Button("π");
        operation[0][1].setOnAction(actionEvent -> {
            if (selectedTextField != null) {
                selectedTextField.setText(selectedTextField.getText() + "π");
            }
        });
        operation[1][1] = new Button("e");
        operation[1][1].setOnAction(actionEvent -> {
            if (selectedTextField != null) {
                selectedTextField.setText(selectedTextField.getText() + "e");
            }
        });
        operation[2][1] = new Button("|x|");
        operation[2][1].setOnAction(actionEvent -> {
            if (selectedTextField != null) {
                selectedTextField.setText(selectedTextField.getText() + "abs(");
            }
        });
        operation[0][2] = new Button("logₙ x");
        operation[0][2].setOnAction(actionEvent -> {
            if (selectedTextField != null) {
                selectedTextField.setText(selectedTextField.getText() + "log(n, x)");
            }
        });
        operation[1][2] = new Button("xⁿ");
        operation[1][2].setOnAction(actionEvent -> {
            if (selectedTextField != null) {
                selectedTextField.setText(selectedTextField.getText() + "^(");
            }
        });
        operation[2][2] = new Button("√");
        operation[2][2].setOnAction(actionEvent -> {
            if (selectedTextField != null) {
                selectedTextField.setText(selectedTextField.getText() + "√(");
            }
        });
        operation[0][3] = new Button("ln");
        operation[0][3].setOnAction(actionEvent -> {
            if (selectedTextField != null) {
                selectedTextField.setText(selectedTextField.getText() + "ln(");
            }
        });

        //operation[1][3] = new Button("sin⁻¹");
        operation[1][3] = new Button("%");
        operation[1][3].setOnAction(actionEvent -> {
            if (selectedTextField != null) {
                selectedTextField.setText(selectedTextField.getText() + "mod(x, n)");
            }
        });
        operation[2][3] = new Button("ⁿ√");
        operation[2][3].setOnAction(actionEvent -> {
            if (selectedTextField != null) {
                selectedTextField.setText(selectedTextField.getText() + "root(n, x)");
            }
        });
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 3; x++) {
                this.getChildren().add(operation[x][y]);
                operation[x][y].setFocusTraversable(false);
                operation[x][y].setPrefWidth(width * 0.1);
                operation[x][y].setPrefHeight(height * 0.09);
                operation[x][y].setLayoutX(width * (0.666 + 0.111 * x) + 4);
                operation[x][y].setLayoutY(height * (0.55 + 0.1 * y));
            }
        }
        // to add the Titles above the panels for functions and variables
        functionTitle = new Label("Funktionen");
        functionTitle.setLayoutX(width * 0.666 + 4);
        functionTitle.setLayoutY(height * 0.01);
        functionTitle.setStyle("-fx-font-size: 24");
        this.getChildren().add(functionTitle);
        variableTitle = new Label("Variablen");
        variableTitle.setLayoutX(width * 0.666 + 4);
        variableTitle.setLayoutY(height * 0.26);
        variableTitle.setStyle("-fx-font-size: 24");
        this.getChildren().add(variableTitle);
        // to create a button to toggle between dark and light mode:
        darkModeToggleButton = new Button("\u263E");
        darkModeToggleButton.setPrefWidth(width * 0.1);
        darkModeToggleButton.setPrefHeight(height * 0.03);
        darkModeToggleButton.setLayoutX(width * 0.666 + 4);
        darkModeToggleButton.setLayoutY(height * 0.95);

        darkModeToggleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (darkModeToggleButton.getText().equals("\u263C")) {
                    darkModeToggleButton.setText("\u263E");
                } else {
                    darkModeToggleButton.setText("\u263C");
                }
                toggleDarkMode();
            }
        });
        this.getChildren().add(darkModeToggleButton);
    }

    /**
     * This method creates a new function row in the UI without an initial equation.
     * It creates a new ScrollPaneFunctionsElement, sets the click listener for the TextField in the row,
     * and adds the new row to the functions HBox.
     */
    public void newFunction() {
        HBox newRow = new ScrollPaneFunctionsElement(functions.getChildren().size() - 1, plotArea, functions, this);
        // click-listener to save the current ScrollPaneFunctionsElement.
        // This way the last selected TextField is in "selectedTextField".
        // When a button of a constant or a calculation is pressed, the corresponding text gets added in the selectedTextField.
        newRow.getChildren().get(0).setOnMouseClicked(mouseEvent -> selectedTextField = (TextField) newRow.getChildren().get(0));
        functions.getChildren().add(functions.getChildren().size() - 1, newRow);
    }

    /**
     * This method creates a new function row in the UI with a given initial equation.
     * It creates a new ScrollPaneFunctionsElement with the provided equation, sets the click listener for the TextField in the row,
     * and adds the new row to the functions HBox.
     *
     * @param equation The initial equation for the new function row.
     */
    public void newFunction(String equation) {
        HBox newRow = new ScrollPaneFunctionsElement(functions.getChildren().size() - 1, plotArea, functions, this, equation);
        // click-listener to save the current ScrollPaneFunctionsElement.
        // This way the last selected TextField is in "selectedTextField".
        // When a button of a constant or a calculation is pressed, the corresponding text gets added in the selectedTextField.
        newRow.getChildren().get(0).setOnMouseClicked(mouseEvent -> selectedTextField = (TextField) newRow.getChildren().get(0));
        functions.getChildren().add(functions.getChildren().size() - 1, newRow);
    }

    /**
     * This method toggles the dark mode of the application.
     * It changes the darkModeEnabled flag, toggles the dark mode of the plot area, and switches the application's stylesheet.
     */
    public void toggleDarkMode() {
        darkModeEnabled = !darkModeEnabled;
        plotArea.toggleDarkMode(darkModeEnabled);
        if (darkModeEnabled) {
            background.setFill(Color.rgb(20, 20, 20));
            this.getStylesheets().remove(getClass().getResource("style.css").toExternalForm());
            this.getStylesheets().add(getClass().getResource("darkModeStyle.css").toExternalForm());
        } else {
            background.setFill(Color.WHITE);
            this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            this.getStylesheets().remove(getClass().getResource("darkModeStyle.css").toExternalForm());
        }
    }


    /**
     * Resizes the layout and its components based on the new dimensions.
     * @param width The new width of the layout.
     * @param height The new height of the layout.
     */
    public void resize(double width, double height) {
        background.setWidth(width);
        background.setHeight(height);
        plotArea.resize(width * 0.666 - 4, height - 4);
        for (Node n : functions.getChildren()) {
            if (n instanceof ScrollPaneFunctionsElement) {
                ((ScrollPaneFunctionsElement) n).resizeInCorrelationToPlotArea();
            }
        }
        for (Node n : constants.getChildren()) {
            if (n instanceof ScrollPaneVariablesElement) {
                ((ScrollPaneVariablesElement) n).resizeInCorrelationToPlotArea();
            }
        }
        // Buttons

        darkModeToggleButton.setPrefWidth(width * 0.1);
        darkModeToggleButton.setPrefHeight(height * 0.03);
        darkModeToggleButton.setLayoutX(width * 0.666 + 4);
        darkModeToggleButton.setLayoutY(height * 0.95);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 3; x++) {
                operation[x][y].setPrefWidth(width * 0.1);
                operation[x][y].setPrefHeight(height * 0.09);
                operation[x][y].setLayoutX(width * (0.666 + 0.111 * x) + 4);
                operation[x][y].setLayoutY(height * (0.55 + 0.1 * y));
            }
        }
        scrollPaneFunctions.setLayoutX(width * 0.666 + 4);
        scrollPaneFunctions.setLayoutY(height * 0.01 + 30);
        scrollPaneFunctions.setPrefWidth(width * 0.334 - 8);
        scrollPaneFunctions.setPrefHeight((height-60) * 0.22);

        scrollPaneVariables.setLayoutX(width * 0.666 + 4);
        scrollPaneVariables.setLayoutY(height * 0.26 + 30);
        scrollPaneVariables.setPrefWidth(width * 0.334 - 8);
        scrollPaneVariables.setPrefHeight((height-60) * 0.22);

        functionTitle.setLayoutX(width * 0.666 + 4);
        functionTitle.setLayoutY(height * 0.01);
        variableTitle.setLayoutX(width * 0.666 + 4);
        variableTitle.setLayoutY(height * 0.26);
    }
}
