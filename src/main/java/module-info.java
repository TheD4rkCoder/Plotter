/**
 * The com.example.plotter module contains classes for a plotter application.
 */
module com.example.plotter {
    requires javafx.controls;
    requires javafx.fxml;
    requires MathParser.org.mXparser;

    // Opens the com.example.plotter package to javafx.fxml for JavaFX FXMLLoader to access the controllers.
    opens com.example.plotter to javafx.fxml;

    // Exports the com.example.plotter package to make it accessible to other modules.
    exports com.example.plotter;
}
