package com.example.simul2d.JavaFX;

import java.util.Random;

import com.example.simul2d.Console.ConsoleMain;
import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.Systems.input.Commands.AddEntityCommand;
import com.example.simul2d.Systems.input.Commands.SetMaterialCommand;
import com.example.simul2d.Systems.input.InputHandler;
import com.example.simul2d.grid.Material;
import com.example.simul2d.grid.Vec2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller for the "Random" pane that places a user-specified number of
 * entities or materials at random positions on the grid.
 * <p>
 * This controller requires a {@link UiState} to know the current editing
 * mode (entity or material) and the selected type.
 * </p>
 */
public class RandomController implements NeedsUiState {

    /** The shared UI state, injected via {@link #setUiState(UiState)}. */
    private UiState uiState;

    /** Text field for the number of items to place. */
    @FXML private TextField countField;

    /** Label used to display error or status messages. */
    @FXML private Label statusLabel;

    /**
     * Initializes the controller after the FXML has been loaded.
     * <p>
     * Sets the default count to "10" and clears the status label.
     * </p>
     */
    @FXML
    public void initialize() {
        countField.setText("10");
        statusLabel.setText("");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUiState(UiState uiState) {
        this.uiState = uiState;
    }

    /**
     * Called when the "Random" button is clicked.
     * <p>
     * Reads the count from {@link #countField}, validates that it is a
     * positive integer, then retrieves the current simulation state.
     * According to the active mode (entity or material) and the selected
     * type, it posts the corresponding commands to the simulation queue.
     * Finally, an information alert is shown.
     * </p>
     */
    @FXML
    private void onRandomClicked() {
        String txt = countField.getText().trim();
        int v;
        try {
            v = Integer.parseInt(txt);
            if (v <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            statusLabel.setText("Enter a positive integer.");
            return;
        }

        SimulationState state = ConsoleMain.getPublishedState();
        if (state == null || state.getGrid() == null) {
            statusLabel.setText("Simulation not started.");
            return;
        }

        Random rnd = new Random();
        int width = state.getGrid().getWidth();
        int height = state.getGrid().getHeight();

        ToolsType mode = uiState == null ? null : uiState.getMode();
        if (mode == ToolsType.ENTITY_MODE) {
            Entities entityType = uiState.getSelectedEntity();
            if (entityType == null) {
                statusLabel.setText("Select an entity type first.");
                return;
            }
            for (int i = 0; i < v; i++) {
                int x = rnd.nextInt(width);
                int y = rnd.nextInt(height);
                InputHandler.COMMAND_QUEUE.add(new AddEntityCommand(new Vec2(x, y), entityType));
            }
        } else if (mode == ToolsType.MATERIAL_MODE) {
            Material mat = uiState == null ? null : uiState.getSelectedMaterial();
            if (mat == null) {
                statusLabel.setText("Select a material first.");
                return;
            }
            for (int i = 0; i < v; i++) {
                int x = rnd.nextInt(width);
                int y = rnd.nextInt(height);
                InputHandler.COMMAND_QUEUE.add(new SetMaterialCommand(new Vec2(x, y), mat));
            }
        } else {
            statusLabel.setText("Unknown mode : select Entity or Material.");
            return;
        }

        Alert a = new Alert(Alert.AlertType.INFORMATION,
                "Placed " + v + " random items.", ButtonType.OK);
        a.setHeaderText("Random");
        a.showAndWait();
        statusLabel.setText("");
    }
}