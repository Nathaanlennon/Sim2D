package com.example.simul2d.Core;

import com.example.simul2d.input.InputHandler;
import com.example.simul2d.render.Render;

import static java.lang.Thread.sleep;

public class SimulationLoop {
    private final SimulationState data;
    private boolean running;
    

    //constructors
    public SimulationLoop(SimulationState data) {
        this.data = data;
        this.running = true;
    }


    

//get methods

    public boolean isRunning() {
        return running;
    }

    //private methods
//public methods
    public void runSimulation(Render render, InputHandler inputHandler) throws InterruptedException {
        while (running) {
            if (!data.isPaused()) {
                data.addTime(data.getSpeed());
                render.printSimulation();
                sleep((long) (1000 / data.getSpeed()));
            }
            inputHandler.handleInput();
        }
    }
//override methods

}
