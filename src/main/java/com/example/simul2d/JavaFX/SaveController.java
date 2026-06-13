package com.example.simul2d.JavaFX;

import java.io.File;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Systems.input.Commands.LoadCommand;
import com.example.simul2d.Systems.input.Commands.SaveCommand;
import com.example.simul2d.Systems.input.InputHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

/**
 * Controller providing save/load UI actions for the simulation.
 *
 * <p>Save/Load operations are forwarded to the input command queue so the
 * simulation thread can perform file IO in a controlled manner.
 */
public class SaveController implements NeedsSimulationState {

    private SimulationState simulationState;

    @FXML
    private HBox root;

    /**
     *
     * <p>If the directory does not exist, it is created automatically.
     *
     * @return the "saves" directory as a {@link File} instance
     */
    private File getSavesDirectory() {
        File dir = new File("saves");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    /**
     * Opens a file chooser and queues a save command for the current simulation state.
     *
     * <p>The simulation is temporarily paused to ensure a consistent state during saving.
     * The selected file path is sent to the input command queue for processing by the simulation thread.
     */
    @FXML
    public void handleSave() {

        if (simulationState == null || simulationState.getGrid() == null) {
            showAlert(AlertType.ERROR, "Save error", "No simulation grid available to save.");
            return;
        }

        boolean wasPaused = simulationState.isPaused();
        if (!wasPaused) {
            simulationState.changePause();
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save simulation");
        chooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Sim2D files", "*.sim"));

        chooser.setInitialDirectory(getSavesDirectory());

        File file = chooser.showSaveDialog(
                root == null || root.getScene() == null ? null : root.getScene().getWindow()
        );

        if (file == null) return;

        InputHandler.COMMAND_QUEUE.add(new SaveCommand(file.getAbsolutePath()));
    }

    /**
     * Opens a file chooser and queues a load command for a simulation file.
     *
     * <p>If the simulation is running, it is paused before loading to avoid concurrent modification.
     * The selected file is forwarded to the input command queue for execution.
     */
    @FXML
    public void handleLoad() {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Load simulation");
        chooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Sim2D files", "*.sim"));

        File dir = getSavesDirectory();
        if (dir.exists()) {
            chooser.setInitialDirectory(dir);
        }

        File file = chooser.showOpenDialog(
                root == null || root.getScene() == null ? null : root.getScene().getWindow()
        );

        if (file == null) return;

        boolean wasPaused = simulationState == null || simulationState.isPaused();
        if (simulationState != null && !wasPaused) {
            simulationState.changePause();
        }

        InputHandler.COMMAND_QUEUE.add(new LoadCommand(file.getAbsolutePath()));
    }

    /**
     * Displays a JavaFX alert dialog with the specified type, title, and message.
     *
     * @param type  the alert type (information, error, warning, etc.)
     * @param title the window title of the alert
     * @param text  the content message displayed in the dialog
     */
    private void showAlert(AlertType type, String title, String text) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(text);
        a.showAndWait();
    }

    /**
     * Injects the current simulation state into the controller.
     *
     * <p>This method is called by the application during initialization.
     *
     * @param state the active simulation state
     */
    @Override
    public void setSimulationState(SimulationState state) {
        this.simulationState = state;
    }

    /**
     * Refreshes the UI from the simulation state.
     *
     * <p>Currently unused.
     */
    @Override
    public void refreshUI() {
        // no-op
    }
}