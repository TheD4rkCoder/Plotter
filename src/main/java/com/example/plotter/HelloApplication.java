package com.example.plotter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HelloApplication extends Application {
    ArrayList<Point> points = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        Group plotArea = new Group();


        Group group = new Group(plotArea);
        Scene scene = new Scene(group, 320, 240);
        stage.setTitle("Plotter");
        stage.setScene(scene);
        stage.show();
    }

    public void drawFunction() {
        Point maxPoint = Collections.max(points, new PointsComp());
        Point minPoint = Collections.min(points, new PointsComp());
        double minToMaxDifference = maxPoint.y - minPoint.y;

        for (int i = 0; i < points.size() - 1; i++) {

        }
    }

    public static void main(String[] args) {
        launch();
    }
}