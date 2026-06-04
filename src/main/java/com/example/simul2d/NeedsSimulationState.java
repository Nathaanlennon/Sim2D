package com.example.simul2d;

import com.example.simul2d.Core.SimulationState;

/**
 * Interface for controllers that need access to the shared SimulationState.
 * Implementing classes should provide a method to receive the SimulationState reference.
 */
public interface NeedsSimulationState {


    /**
     * Inject the shared SimulationState so the controller can read model data.
     * @param state the SimulationState to be injected
     */
    void setSimulationState(SimulationState state);

    /**
     * Optional method to refresh the UI based on the current state.
     */
    void refreshUI();
    
}
