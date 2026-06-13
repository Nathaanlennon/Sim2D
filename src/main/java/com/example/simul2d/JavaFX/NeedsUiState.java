package com.example.simul2d.JavaFX;

/**
 * Interface for controllers that need the UI selection/state model injected.
 */
public interface NeedsUiState {

    /**
     * Injects the shared {@link UiState} instance into the controller.
     *
     * @param uiState the UI state model to be used by the controller
     */
    void setUiState(UiState uiState);
}
