/**
 * ScrollPaneVariablesElement represents a single variable element in the scroll pane, including a TextField for input,
 * and a button for deletion.
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
import org.mariuszgromada.math.mxparser.Argument;

import java.util.ArrayList;

public class ScrollPaneVariablesElement {
    private static final ArrayList<Integer> indexes = new ArrayList<>();
    private int index;
    private PlotArea plotArea;
    private HBox content;

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
        String beginArgumentName = Character.toString(65 + index);
        plotArea.addVariable(index, new Argument(beginArgumentName + " = 1"));
        TextField newVariableTextField = new TextField(beginArgumentName + " = 1");
        newVariableTextField.setPrefWidth(plotArea.getWidth() * 0.49 - 30);
        newVariableTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                Argument a = new Argument(newVariableTextField.getText());
                if (!a.checkSyntax()) {
                    return;
                }
                plotArea.removeVariable(indexes.get(index));
                plotArea.addVariable(indexes.get(index), a);
            }
        });
        Button deleteButton = new Button("\uD83D\uDDD1");
        deleteButton.setOnAction(actionEvent -> {
            root.getChildren().remove(content);
            plotArea.removeVariable(indexes.get(index));
            for (int i = index; i < indexes.size(); i++) {
                indexes.set(i, indexes.get(i) - 1);
            }
        });
        content = new HBox(newVariableTextField, deleteButton);


    }

    /**
     * Returns the HBox containing the elements of the ScrollPaneVariablesElement.
     *
     * @return The HBox containing the TextField and delete button for the ScrollPaneVariablesElement.
     */
    public HBox getContent() {
        return content;
    }
}
