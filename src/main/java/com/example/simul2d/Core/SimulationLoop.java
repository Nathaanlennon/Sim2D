package com.example.simul2d.Core;

import com.example.simul2d.Systems.UpdateSimulationSystem;
import com.example.simul2d.Systems.input.InputHandler;
import com.example.simul2d.Systems.ConsoleRenderSystem;
import javafx.application.Platform;

import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Runs the update-render-input loop for the simulation.
 */
public class SimulationLoop {
    public static SimulationLoop self;
    private final SimulationState data;
    private final UpdateSimulationSystem updateSimulationSystem;
    private volatile boolean running;
    private volatile List<Runnable> contentUpdateCallbacks;
    private ConsoleRenderSystem consoleRenderSystem = null;
    private InputHandler inputHandler = null;


    //constructors
    public SimulationLoop(SimulationState data) {
        this.data = data;
        this.running = true;
        this.updateSimulationSystem = new UpdateSimulationSystem(data);
        self = this;
    }

    /**
     * Request that the simulation loop stop at the next convenient point.
     */
    public void stop() {
        this.running = false;
    }


    //set methods
    public void setContentUpdateCallbacks(List<Runnable> callbacks) {
        this.contentUpdateCallbacks = callbacks;
    }

    //get methods
    public UpdateSimulationSystem getUpdateSimulationSystem() {
        return updateSimulationSystem;
    }

    /**
     * Returns whether the loop is still running.
     *
     * @return {@code true} while the loop remains active
     */
    public boolean isRunning() {
        return running;
    }

    //private methods


    //public methods
    public void update() {
        data.getLock().writeLock().lock();
        try {
            updateSimulationSystem.update();
        } finally {
            data.getLock().writeLock().unlock();
        }
        // update an atomic snapshot of the grid so the UI can read a stable
        // pre-rendered string representation without locking.
//                data.updateGridSnapshot();

    }

    public void render() {
        data.getLock().readLock().lock();
        try {
            consoleRenderSystem.printSimulation();
            if (contentUpdateCallbacks != null) {

                for (Runnable callback : contentUpdateCallbacks) {
                    Platform.runLater(callback);
                }
            }
        } finally {
            data.getLock().readLock().unlock();
        }
    }

    /**
     * Executes the main simulation loop until {@code running} becomes false.
     *
     * @param render       the renderer used to display the simulation state
     * @param inputHandler the input handler used to consume queued commands
     * @throws InterruptedException if the loop sleep is interrupted
     */
    public void runSimulation(ConsoleRenderSystem render, InputHandler inputHandler) throws InterruptedException {
        if (this.consoleRenderSystem == null) this.consoleRenderSystem = render;
        if (this.inputHandler == null) this.inputHandler = inputHandler;
        while (running) {
            inputHandler.handleInput();
            if (!data.isPaused()) {
                update();
                // render part
                render();


                sleep((long) (1000 / data.getSpeed()));
            }
        }
    }
//override methods

}
