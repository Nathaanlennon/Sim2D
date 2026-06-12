module com.example.simul2d {
    // JavaFX Controls = boutons, labels, etc.
    requires javafx.controls;
    // JavaFX FXML = chargement de l'interface décrite en XML.
    requires javafx.fxml;
    requires java.desktop;


    // `opens` autorise JavaFX FXML à accéder aux champs/méthodes annotés @FXML.
    opens com.example.simul2d to javafx.fxml;
    // `exports` rend le package visible aux autres modules.
    //exports com.example.simul2d;
    exports com.example.simul2d.JavaFX;
    opens com.example.simul2d.JavaFX to javafx.fxml;
}