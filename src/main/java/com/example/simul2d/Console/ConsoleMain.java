package com.example.simul2d.Console;


import com.example.simul2d.Core.*;
import com.example.simul2d.grid.Grid;
import com.example.simul2d.Entities.Mold.AxialMold1;
import com.example.simul2d.Entities.Mold.CircMold1;
import com.example.simul2d.Entities.Mold.DividedMold1;
import com.example.simul2d.grid.*;
import com.example.simul2d.input.InputHandler;
import com.example.simul2d.input.InputReader;
import com.example.simul2d.render.Render;

/**
 * Console-based entry point for running the simulation loop.
 */
public class ConsoleMain {

    // Published simulation state so other parts of the app can access the running
    // simulation when ConsoleMain is started programmatically.
    public static volatile com.example.simul2d.Core.SimulationState publishedState;

    public static com.example.simul2d.Core.SimulationState getPublishedState() {
        return publishedState;
    }

    /**
     * Small holder describing a running simulation: the thread, the state and the loop.
     */
    public static record SimulationRun(Thread thread, SimulationState state, SimulationLoop loop) {}

    /**
     * Start the simulation and return a SimulationRun containing the created
     * SimulationState, the SimulationLoop and the thread running the loop.
     * The SimulationState is published in {@link #publishedState} as well.
     */
    public static SimulationRun startSimulation() {
        //TODO: make that main calls that somehow or make another function to start sumulation so no code duplication
        // create the model
        SimulationState state = new SimulationState();
        publishedState = state;
        
        // create loop and helpers
        SimulationLoop loop = new SimulationLoop(state);
        Render renderer = new Render(state);
        InputHandler inputHandler = new InputHandler(state);

        // sample test setup (same as main)
        Grid grid = state.getGrid();

        // start the console input reader
        new Thread(new InputReader(), "InputReader-Thread").start();

        // start the simulation loop in its own thread
        Thread t = new Thread(() -> {
            try {
                loop.runSimulation(renderer, inputHandler);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "ConsoleMain-Thread");
        t.start();

        // for test purposes for now
        grid.getCell(0,0).addEntity(new CircMold1());
        grid.getCell(0,0).setMaterial(Material.WOOD);
  
        grid.getCell(grid.getWidth()-1, grid.getHeight()-1).addEntity(new DividedMold1());
        grid.getCell(0, grid.getHeight()-1).addEntity(new AxialMold1());

        return new SimulationRun(t, state, loop);
    }

    /**
     * Starts the simulation and the background input reader.
     *
     * @param args command-line arguments, unused by this launcher
     * @throws InterruptedException if the simulation loop is interrupted while sleeping
     */
    public static void main(String[] args) throws InterruptedException {

            startSimulation();



    }

}
