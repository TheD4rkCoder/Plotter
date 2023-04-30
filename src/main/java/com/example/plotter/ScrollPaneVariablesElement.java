package com.example.plotter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.mariuszgromada.math.mxparser.Argument;

import java.util.ArrayList;

public class ScrollPaneVariablesElement {
    private static ArrayList<Integer> indexes = new ArrayList<>();
    private int index;
    private PlotArea plotArea;
    private HBox content;

    public ScrollPaneVariablesElement(int index, PlotArea plotArea, VBox root) {
        indexes.add(index, index);
        this.index = index;
        this.plotArea = plotArea;
        plotArea.addVariable(index, new Argument("a = 0"));
        TextField newVariableTextField = new TextField("");
        newVariableTextField.setPrefWidth(plotArea.getWidth()/2*0.96-10);
        newVariableTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    Argument a = new Argument(newVariableTextField.getText());
                    if (!a.checkSyntax()) {
                        return;
                    }
                    plotArea.removeVariable(indexes.get(index));
                    plotArea.addVariable(indexes.get(index), a);
                }
            }
        });
        Button deleteButton = new Button("\uD83D\uDDD1");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.getChildren().remove(content);
                plotArea.removeVariable(indexes.get(index));
                for (int i = index; i < indexes.size(); i++) {
                    indexes.set(i, indexes.get(i) - 1);
                }
            }
        });
        content = new HBox(newVariableTextField, deleteButton);


    }

    public HBox getContent() {
        return content;
    }
}
