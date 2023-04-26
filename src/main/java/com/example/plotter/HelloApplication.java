package com.example.plotter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.mariuszgromada.math.mxparser.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HelloApplication extends Application {
    final int BEGINWIDTH=300;
    @Override
    public void start(Stage stage) throws IOException {
        License.iConfirmNonCommercialUse("Plotter");

        Group root = new Group();                           //main Group
        PlotArea plotArea = new PlotArea(BEGINWIDTH);
        Group funktionArea = new Group();
        Group constantArea = new Group();
        Group buttonArea = new Group();
        root.getChildren().add(plotArea);
        root.getChildren().add(funktionArea);
        root.getChildren().add(constantArea);
        root.getChildren().add(buttonArea);
        Layout layout = new Layout(root);
        Scene scene = new Scene(root, 320, 240);
        stage.setFullScreen(true);
        stage.setTitle("Plotter");
        stage.setScene(scene);
        layout.setEveryGroupLayout(buttonArea,plotArea,funktionArea,constantArea);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}