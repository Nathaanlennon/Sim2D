package com.example.simul2d.Core;

import com.example.simul2d.Systems.UpdateSimulation;
import com.example.simul2d.input.InputHandler;
import com.example.simul2d.render.Render;

import static java.lang.Thread.sleep;

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

    public boolean isRunning() {
        return running;
    }

    //private methods
//public methods
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
