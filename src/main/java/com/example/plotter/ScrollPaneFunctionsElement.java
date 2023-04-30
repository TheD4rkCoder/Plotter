/**
 * ScrollPaneFunctionsElement represents a single function element in the scroll pane, including a TextField for input,
 * and buttons for derivative calculation, deletion, visibility, and function analysis.
 *
 * @author Plotter
 * @version 1.0
 */
package com.example.plotter;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;


public class ScrollPaneFunctionsElement {
    private static final ArrayList<Integer> indexes = new ArrayList<>();
    private int index;
    private PlotArea plotArea;
    private HBox content;

    /**
     * Constructor for creating a new ScrollPaneFunctionsElement.
     *
     * @param index    The index of the function in the PlotArea.
     * @param plotArea The PlotArea to which the function belongs.
     * @param root     The VBox containing the elements of the ScrollPaneFunctionsElement.
     */
    public ScrollPaneFunctionsElement(int index, PlotArea plotArea, VBox root) {
        indexes.add(index, index);
        this.index = index;
        this.plotArea = plotArea;
        String beginFunctionName = Character.toString(97 + index);
        plotArea.addFunction(index, new Function(beginFunctionName + "(x) = x"));
        TextField newFunctionTextField = new TextField(beginFunctionName + "(x)= x");
        newFunctionTextField.setPrefWidth(plotArea.getWidth() * 0.49 - 130);
        newFunctionTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                Function f = new Function(newFunctionTextField.getText());
                plotArea.removeFunction(indexes.get(index));
                plotArea.addFunction(indexes.get(index), f);
                // todo still need to test if the function is even valid
            }
        });

        Button deleteButton = new Button("\uD83D\uDDD1");
        deleteButton.setOnAction(actionEvent -> {
            root.getChildren().remove(content);
            plotArea.removeFunction(indexes.get(index));
            for (int i = index; i < indexes.size(); i++) {
                indexes.set(i, indexes.get(i) - 1);
            }
        });
        Button derivationButton = new Button("f'");
        derivationButton.setOnAction(actionEvent -> {
            String[] funcParts = newFunctionTextField.getText().split("=");
            if (funcParts.length != 2) {
                return;
            }
            newFunctionTextField.setText(funcParts[0] + "= der(" + funcParts[1] + ", x)");
            Function f = new Function(newFunctionTextField.getText());
            plotArea.removeFunction(indexes.get(index));
            plotArea.addFunction(indexes.get(index), f);
        });
        Button visibilityButton = new Button("\uD83D\uDC41");
        visibilityButton.setOnAction(actionEvent -> plotArea.changeFunctionVisibility(indexes.get(index)));
        Button functionAnalysisInformationButton = new Button("i");
        content = new HBox(newFunctionTextField, derivationButton, deleteButton, visibilityButton, functionAnalysisInformationButton);


    }

    /**
     * Returns the HBox containing the elements of the ScrollPaneFunctionsElement.
     *
     * @return The HBox containing the TextField and buttons for the ScrollPaneFunctionsElement.
     */
    public HBox getContent() {
        return content;
    }
}
