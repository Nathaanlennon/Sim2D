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
    private boolean running;
    

    //constructors
    public SimulationLoop(SimulationState data) {
        this.data = data;
        this.running = true;
        this.updateSimulation = new UpdateSimulation(data);
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
            if (!data.isPaused()) {

                
                updateSimulation.update();



                render.printSimulation();
                sleep((long) (1000 / data.getSpeed()));
            }
            inputHandler.handleInput();
        }
    }
//override methods

}
