package com.example.plotter;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import org.mariuszgromada.math.mxparser.*;

import java.util.ArrayList;


public class PlotArea extends Group{
    private double width;
    private double height;
    ArrayList<Boolean> isFuncitonVisible = new ArrayList<>();
    ArrayList<Function> functions = new ArrayList<>();
    ArrayList<Argument> variables = new ArrayList<>();
    public PlotArea(int beginWidth) {
        super();
        this.height = beginWidth;
        this.width = beginWidth;
        this.getChildren().add(new Rectangle(beginWidth, beginWidth));
    }

    public void addFunction(Function function){
        for (Argument a : variables) {
            function.addArguments(a);
        }
        functions.add(function);
        isFuncitonVisible.add(true);
    }
    public void addVariable(Argument argument) {

    }
    public void removeVariable(Argument argument) {
        removeVariable(argument.getArgumentExpressionString());
    }
    public void removeVariable(String argument) {
        for (Argument a : variables) {
            if (a.getArgumentExpressionString().equals(argument)) {
                variables.remove(a);
            }
        }
    }
    public void removeFunction(Function function) {
        removeFunction(function.getFunctionExpressionString());
    }
    public void removeFunction(String function) {
        for (int i = 0; i < functions.size(); i++) {
            if (functions.get(i).getFunctionExpressionString().equals(function)) {
                functions.remove(i);
            }
        }
    }
    public void drawInGroup(Group group){

    }
}
