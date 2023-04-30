/**
 * Layout is a custom JavaFX Group that organizes the UI components for the Plotter application.
 * It manages the placement of the plot area, scroll panes for functions and constants,
 * and buttons for various mathematical operations.
 *
 * @author Plotter
 * @version 1.0
 */
package com.example.plotter;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Layout extends Group {
    private TextField selectedTextField;
    private final VBox functions = new VBox();
    private final VBox constants = new VBox();

    /**
     * Constructs a Layout object with the given dimensions.
     *
     * @param width  The width of the layout.
     * @param height The height of the layout.
     */
    public Layout(double width, double height) {
        PlotArea plotArea = new PlotArea(width * 0.666 - 4, height - 4);
        this.getChildren().add(plotArea);
        plotArea.setLayoutX(2);
        plotArea.setLayoutY(2);

        ScrollPane scrollPaneFunctions = new ScrollPane();
        scrollPaneFunctions.setLayoutX(width * 0.666 + 4);
        scrollPaneFunctions.setLayoutY(height * 0.05);
        scrollPaneFunctions.setContent(functions);
        scrollPaneFunctions.setPrefWidth(width * 0.334 - 8);
        scrollPaneFunctions.setPrefHeight(height * 0.2);
        this.getChildren().add(scrollPaneFunctions);
        ScrollPane scrollPaneVariables = new ScrollPane();
        scrollPaneVariables.setLayoutX(width * 0.666 + 4);
        scrollPaneVariables.setLayoutY(height * 0.3);
        scrollPaneVariables.setContent(constants);
        scrollPaneVariables.setPrefWidth(width * 0.334 - 8);
        scrollPaneVariables.setPrefHeight(height * 0.2);
        this.getChildren().add(scrollPaneVariables);
        Button addFunctionButton = new Button("+");
        functions.getChildren().add(addFunctionButton);
        addFunctionButton.setStyle("-fx-background-color: #CCFF99");
        addFunctionButton.setOnAction(actionEvent -> {

            HBox newRow = new ScrollPaneFunctionsElement(functions.getChildren().size() - 1, plotArea, functions).getContent();
            newRow.getChildren().get(0).setOnMouseClicked(mouseEvent -> selectedTextField = (TextField) newRow.getChildren().get(0));
            functions.getChildren().add(functions.getChildren().size() - 1, newRow);
        });
        Button addVariableButton = new Button("+");
        constants.getChildren().add(addVariableButton);
        addVariableButton.setStyle("-fx-background-color: #CCFF99");
        addVariableButton.setOnAction(actionEvent -> {

            HBox newRow = new ScrollPaneVariablesElement(constants.getChildren().size() - 1, plotArea, constants).getContent();
            newRow.getChildren().get(0).setOnMouseClicked(mouseEvent -> selectedTextField = (TextField) newRow.getChildren().get(0));
            constants.getChildren().add(constants.getChildren().size() - 1, newRow);
        });

        //todo buttons

        Button[][] operation = new Button[3][4];
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
                operation[x][y].setPrefWidth(width / 10);
                operation[x][y].setPrefHeight(height * 0.09);
                operation[x][y].setLayoutX(width * (0.666 + 0.111 * x) + 4);
                operation[x][y].setLayoutY(height * (0.55 + 0.1 * y));
            }
        }
    }

    /**
     * Resizes the layout and its components based on the new dimensions.
     */
    public void resize() {
        // Implementation...
    }
}
