package com.example.simul2d.Core;

import com.example.simul2d.Systems.UpdateSimulation;
import com.example.simul2d.input.InputHandler;
import com.example.simul2d.render.Render;

import static java.lang.Thread.sleep;

/**
 * Runs the update-render-input loop for the simulation.
 */
public class SimulationLoop {
    private final SimulationState data;
    private UpdateSimulation updateSimulation;
    private volatile boolean running;
    

    //constructors
    public SimulationLoop(SimulationState data) {
        this.data = data;
        this.running = true;
        this.updateSimulation = new UpdateSimulation(data);
    }

    /**
     * Request that the simulation loop stop at the next convenient point.
     */
    public void stop() {
        this.running = false;
    }


    

//get methods

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
    /**
     * Executes the main simulation loop until {@code running} becomes false.
     *
     * @param render the renderer used to display the simulation state
     * @param inputHandler the input handler used to consume queued commands
     * @throws InterruptedException if the loop sleep is interrupted
     */
    public void runSimulation(Render render, InputHandler inputHandler) throws InterruptedException {
        while (running) {
            inputHandler.handleInput();
            if (!data.isPaused()) {

                
                updateSimulation.update();

                // update an atomic snapshot of the grid so the UI can read a stable
                // pre-rendered string representation without locking.
                data.updateGridSnapshot();

                render.printSimulation();
                sleep((long) (1000 / data.getSpeed()));
            }
        }
    }
//override methods

}
