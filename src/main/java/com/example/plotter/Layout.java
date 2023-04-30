package com.example.plotter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;

public class Layout extends Group {
    private double width, height;
    private TextField selectedTextField;
    private ArrayList<TextField> funktionField;
    private ArrayList<TextField> constantField;
    private VBox functions = new VBox();
    private VBox constants = new VBox();
    private ScrollPane scrollPaneVariables = new ScrollPane();
    private ScrollPane scrollPaneFunctions = new ScrollPane();
    private ButtonBar[] buttonBar = new ButtonBar[4];
    private int amountTextfields = 1;
    private int amountConstants = 1;


    public Layout(double width, double height) {
        this.width = width;
        this.height = height;
        PlotArea plotArea = new PlotArea(width * 0.666 - 4, height - 4);
        this.getChildren().add(plotArea);
        plotArea.setLayoutX(2);
        plotArea.setLayoutY(2);

        scrollPaneFunctions.setLayoutX(width * 0.666 + 4);
        scrollPaneFunctions.setLayoutY(height * 0.05);
        scrollPaneFunctions.setContent(functions);
        scrollPaneVariables.setLayoutX(width * 0.666 + 4);
        scrollPaneVariables.setLayoutY(height * 0.3);
        scrollPaneVariables.setContent(constants);
        scrollPaneFunctions.setPrefWidth(width * 0.334 - 8);
        scrollPaneFunctions.setPrefHeight(height * 0.2);
        scrollPaneVariables.setPrefWidth(width * 0.334 - 8);
        scrollPaneVariables.setPrefHeight(height * 0.2);
        this.getChildren().add(scrollPaneFunctions);
        this.getChildren().add(scrollPaneVariables);
        Button addFunctionButton = new Button("+");
        functions.getChildren().add(addFunctionButton);
        addFunctionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                HBox newRow = new ScrollPaneFunctionsElement(functions.getChildren().size() - 1, plotArea, functions).getContent();
                newRow.getChildren().get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectedTextField = (TextField)newRow.getChildren().get(0);
                    }
                });
                functions.getChildren().add(functions.getChildren().size() - 1, newRow);
            }
        });
        Button addVariableButton = new Button("+");
        constants.getChildren().add(addVariableButton);
        addVariableButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                HBox newRow = new ScrollPaneVariablesElement(constants.getChildren().size() - 1, plotArea, constants).getContent();
                newRow.getChildren().get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectedTextField = (TextField)newRow.getChildren().get(0);
                    }
                });
                constants.getChildren().add(constants.getChildren().size() - 1, newRow);
            }
        });

        //todo buttons

        Button [] [] operation = new Button[3][4];
        operation[0][0] = new Button("sin");
        operation[0][0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (selectedTextField!= null) {
                    selectedTextField.setText(selectedTextField.getText() + "sin(");
                }
            }
        });
        operation[1][0] = new Button("cos");
        operation[1][0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (selectedTextField!= null) {
                    selectedTextField.setText(selectedTextField.getText() + "cos(");
                }
            }
        });
        operation[2][0] = new Button("tan");
        operation[2][0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (selectedTextField!= null) {
                    selectedTextField.setText(selectedTextField.getText() + "tan(");
                }
            }
        });
        operation[0][1] = new Button("π");
        operation[0][1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (selectedTextField!= null) {
                    selectedTextField.setText(selectedTextField.getText() + "π");
                }
            }
        });
        operation[1][1] = new Button("e");
        operation[1][1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (selectedTextField!= null) {
                    selectedTextField.setText(selectedTextField.getText() + "e");
                }
            }
        });
        operation[2][1] = new Button("|x|");
        operation[2][1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (selectedTextField!= null) {
                    selectedTextField.setText(selectedTextField.getText() + "abs(");
                }
            }
        });
        operation[0][2] = new Button("logₙm");
        operation[0][2].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (selectedTextField!= null) {
                    selectedTextField.setText(selectedTextField.getText() + "log(n, m)");
                }
            }
        });
        operation[1][2] = new Button("xⁿ");
        operation[1][2].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (selectedTextField!= null) {
                    selectedTextField.setText(selectedTextField.getText() + "^(");
                }
            }
        });
        operation[2][2] = new Button("√");
        operation[2][2].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (selectedTextField!= null) {
                    selectedTextField.setText(selectedTextField.getText() + "√(");
                }
            }
        });
        operation[0][3] = new Button("ln");
        operation[1][3] = new Button("Min");
        operation[2][3] = new Button("Max");
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 3; x++) {
                this.getChildren().add(operation[x][y]);
                operation[x][y].setPrefWidth(width/10);
                operation[x][y].setPrefHeight(height*0.09);
                operation[x][y].setLayoutX(width * (0.666 + 0.111*x) + 4);
                operation[x][y].setLayoutY(height*(0.55+0.1*y));
            }
        }
    }

    public void resize() {

    }



/*
    ScrollPane scrollConst = new ScrollPane();
    ScrollPane scrollFunkt = new ScrollPane();
    Button addFunktion = new Button("+");
    Button addConstant = new Button("+");
    private void setTextonButton(){                     //add text on the buttons
        operation[0][0] = new Button("sin");
        operation[1][0] = new Button("cos");
        operation[2][0] = new Button("tan");

        operation[0][1] = new Button("π");
        operation[1][1] = new Button("e");
        operation[2][1] = new Button("|x|");

        operation[0][2] = new Button("logₛn");
        operation[1][2] = new Button("xⁿ");
        operation[2][2] = new Button("√");

        operation[0][3] = new Button("ln");
        operation[1][3] = new Button("Min");
        operation[2][3] = new Button("Max");

    }

    private void setButtonLayout(Group buttonGroup){    //add every Button to the Group and into the Bars
        setTextonButton();
        for (int i = 0; i < 4; i++){
            buttonBar[i] = new ButtonBar();
            buttonGroup.getChildren().add(buttonBar[i]);
            for (int y = 0; y < 3; y++){
                buttonBar[i].getButtons().add(operation[y][i]);
               }
        }
        //so the button don't get stacked
        for(int i = 0; i<4;i++){
            buttonBar[i].setLayoutY(buttonGroup.getLayoutY()+(i*50));
        }
    }
/*
    public void addNewConstant(Group plotArea){
        TextField newTextField = new TextField();
        newTextField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedTextField = newTextField;
            }
        });
        newTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    plotArea.addVariable(newTextField);
                }
            }
        });
        constantField.add(newTextField);
        constantField.get(amountConstants).setMaxWidth(scrollFunkt.getMaxWidth());
        constants.getChildren().remove(addConstant);
        constants.getChildren().add(constantField.get(amountConstants));
        constants.getChildren().add(addConstant);
        amountConstants++;
    }
    private void setConstantArea(Group constantArea,Group plotArea){
        constantField = new ArrayList<>();
        constantField.add(new TextField());
        scrollConst.setPrefWidth(mainScene.getWidth()-mainScene.getHeight());
        scrollConst.setPrefHeight(mainScene.getHeight());
        constants.getChildren().add(constantField.get(0));
        constantArea.getChildren().add(addConstant); //Button
        addConstant.setOnAction(e->{
            addNewConstant(plotArea);
        });
        constants.getChildren().add(addConstant);
        scrollConst.setContent(constants);

    }

    private void addNewFunktion(){
        funktionField.add(new TextField());
        funktionField.get(amountTextfields).setMaxWidth(scrollFunkt.getMaxWidth());
        funktions.getChildren().remove(addFunktion);
        funktions.getChildren().add(funktionField.get(amountTextfields));
        funktions.getChildren().add(addFunktion);
        amountTextfields++;
    }
    private void setFunktionArea(Group funktionArea){
        funktionField = new ArrayList<>();
        scrollFunkt.setPrefWidth(mainScene.getWidth()-mainScene.getHeight());
        scrollFunkt.setPrefHeight(mainScene.getHeight());
        funktionField.add(new TextField());
        funktions.getChildren().add(funktionField.get(0));
        funktionArea.getChildren().add(addFunktion); //the button to add
        addFunktion.setOnAction(e->{
            addNewFunktion();
        });
        funktions.getChildren().add(addFunktion);
        scrollFunkt.setContent(funktions);

    }
    private void setPosition(Group buttonArea,Group plotArea,Group funktionArea,Group constantArea){

        plotArea.setLayoutX(mainScene.getHeight());
        plotArea.setLayoutY(mainScene.getHeight());

        buttonArea.setLayoutX(mainScene.getHeight());
        buttonArea.setLayoutY(mainScene.getHeight()*0.75);

        constantArea.setLayoutX(mainScene.getHeight());
        constantArea.setLayoutY(mainScene.getHeight()*0.4);

        funktionArea.setLayoutX(mainScene.getHeight());
    }

    public void setEveryGroupLayout(Group buttonArea,Group plotArea,Group funktionArea,Group constantArea) {

        mainScene = root.getScene();
        mainScene.widthProperty().addListener(e->{
            setPosition(buttonArea,plotArea,funktionArea,constantArea);
        });
        mainScene.heightProperty().addListener(e->{
            setPosition(buttonArea,plotArea,funktionArea,constantArea);
        });
        //Positioning of the ButtonArea
        setButtonLayout(buttonArea);

        //Positioning of the ConstantArea
        setConstantArea(constantArea,plotArea);
        constantArea.getChildren().add(scrollConst);
        System.out.println(constantArea.getLayoutY());

        //Positioning of the funktionArea
        setFunktionArea(funktionArea);
        funktionArea.getChildren().add(scrollFunkt);


    }
    */
}
