package com.example.plotter;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Objects;

public class Layout {
    Group root;
    Scene mainScene;
    TextField selectedTextField;
    ArrayList <TextField> funktionField;
    ArrayList <TextField> constantField;
    VBox funktions = new VBox();
    VBox constants = new VBox();
    private int amountTextfields=1;
    private int amountConstants=1;

    public Layout(Group root) {
        this.root=root;
    }

    ButtonBar[] buttonBar = new ButtonBar[4];
    Button [] [] operation = new Button[3][4];
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
}
