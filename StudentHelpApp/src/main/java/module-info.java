module com.example.studenthelpapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.studenthelpapp to javafx.fxml;
    exports com.example.studenthelpapp;
}
