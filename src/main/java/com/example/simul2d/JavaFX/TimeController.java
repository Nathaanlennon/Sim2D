package com.example.simul2d.JavaFX;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Systems.input.Commands.DecreaseSpeedCommand;
import com.example.simul2d.Systems.input.Commands.IncreaseSpeedCommand;
import com.example.simul2d.Systems.input.Commands.PauseCommand;
import com.example.simul2d.Systems.input.Commands.SpeedCommand;
import com.example.simul2d.Systems.input.Commands.StepCommand;
import com.example.simul2d.Systems.input.InputHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller for the time control panel of the simulation UI.
 * <p>
 * Provides buttons and displays for:
 * </p>
 * <ul>
 *   <li>Play / pause toggle</li>
 *   <li>Speed increase / decrease</li>
 *   <li>Direct speed setting (1×, 2×, 3×)</li>
 *   <li>Manual step execution with a configurable number of steps</li>
 * </ul>
 * <p>
 * This controller implements {@link NeedsSimulationState} so it can be
 * injected with the shared {@link SimulationState} and refresh its UI
 * accordingly.
 * </p>
 */
public class TimeController implements NeedsSimulationState {

    /** The shared simulation state (injected after FXML loading). */
    private SimulationState state;

    /** Label showing the current simulation time. */
    @FXML private Label currentTime;
    /** Label showing the current simulation speed. */
    @FXML private Label currentSpeed;
    /** Button that toggles between play and pause. */
    @FXML private Button playPauseButton;
    /** Text field for the number of steps to execute in a manual step. */
    @FXML private TextField countField;
    /** Label used to display validation errors for the step count. */
    @FXML private Label statusLabel;

    /**
     * Initializes the controller after the FXML file has been loaded.
     * <p>
     * Sets the default time display to "Time: 0.00".
     * </p>
     */
    @FXML
    private void initialize() {
        System.out.println("TimeController initialized");
        if (currentTime != null) {
            currentTime.setText("Time: 0.00");
        }
    }

    /**
     * Handles the play/pause button action by posting a {@link PauseCommand}
     * to the simulation input queue and updating the button text to "Play".
     * (The actual play/pause state will be reflected on the next
     * {@link #refreshUI()} call.)
     */
    @FXML
    public void handlePlayPause() {
        InputHandler.COMMAND_QUEUE.add(new PauseCommand());
        playPauseButton.setText("Play");
    }

    /**
     * Handles the "speed up" button by posting an {@link IncreaseSpeedCommand}.
     */
    @FXML
    public void handleSpeedUp() {
        InputHandler.COMMAND_QUEUE.add(new IncreaseSpeedCommand());
    }

    /**
     * Handles the "speed down" button by posting a {@link DecreaseSpeedCommand}.
     */
    @FXML
    public void handleSpeedDown() {
        InputHandler.COMMAND_QUEUE.add(new DecreaseSpeedCommand());
    }

    /**
     * Sets the simulation speed to 1×.
     */
    @FXML
    public void handleSpeed1() {
        InputHandler.COMMAND_QUEUE.add(new SpeedCommand(1));
    }

    /**
     * Sets the simulation speed to 2×.
     */
    @FXML
    public void handleSpeed2() {
        InputHandler.COMMAND_QUEUE.add(new SpeedCommand(2));
    }

    /**
     * Sets the simulation speed to 3×.
     */
    @FXML
    public void handleSpeed3() {
        InputHandler.COMMAND_QUEUE.add(new SpeedCommand(3));
    }

    /**
     * Handles the manual step button.
     * <p>
     * Reads the count from {@link #countField}, validates that it is a
     * positive integer, and then posts a {@link StepCommand} with that number
     * of steps to the simulation input queue. If the input is invalid, an
     * error message is shown in {@link #statusLabel}.
     * </p>
     */
    @FXML
    public void handleStep() {
        String txt = countField.getText().trim();
        int v;
        try {
            v = Integer.parseInt(txt);
            if (v <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            statusLabel.setText("Enter a positive integer.");
            return;
        }
        InputHandler.COMMAND_QUEUE.add(new StepCommand(v));
    }

    /**
     * Injects the shared {@link SimulationState}.
     * <p>
     * This method should be called right after {@link javafx.fxml.FXMLLoader#load()}
     * from the application thread. It stores the reference and immediately updates
     * the time label if the node is already constructed.
     * </p>
     *
     * @param state the simulation state, may be {@code null}
     */
    @Override
    public void setSimulationState(SimulationState state) {
        this.state = state;
        if (currentTime != null && state != null) {
            currentTime.setText(String.format("Time: %.2f", state.getTime()));
        }
    }

    /**
     * Refreshes all UI elements that depend on the simulation state.
     * <p>
     * Updates the time and speed labels with the current values, and sets
     * the play/pause button text accordingly. This method is typically called
     * from a periodic timer or after each simulation step.
     * </p>
     */
    @Override
    public void refreshUI() {
        if (state != null) {
            if (currentTime != null) currentTime.setText(String.format("Time: %.2f", state.getTime()));
            if (currentSpeed != null) currentSpeed.setText(String.format("Speed: %.2f", state.getSpeed()));
        }

        if (state.isPaused()) {
            playPauseButton.setText("Play");
        } else {
            playPauseButton.setText("Pause");
        }
    }
}