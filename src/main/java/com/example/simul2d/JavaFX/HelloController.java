// Lien entre fichier xml et le java, donc en gros faut mettre les trucs dedans

package com.example.simul2d.JavaFX;


import javafx.fxml.FXML;
import com.example.simul2d.Core.SimulationState;
import javafx.scene.control.Label;

public class HelloController implements NeedsSimulationState{
    @FXML
    private Label welcomeText;

    @FXML
    private Label infoText;

    
    // Shared simulation state injected by the application
    private com.example.simul2d.Core.SimulationState simulationState;


    @FXML
    private void initialize() {
        // Cette méthode est appelée automatiquement après le chargement du FXML.
        
       
       
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
