package com.example.simul2d;


import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.input.CommandType;
import com.example.simul2d.input.InputHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class TimeController implements NeedsSimulationState {

    // Reference to the shared simulation state (injected by HelloApplication)
    private SimulationState state;

    @FXML
    private Label currentTime;
    @FXML
    private Label currentSpeed;


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
    @Override
    public void refreshUI() {
        if (state != null) {
            if (currentTime != null) currentTime.setText(String.format("Time: %.2f", state.getTime()));
            if (currentSpeed != null) currentSpeed.setText(String.format("Speed: %.2f", state.getSpeed()));
        }
    }

    @FXML
    public void handlePlayPause() {
        InputHandler.COMMAND_TYPE_QUEUE.add(CommandType.PAUSE);
    }

    @FXML
    public void handleSpeedUp() {
        InputHandler.COMMAND_TYPE_QUEUE.add(CommandType.INCREASE_SPEED);
    }

    @FXML
    public void handleSpeedDown() {
        InputHandler.COMMAND_TYPE_QUEUE.add(CommandType.DECREASE_SPEED);
    }

    @FXML
    public void handleSpeed1() {
        InputHandler.COMMAND_TYPE_QUEUE.add(CommandType.SPEED1);
    }

    @FXML
    public void handleSpeed2() {
        InputHandler.COMMAND_TYPE_QUEUE.add(CommandType.SPEED2);
    }

    @FXML
    public void handleSpeed3() {
        InputHandler.COMMAND_TYPE_QUEUE.add(CommandType.SPEED3);
    }
}
