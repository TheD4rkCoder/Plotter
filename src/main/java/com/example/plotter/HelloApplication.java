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
    final double BEGINWIDTH=1200;
    final double BEGINHEIGHT=800;
    @Override
    public void start(Stage stage) throws IOException {
        License.iConfirmNonCommercialUse("Plotter");

        Layout root = new Layout(BEGINWIDTH, BEGINHEIGHT);                           //main Group

        Scene scene = new Scene(root, BEGINWIDTH, BEGINHEIGHT);
        //stage.setFullScreen(true);
        stage.setTitle("Plotter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}