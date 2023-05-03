/**
 * Plotter is a JavaFX application for graphically representing mathematical functions.
 * It uses the mxparser library to parse and evaluate the functions.
 *
 * @author Plotter
 * @version 1.0
 */
package com.example.plotter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mariuszgromada.math.mxparser.*;

import java.io.IOException;
import java.util.Objects;

/**
 * The Plotter class extends the JavaFX Application class and is responsible for
 * creating and displaying the main application window.
 */
public class Plotter extends Application {
    final double BEGINWIDTH = 1200;  // The initial width of the window
    final double BEGINHEIGHT = 800;  // The initial height of the window

    /**
     * The main method is the entry point for the JavaFX application.
     * It launches the application, calling the start method.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * The start method is called when the JavaFX application is launched.
     * It initializes the main application window and scene.
     *
     * @param stage The primary stage for this application.
     * @throws IOException If there is an error loading the CSS stylesheet.
     */
    @Override
    public void start(Stage stage) throws IOException {
        License.iConfirmNonCommercialUse("Plotter");

        Layout root = new Layout(BEGINWIDTH, BEGINHEIGHT);  // main Group

        Scene scene = new Scene(root, BEGINWIDTH, BEGINHEIGHT);
        // stage.setFullScreen(true);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.setTitle("Plotter");
        stage.setScene(scene);
        stage.show();
    }
}
