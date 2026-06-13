package com.example.simul2d.JavaFX;

import java.util.Random;

import com.example.simul2d.Console.ConsoleMain;
import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.Systems.input.Commands.AddEntityCommand;
import com.example.simul2d.Systems.input.Commands.SetMaterialCommand;
import com.example.simul2d.Systems.input.InputHandler;
import com.example.simul2d.grid.Vec2;
import com.example.simul2d.grid.Material;
import com.example.simul2d.JavaFX.ToolsType;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RandomController implements NeedsUiState {

    private UiState uiState;
    @FXML private TextField countField;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        countField.setText("10");
        statusLabel.setText("");
    }

    @Override
    public void setUiState(UiState uiState) {
        this.uiState = uiState;
    }

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

        Alert a = new Alert(Alert.AlertType.INFORMATION, "Placed " + v + " random items.", ButtonType.OK);
        a.setHeaderText("Random");
        a.showAndWait();
        statusLabel.setText("");


    }
}
