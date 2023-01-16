module com.example.plotter {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.plotter to javafx.fxml;
    exports com.example.plotter;
}