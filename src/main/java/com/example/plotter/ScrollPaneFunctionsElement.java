package com.example.plotter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;

public class ScrollPaneFunctionsElement {
    private static ArrayList<Integer> indexes = new ArrayList<>();
    private int index;
    private PlotArea plotArea;
    private HBox content;

    public ScrollPaneFunctionsElement(int index, PlotArea plotArea, VBox root) {
        indexes.add(index, index);
        this.index = index;
        this.plotArea = plotArea;
        String beginFunctionName = Character.toString(97 + index);
        plotArea.addFunction(index, new Function(beginFunctionName + "(x) = x"));
        TextField newFunctionTextField = new TextField(beginFunctionName + "(x)= x");
        newFunctionTextField.setPrefWidth(plotArea.getWidth() * 0.49 - 130);
        newFunctionTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    Function f = new Function(newFunctionTextField.getText());
                    plotArea.removeFunction(indexes.get(index));
                    plotArea.addFunction(indexes.get(index), f);
                    // todo still need to test if the function is even valid
                }
            }
        });
        /*
        newFunctionTextField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedTextField = newFunctionTextField;
            }
        });
         */
        Button deleteButton = new Button("\uD83D\uDDD1");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.getChildren().remove(content);
                plotArea.removeFunction(indexes.get(index));
                for (int i = index; i < indexes.size(); i++) {
                    indexes.set(i, indexes.get(i) - 1);
                }
            }
        });
        Button derivationButton = new Button("f'");
        derivationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String[] funcParts = newFunctionTextField.getText().split("=");
                if (funcParts.length != 2) {
                    return;
                }
                newFunctionTextField.setText(funcParts[0] + "= der(" + funcParts[1] + ", x)");
                Function f = new Function(newFunctionTextField.getText());
                plotArea.removeFunction(indexes.get(index));
                plotArea.addFunction(indexes.get(index), f);
            }
        });
        Button visibilityButton = new Button("\uD83D\uDC41");
        visibilityButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                plotArea.changeFunctionVisibility(indexes.get(index));
            }
        });
        Button functionAnalysisInformationButton = new Button("i");
        content = new HBox(newFunctionTextField, derivationButton, deleteButton, visibilityButton, functionAnalysisInformationButton);


    }

    public HBox getContent() {
        return content;
    }
}
