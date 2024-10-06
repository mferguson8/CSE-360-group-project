module com.example.studenthelpapp {
    requires javafx.controls;
    requires javafx.fxml;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    requires java.sql;
<<<<<<< HEAD

=======
    
>>>>>>> 081d4a2 (fixed maven / java issues)
=======

>>>>>>> 18cf93c (my changes)
=======
>>>>>>> f1d25a6 (Added hashString function, which required 2 new imports)
=======
    requires java.sql;
>>>>>>> main
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.studenthelpapp to javafx.fxml;
    exports com.example.studenthelpapp;
}