module com.example.studenthelpapp {
    requires javafx.controls;
    requires javafx.fxml;
<<<<<<< HEAD
    requires java.sql;

=======
    
>>>>>>> 081d4a2 (fixed maven / java issues)
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.studenthelpapp to javafx.fxml;
    exports com.example.studenthelpapp;
}