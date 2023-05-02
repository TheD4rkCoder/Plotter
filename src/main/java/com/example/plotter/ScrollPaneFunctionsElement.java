/**
 * ScrollPaneFunctionsElement represents a single function element in the scroll pane, including a TextField for input,
 * and buttons for derivative calculation, deletion, visibility, and function analysis.
 *
 * @author Plotter
 * @version 1.0
 */
package com.example.plotter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;
import java.util.Comparator;


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
    public ScrollPaneFunctionsElement(int index, PlotArea plotArea, VBox root, Layout layout) {

        StringBuilder beginFunctionName = new StringBuilder(new String()); //= Character.toString(97 + ((index > 3) ? index + 1 : index)%26);
        int ind = indexes.size();
        indexes.add(index, index);
        while (ind >= 0) {
            beginFunctionName.append(Character.toString(97 + ((ind % 24 > 3) ? (ind % 24 > 21) ? ind % 24 + 2 : ind % 24 + 1 : ind % 24)));
            ind -= 24;
        }
        beginFunctionName.append("(x) = x");
        init(index, plotArea, root, layout, beginFunctionName.toString());
    }
    public ScrollPaneFunctionsElement(int index, PlotArea plotArea, VBox root, Layout layout, String equation) { // layout for adding a new Function for the derivative
        init(index, plotArea, root, layout, equation);
    }

    private void init(int index, PlotArea plotArea, VBox root, Layout layout, String equation) {
        indexes.add(index, index);
        this.index = index;
        this.plotArea = plotArea;
        plotArea.addFunction(index, new Function(equation));
        TextField newFunctionTextField = new TextField(equation);
        newFunctionTextField.setPrefWidth(plotArea.getWidth() * 0.49 - 135);
        newFunctionTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                Function f = new Function(newFunctionTextField.getText());
                plotArea.removeFunction(indexes.get(index));
                plotArea.addFunction(indexes.get(index), f);
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
            layout.newFunction("der" + funcParts[0] + "= der(" + funcParts[0] + ", x)");
        });
        Button visibilityButton = new Button("\uD83D\uDC41");
        visibilityButton.setStyle("-fx-background-color: #CCFF99");
        visibilityButton.setOnAction(actionEvent -> {
            plotArea.changeFunctionVisibility(indexes.get(index));
            if (!visibilityButton.getStyle().contains("-fx-background-color: #FF0F0F")) {
                visibilityButton.setStyle("-fx-background-color: #FF0F0F");
            } else {
                visibilityButton.setStyle("-fx-background-color: #CCFF99");

            }
        });
        Button functionAnalysisInformationButton = new Button("i");
        functionAnalysisInformationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // todo label with function-analysis data
            }
        });
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
