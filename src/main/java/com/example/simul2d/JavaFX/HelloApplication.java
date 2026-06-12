package com.example.simul2d.JavaFX;

import java.io.IOException;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.List;

import com.example.simul2d.Core.SimulationLoop;
import com.example.simul2d.Core.SimulationState;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private SimulationLoop simulationLoop;
    private Thread simThread;

    @Override
    public void init() {
        // Appelée une seule fois avant l'affichage de la fenêtre.
        System.out.println("init() : l'application JavaFX démarre.");
    }

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        // First show an initialization view to collect grid size and toric option from the user.
        FXMLLoader initLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/simul2d/init-view.fxml"));
        Scene initScene = new Scene(initLoader.load(), 360, 200);
        stage.setTitle("Simul2D - Initialisation");
        stage.setScene(initScene);
        stage.show();

        // get controller and set callback to be invoked when the user validates the form
        Object initCtrl = initLoader.getController();
        if (initCtrl instanceof InitController) {
            ((InitController) initCtrl).setOnValidated((w, h, toric) -> {
                try {
                    // start simulation with chosen parameters
                    com.example.simul2d.Console.ConsoleMain.SimulationRun run = com.example.simul2d.Console.ConsoleMain.startSimulation(w, h, toric);
                    SimulationState published = run.state();

                    // load main UI
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/simul2d/hello-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                    stage.setTitle("Simul2D - JavaFX demo");
                    stage.setScene(scene);

                    List<Runnable> neededSimulationCallbacks = new ArrayList<>();
                    List<NeedsGraphValues> neededGraphCallbacks = new ArrayList<>();
                    UiState UiState = new UiState();

                    Object controller = fxmlLoader.getController();
                    if (controller instanceof NeedsSimulationState) {
                        ((NeedsSimulationState) controller).setSimulationState(published);
                        neededSimulationCallbacks.add(((NeedsSimulationState) controller)::refreshUI);
                    }
                    if (controller instanceof NeedsUiState) {
                        ((NeedsUiState) controller).setUiState(UiState);
                    }

                    java.util.Map<String, Object> namespace = fxmlLoader.getNamespace();
                    for (Object ctrl : namespace.values()) {
                        if (ctrl instanceof NeedsSimulationState) {
                            ((NeedsSimulationState) ctrl).setSimulationState(published);
                            neededSimulationCallbacks.add(((NeedsSimulationState) ctrl)::refreshUI);
                        }
                        if (ctrl instanceof NeedsUiState) {
                            ((NeedsUiState) ctrl).setUiState(UiState);
                        }
                        if (ctrl instanceof NeedsGraphValues){
                            neededGraphCallbacks.add((timeStep, populations) -> ((NeedsGraphValues) ctrl).addDataPoint(timeStep, populations));
                        }
                    }

                    // keep loop/thread for shutdown
                    this.simulationLoop = run.loop();
                    this.simulationLoop.setContentUpdateCallbacks(neededSimulationCallbacks);
                    this.simulationLoop.getUpdateSimulationSystem().setGraphicsUpdateCallbacks(neededGraphCallbacks);
                    this.simThread = run.thread();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

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

        exit(0);
    }
}
