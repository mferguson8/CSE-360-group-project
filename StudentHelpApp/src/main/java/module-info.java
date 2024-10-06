module com.example.studenthelpapp {
    requires javafx.controls;
    requires javafx.fxml;
<<<<<<< HEAD
<<<<<<< HEAD
    requires java.sql;

=======
    
>>>>>>> 081d4a2 (fixed maven / java issues)
=======

>>>>>>> 18cf93c (my changes)
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.studenthelpapp to javafx.fxml;
    exports com.example.studenthelpapp;
}