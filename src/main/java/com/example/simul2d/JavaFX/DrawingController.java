package com.example.simul2d.JavaFX;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.grid.Material;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class DrawingController implements NeedsSimulationState {

    private SimulationState state;
    private Material selectedMaterial = Material.EMPTY;
    private boolean isDrawing = true; // true for drawing, false for erasing

    @FXML
    private VBox buttonContainer;
    
    //set methods
//get methods
//private methods
//public methods
    @FXML
    public void initialize() {
        for (Material mat : Material.values()) {
            Button btn = new Button(mat.name());
            btn.setOnAction(e -> {
                if (isDrawing) {
                    selectedMaterial = mat;
                } else {
                    selectedMaterial = Material.EMPTY;
                }
            });
            btn.setText(mat.name());
            buttonContainer.getChildren().add(btn);
        }
    }
//override methods

    /**
     * Inject the shared SimulationState so the controller can read model data.
     * Call this after FXMLLoader.load() from the application thread.
     */
    @Override
    public void setSimulationState(SimulationState state) {

        this.state = state;

    }

    @Override
    public void refreshUI() {
    }

}
