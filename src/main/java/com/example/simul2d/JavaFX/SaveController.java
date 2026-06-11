package com.example.simul2d.JavaFX;

import java.io.File;
import java.io.IOException;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Systems.ConsoleRenderSystem;
import com.example.simul2d.Systems.SaveSystem;
import com.example.simul2d.grid.Grid;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class SaveController implements NeedsSimulationState {

    private SimulationState simulationState;

    @FXML
    private HBox root;

    @FXML
    public void handleSave() {
        if (simulationState == null || simulationState.getGrid() == null) {
            showAlert(AlertType.ERROR, "Save error", "No simulation grid available to save.");
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save simulation");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sim2D files", "*.sim"));
        File file = chooser.showSaveDialog(root == null || root.getScene() == null ? null : root.getScene().getWindow());
        if (file == null) return;

        try {
            SaveSystem.saveSystem(simulationState.getGrid(), file.getAbsolutePath());
            ConsoleRenderSystem.printSomething("Saved simulation to: " + file.getAbsolutePath());
            showAlert(AlertType.INFORMATION, "Saved", "Saved simulation to " + file.getName());
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Save failed", e.getMessage());
        }
    }

    @FXML
    public void handleLoad() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Load simulation");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sim2D files", "*.sim"));
        File file = chooser.showOpenDialog(root == null || root.getScene() == null ? null : root.getScene().getWindow());
        if (file == null) return;

        try {
            Grid loaded = SaveSystem.loadSystem(file.getAbsolutePath());
            if (simulationState != null) {
                simulationState.setGrid(loaded);
                simulationState.updateGridSnapshot();
            }
            ConsoleRenderSystem.printSomething("Loaded simulation from: " + file.getAbsolutePath());
            showAlert(AlertType.INFORMATION, "Loaded", "Loaded simulation from " + file.getName());
        } catch (IOException | ClassNotFoundException e) {
            showAlert(AlertType.ERROR, "Load failed", e.getMessage());
        }
    }

    private void showAlert(AlertType type, String title, String text) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(text);
        a.showAndWait();
    }

    @Override
    public void setSimulationState(SimulationState state) {
        this.simulationState = state;
    }

    @Override
    public void refreshUI() {
        // no-op for now
    }

}
