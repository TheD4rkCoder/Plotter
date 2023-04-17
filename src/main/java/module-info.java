module com.example.plotter {
    requires javafx.controls;
    requires javafx.fxml;
    requires MathParser.org.mXparser;


    opens com.example.plotter to javafx.fxml;
    exports com.example.plotter;
}