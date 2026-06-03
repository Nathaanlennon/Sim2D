package com.example.simul2d;


import com.example.simul2d.Core.SimulationState;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TimeController implements NeedsSimulationState {
    
    
    @FXML
    private Label currentTime;
    // Reference to the shared simulation state (injected by HelloApplication)
    private SimulationState state;
    

    //constructors
    

    //set methods
//get methods
//private methods
    @FXML
    private void initialize() {
        System.out.println("TimeController initialized");
        if (currentTime != null) {
            currentTime.setText("Time: 0.00");
        }
        
    }

    /**
     * Inject the shared SimulationState so the controller can read model data.
     * Call this after FXMLLoader.load() from the application thread.
     */
    public void setSimulationState(SimulationState state) {
        this.state = state;
        if (currentTime != null && state != null) {
            currentTime.setText(String.format("Time: %.2f", state.getTime()));
        }
    }
//public methods

//override methods

}
