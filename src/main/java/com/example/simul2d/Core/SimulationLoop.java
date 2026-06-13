package com.example.simul2d.Core;

import static java.lang.Thread.sleep;
import java.util.List;

import com.example.simul2d.Systems.ConsoleRenderSystem;
import com.example.simul2d.Systems.UpdateSimulationSystem;
import com.example.simul2d.Systems.input.InputHandler;

import javafx.application.Platform;

/**
 * Controls and runs the main simulation loop.
 *
 * <p>This class coordinates the update, render and input subsystems for a
 * {@link SimulationState}. It owns an {@link UpdateSimulationSystem} which
 * advances the simulation logic and uses a {@link ConsoleRenderSystem} and
 * {@link com.example.simul2d.Systems.input.InputHandler} to render state and
 * process input respectively.
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
     * Requests that the simulation loop stop at the next convenient point.
     * Calling this method will cause {@link #runSimulation(ConsoleRenderSystem, com.example.simul2d.Systems.input.InputHandler)}
     * to exit its main loop and return once the current iteration completes.
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
     * Executes the simulation loop until {@link #stop()} is called.
     *
     * <p>Each loop iteration first processes any queued input via
     * {@code inputHandler.handleInput()}, then advances the simulation state
     * with {@link #update()} (unless the state is paused) and finally invokes
     * the provided renderer. The loop sleeps between iterations according to
     * the simulation speed value from {@link SimulationState}.
     *
     * @param render       the renderer used to display the simulation state
     * @param inputHandler the input handler used to consume queued commands
     * @throws InterruptedException if the thread sleep is interrupted
     */
    public void runSimulation(ConsoleRenderSystem render, InputHandler inputHandler) throws InterruptedException {
        if (this.consoleRenderSystem == null) this.consoleRenderSystem = render;
        if (this.inputHandler == null) this.inputHandler = inputHandler;
        while (running) {
            inputHandler.handleInput();
            if (!data.isPaused()) {
                update();
                render();
                sleep((long) (1000 / data.getSpeed()));
            }
        }
    }
//override methods

}
