// Lien entre fichier xml et le java, donc en gros faut mettre les trucs dedans

package com.example.simul2d.JavaFX;

import com.example.simul2d.Core.SimulationState;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Main controller for the simulation window layout.
 * <p>
 * It manages the left, center, and right panes and provides a simple
 * info label showing the current simulation speed. The controller
 * receives the shared {@link SimulationState} via
 * {@link #setSimulationState(SimulationState)} and updates its UI
 * when {@link #refreshUI()} is called by the simulation loop.
 * </p>
 */
public class HelloController implements NeedsSimulationState {

    /** A welcome label (currently unused, defined in FXML). */
    @FXML private Label welcomeText;

    /** Label displaying simulation info, e.g. speed. */
    @FXML private Label infoText;

    /** The root horizontal container of the main layout. */
    @FXML private HBox root;

    /** The left pane (typically 15% of width). */
    @FXML private VBox leftPane;

    /** The center pane (scrollable, typically 50% of width). */
    @FXML private ScrollPane centerPane;

    /** The right pane (typically 35% of width). */
    @FXML private VBox rightPane;

    /** Label that can show editing mode information (defined in FXML). */
    @FXML private Label editingInfoLabel;

    /** Shared simulation state injected by the application. */
    private com.example.simul2d.Core.SimulationState simulationState;

    /**
     * Initializes the controller after FXML loading.
     * <p>
     * Binds the preferred width of the three main panes to proportions of the
     * root width (15% left, 50% center, 35% right).
     * </p>
     */
    @FXML
    public void initialize() {
        leftPane.prefWidthProperty().bind(root.widthProperty().multiply(0.15));
        centerPane.prefWidthProperty().bind(root.widthProperty().multiply(0.5));
        rightPane.prefWidthProperty().bind(root.widthProperty().multiply(0.35));
    }

    /**
     * Placeholder for a button click handler (currently empty).
     */
    @FXML
    protected void onHelloButtonClick() {
    }

    /**
     * Injects the shared {@link SimulationState} and optionally updates the
     * info label.
     *
     * @param state the simulation state to be injected
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
     * Called periodically by the simulation loop to refresh the UI.
     * <p>
     * Updates the info label with the current speed.
     * </p>
     */
    @Override
    public void refreshUI() {
        if (infoText != null && simulationState != null) {
            infoText.setText("Speed: " + simulationState.getSpeed());
        }
    }
}