package com.example.plotter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mariuszgromada.math.mxparser.*;

import java.io.IOException;

public class Plotter extends Application {
    final double BEGINWIDTH = 1200;
    final double BEGINHEIGHT = 800;

    @Override
    public void start(Stage stage) throws IOException {
        License.iConfirmNonCommercialUse("Plotter");

        Layout root = new Layout(BEGINWIDTH, BEGINHEIGHT);                           //main Group

        Scene scene = new Scene(root, BEGINWIDTH, BEGINHEIGHT);
        //stage.setFullScreen(true);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.setTitle("Plotter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}