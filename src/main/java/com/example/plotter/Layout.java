package com.example.plotter;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Objects;

public class Layout {
    Group root;
    Scene mainScene;
    public Layout(Group root) {
        this.root=root;
    }

    ButtonBar[] buttonBar = new ButtonBar[4];
    Button [] [] operation = new Button[3][4];

    ArrayList <Label> funktionField = new ArrayList<>();
    Button [] removeConstantField = new Button[5];
    ScrollPane scrollConst = new ScrollPane();
    ScrollPane scrollFunkt = new ScrollPane();
    Button addFunk = new Button("+");
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

    private void setConstantArea(Group constantArea){



    }
    public void addConstantToScrollPane(){
    }
    private void setFunktionArea(Group group){
        funktionField.add(new Label());
        for (Label funktion:funktionField) {
            scrollFunkt.setContent(funktion);
        }



    }
    private void setPosition(Group buttonArea,Group plotArea,Group funktionArea,Group constantArea){

        buttonArea.setLayoutX(mainScene.getHeight());
        buttonArea.setLayoutY(mainScene.getHeight()*0.75);

        constantArea.setLayoutX(mainScene.getHeight());
        constantArea.setLayoutY(mainScene.getHeight()*0.5);

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
        buttonArea.setLayoutX(mainScene.getHeight());
        buttonArea.setLayoutY(mainScene.getHeight()*0.75);

        //Positioning of the ConstantArea
        //setConstantArea(constantArea);
        constantArea.setLayoutX(mainScene.getHeight());
        constantArea.setLayoutY(mainScene.getHeight()*0.5);

        //Positioning of the funktionArea
        funktionArea.setLayoutX(mainScene.getHeight());

        funktionArea.getChildren().add(scrollFunkt);
        setFunktionArea(funktionArea);
    }
}
