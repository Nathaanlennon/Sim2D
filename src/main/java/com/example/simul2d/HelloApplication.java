package com.example.simul2d;

import com.example.simul2d.Core.SimulationLoop;
import com.example.simul2d.Core.SimulationState;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private SimulationLoop simulationLoop;
    private Thread simThread;
    @Override
    public void init() {
        // Appelée une seule fois avant l'affichage de la fenêtre.
        System.out.println("init() : l'application JavaFX démarre.");
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Start the console simulation and obtain the running SimulationState and thread.
        com.example.simul2d.Console.ConsoleMain.SimulationRun run = com.example.simul2d.Console.ConsoleMain.startSimulation();
        SimulationState published = run.state();
        
        // FXMLLoader lit le fichier FXML et construit l'interface graphique.
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Scene = le contenu de la fenêtre. Load FXML and inject the shared state into controllers.
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Simul2D - JavaFX demo");
        stage.setScene(scene);
        // show() affiche enfin la fenêtre à l'écran.
        stage.show();

        // After loading, inject the published SimulationState into controllers
        Object controller = fxmlLoader.getController();
        if (controller instanceof NeedsSimulationState) {
            ((NeedsSimulationState) controller).setSimulationState(published);
        }

        // keep loop/thread for shutdown
        this.simulationLoop = run.loop();
        this.simThread = run.thread();
    }

    @Override
    public void stop() {
        // Appelée quand l'application se ferme.
        System.out.println("stop() : fermeture de l'application JavaFX.");
        if (simulationLoop != null) {
            simulationLoop.stop();
        }
        if (simThread != null && simThread.isAlive()) {
            simThread.interrupt();
            try {
                simThread.join(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
