// Lien entre fichier xml et le java, donc en gros faut mettre les trucs dedans

package com.example.simul2d.JavaFX;


import javafx.fxml.FXML;
import com.example.simul2d.Core.SimulationState;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HelloController implements NeedsSimulationState{
    @FXML
    private Label welcomeText;

    @FXML
    private Label infoText;

    @FXML private HBox root;

    @FXML private VBox leftPane;
    @FXML private ScrollPane centerPane;
    @FXML private VBox rightPane;
    @FXML private Label editingInfoLabel;
    // Shared simulation state injected by the application
    private com.example.simul2d.Core.SimulationState simulationState;


    @FXML
    public void initialize() {
        leftPane.prefWidthProperty().bind(root.widthProperty().multiply(0.15));
        centerPane.prefWidthProperty().bind(root.widthProperty().multiply(0.5));
        rightPane.prefWidthProperty().bind(root.widthProperty().multiply(0.35));
    }

    @FXML
    protected void onHelloButtonClick() {
    }

    /**
     * 
     * @param state the SimulationState to be injected
     */
    @Override
    public void setSimulationState(SimulationState state) {
        this.simulationState = state;
        // optional: update UI with initial info from state
        if (infoText != null && state != null) {
            infoText.setText("Speed: " + state.getSpeed());
        }
    }

    /**
     * This method will be called by the simmulation loop as callback to update the UI with the latest state.
     */
    @Override
    public void refreshUI() {
        
        if (infoText != null && simulationState != null) {
            infoText.setText("Speed: " + simulationState.getSpeed());
        }
    }
    
    
}
