// Lien entre fichier xml et le java, donc en gros faut mettre les trucs dedans

package com.example.simul2d;


import javafx.fxml.FXML;
import com.example.simul2d.Core.SimulationState;
import javafx.scene.control.Label;

public class HelloController implements NeedsSimulationState{
    @FXML
    private Label welcomeText;

    @FXML
    private Label infoText;

    // On crée une variable pour stocker notre personnage
    // Elle est utilisable partout dans ce contrôleur
    private Personnage monPersonnage;

    private int clickCount = 0;
    // Shared simulation state injected by the application
    private com.example.simul2d.Core.SimulationState simulationState;

    // reference to the included controller (time-control-view.fxml)
    @FXML
    private TimeController timeController;

    @FXML
    private void initialize() {
        // Cette méthode est appelée automatiquement après le chargement du FXML.
        
        // On crée un nouveau personnage appelé "Héros"
        monPersonnage = new Personnage("Héros");
        
       
    }

    @FXML
    protected void onHelloButtonClick() {
    }

    /**
     * Inject the simulation state for this controller. Call after FXMLLoader.load().
     */
    public void setSimulationState(SimulationState state) {
        this.simulationState = state;
        // optional: update UI with initial info from state
        if (infoText != null && state != null) {
            infoText.setText("Speed: " + state.getSpeed());
        }
        // forward to included controller if available
        if (timeController != null) {
            timeController.setSimulationState(state);
        }
    }
}
