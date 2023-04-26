package com.example.plotter;

import javafx.scene.shape.Line;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;

public class PlotFunction {
    private Function function;
    private boolean isFunctionVisible = true;
    private ArrayList<Line> lines = new ArrayList<>();

    public void recalculateLinesPosition() {
        if (!isFunctionVisible) {
            return;
        }
    }

    public Function getFunction() {
        return function;
    }

    public boolean isFunctionVisible() {
        return isFunctionVisible;
    }

    public void changeFunctionVisibility() {
        isFunctionVisible = !isFunctionVisible;
        for (Line l : lines) {
            l.setVisible(isFunctionVisible);
        }
    }

    public PlotFunction(Function function) {
        this.function = function;
    }
}
