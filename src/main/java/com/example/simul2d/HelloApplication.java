package com.example.simul2d;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void init() {
        // Appelée une seule fois avant l'affichage de la fenêtre.
        System.out.println("init() : l'application JavaFX démarre.");
    }

    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader lit le fichier FXML et construit l'interface graphique.
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Scene = le contenu de la fenêtre.
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Simul2D - JavaFX demo");
        stage.setScene(scene);
        // show() affiche enfin la fenêtre à l'écran.
        stage.show();
    }

    @Override
    public void stop() {
        // Appelée quand l'application se ferme.
        System.out.println("stop() : fermeture de l'application JavaFX.");
    }
}
