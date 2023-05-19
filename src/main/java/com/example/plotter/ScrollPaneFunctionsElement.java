package com.example.plotter;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.mariuszgromada.math.mxparser.Function;
//import org.matheclipse.core.eval.ExprEvaluator; // todo
//import org.matheclipse.core.expression.Expr;

import java.util.ArrayList;

/**
 * ScrollPaneFunctionsElement represents a single function element in the scroll pane, including a TextField for input,
 * and buttons for derivative calculation, deletion, visibility, and function analysis.
 *
 * @author Plotter
 * @version 1.0
 */
public class ScrollPaneFunctionsElement extends HBox {
    private static final ArrayList<Integer> indexes = new ArrayList<>();
    private int index;
    private PlotArea plotArea;
    private TextField newFunctionTextField;

    /**
     * Constructs a new ScrollPaneFunctionsElement with the specified index, plotArea, root VBox, and layout.
     * The constructor initializes the function name based on the index, creating a name like "a(x) = x" where 'a' is derived from the index.
     *
     * @param index The index of the ScrollPaneFunctionsElement.
     * @param plotArea The PlotArea associated with the ScrollPaneFunctionsElement.
     * @param root The root VBox that contains the ScrollPaneFunctionsElement.
     * @param layout The Layout associated with the ScrollPaneFunctionsElement.
     */
    public ScrollPaneFunctionsElement(int index, PlotArea plotArea, VBox root, Layout layout) {
        StringBuilder beginFunctionName = new StringBuilder();
        int ind = indexes.size();
        while (ind >= 0) {
            beginFunctionName.append(Character.toString(97 + ((ind % 24 > 3) ? (ind % 24 > 21) ? ind % 24 + 2 : ind % 24 + 1 : ind % 24)));
            ind -= 24;
        }
        beginFunctionName.append("(x) = x");
        init(index, plotArea, root, layout, beginFunctionName.toString());
    }


    /**
     * Constructs a new ScrollPaneFunctionsElement with the specified index, plotArea, root VBox, layout, and equation.
     *
     * @param index The index of the ScrollPaneFunctionsElement.
     * @param plotArea The PlotArea associated with the ScrollPaneFunctionsElement.
     * @param root The root VBox that contains the ScrollPaneFunctionsElement.
     * @param layout The Layout associated with the ScrollPaneFunctionsElement.
     * @param equation The equation associated with the ScrollPaneFunctionsElement.
     */
    public ScrollPaneFunctionsElement(int index, PlotArea plotArea, VBox root, Layout layout, String equation) {
        init(index, plotArea, root, layout, equation);
    }


    private void init(int index, PlotArea plotArea, VBox root, Layout layout, String equation) {
        indexes.add(index, index);
        this.index = index;
        this.plotArea = plotArea;
        Button visibilityButton = new Button("\uD83D\uDC41");
        Button derivationButton = new Button("f'");
        Button deleteButton = new Button("\uD83D\uDDD1");
        //Button functionAnalysisInformationButton = new Button("i"); // todo

        plotArea.addFunction(index, new Function(equation));
        newFunctionTextField = new TextField(equation);
        newFunctionTextField.setPrefWidth(plotArea.getWidth() * 0.49 - 135);
        newFunctionTextField.setPrefHeight(33);
        newFunctionTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                Function f = new Function(newFunctionTextField.getText());
                plotArea.removeFunction(indexes.get(index));
                plotArea.addFunction(indexes.get(index), f);
                visibilityButton.setStyle("-fx-background-color: #66AA33");
            }
        });

        deleteButton.setOnAction(actionEvent -> {
            root.getChildren().remove(this);
            plotArea.removeFunction(indexes.get(index));
            for (int i = index; i < indexes.size(); i++) {
                indexes.set(i, indexes.get(i) - 1);
            }
        });
        derivationButton.setOnAction(actionEvent -> {
            String[] funcParts = newFunctionTextField.getText().split("=");
            if (funcParts.length != 2) {
                return;
            }
            layout.newFunction("der" + funcParts[0] + "= der(" + funcParts[0] + ", x)");
        });
        visibilityButton.setStyle("-fx-background-color: #66AA33");
        visibilityButton.setOnAction(actionEvent -> {
            plotArea.changeFunctionVisibility(indexes.get(index));
            if (!visibilityButton.getStyle().contains("-fx-background-color: #FF0F0F")) {
                visibilityButton.setStyle("-fx-background-color: #FF0F0F");
            } else {
                visibilityButton.setStyle("-fx-background-color: #66AA33");

            }
        });
        /*
        functionAnalysisInformationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // todo label with function-analysis data
            }
        });
         */
        this.getChildren().addAll(newFunctionTextField, derivationButton, deleteButton, visibilityButton); // add functionAnalysisButton later
    }

    /**
     * Resizes the newFunctionTextField in correlation to the width of the plotArea.
     * The width of the newFunctionTextField is set to 49% of the plotArea width minus 135 pixels.
     */
    public void resizeInCorrelationToPlotArea() {
        newFunctionTextField.setPrefWidth(plotArea.getWidth() * 0.49 - 135);
    }


}
