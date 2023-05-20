package com.example.plotter;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.mariuszgromada.math.mxparser.Argument;

import java.util.ArrayList;

/**
 * ScrollPaneVariablesElement represents a single variable element in the scroll pane, including a TextField for input,
 * and a button for deletion.
 *
 * @author Plotter
 * @version 1.0
 */
public class ScrollPaneVariablesElement extends HBox {
    private static final ArrayList<Integer> indexes = new ArrayList<>();
    private int index;
    private PlotArea plotArea;
    private TextField newVariableTextField;

    /**
     * Constructor for creating a new ScrollPaneVariablesElement.
     *
     * @param index    The index of the variable in the PlotArea.
     * @param plotArea The PlotArea to which the variable belongs.
     * @param root     The VBox containing the elements of the ScrollPaneVariablesElement.
     */
    public ScrollPaneVariablesElement(int index, PlotArea plotArea, VBox root) {
        indexes.add(index, index);
        this.index = index;
        this.plotArea = plotArea;
        StringBuilder beginArgumentName = new StringBuilder(new String()); //Character.toString(65 + index);
        int ind = indexes.size() - 1;
        while (ind >= 0) {
            beginArgumentName.append(Character.toString(65 + ((ind % 25 > 1) ? (ind % 25 + 1) : (ind % 25))));
            ind -= 25;
        }
        plotArea.addVariable(index, new Argument(beginArgumentName + " = 1"));
        newVariableTextField = new TextField(beginArgumentName + " = 1");
        newVariableTextField.setPrefWidth(plotArea.getWidth() * 0.49 - 30);
        newVariableTextField.setPrefHeight(35);
        newVariableTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                Argument a = new Argument(newVariableTextField.getText());
                if (!a.checkSyntax()) {
                    plotArea.showErrorWindow();
                    return;
                }
                plotArea.removeVariable(indexes.get(index));
                plotArea.addVariable(indexes.get(index), a);
            }
        });
        Button deleteButton = new Button("\uD83D\uDDD1");
        deleteButton.setOnAction(actionEvent -> {
            root.getChildren().remove(this);
            plotArea.removeVariable(indexes.get(index));
            for (int i = index; i < indexes.size(); i++) {
                indexes.set(i, indexes.get(i) - 1);
            }
        });
        this.getChildren().addAll(newVariableTextField, deleteButton);
    }

    /**
     * Resizes the newVariableTextField in correlation to the width of the plotArea.
     * The width of the newVariableTextField is set to 49% of the plotArea width minus 30 pixels for the button.
     */
    public void resizeInCorrelationToPlotArea() {
        newVariableTextField.setPrefWidth(plotArea.getWidth() * 0.49 - 30);
    }

}
