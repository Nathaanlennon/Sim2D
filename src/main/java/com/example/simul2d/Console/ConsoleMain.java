package com.example.simul2d.Console;


import com.example.simul2d.Core.SimulationLoop;
import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Entities.Mold.AxialMold1;
import com.example.simul2d.Entities.Mold.CircMold1;
import com.example.simul2d.Entities.Mold.DividedMold1;
import com.example.simul2d.Systems.ConsoleRenderSystem;
import com.example.simul2d.Systems.input.InputHandler;
import com.example.simul2d.Systems.input.InputReader;
import com.example.simul2d.grid.Grid;
import com.example.simul2d.grid.Material;

/**
 * Console-based entry point for running the simulation loop.
 */
public class ConsoleMain {

    /**
     * Published simulation state so other parts of the app can access the running
     * simulation when ConsoleMain is started programmatically.
     */
    public static volatile com.example.simul2d.Core.SimulationState publishedState;

    public static com.example.simul2d.Core.SimulationState getPublishedState() {
        return publishedState;
    }

    /**
     * Small holder describing a running simulation: the thread, the state and the loop.
     *
     * @param thread the thread executing the simulation loop
     * @param state  the current simulation state
     * @param loop   the simulation loop controller
     */
    public static record SimulationRun(Thread thread, SimulationState state, SimulationLoop loop) {
    }

    /**
     * Start the simulation and return a SimulationRun containing the created
     * SimulationState, the SimulationLoop and the thread running the loop.
     * The SimulationState is published in {@link #publishedState} as well.
     *
     * @return a SimulationRun containing the created SimulationState, the SimulationLoop and the thread running the loop
     */
    public static SimulationRun startSimulation() {
        //TODO: make that main calls that somehow or make another function to start sumulation so no code duplication
        // create the model
        SimulationState state = new SimulationState();
        publishedState = state;

        // create loop and helpers
        SimulationLoop loop = new SimulationLoop(state);
        ConsoleRenderSystem renderer = new ConsoleRenderSystem(state);
        InputHandler InputHandler = new InputHandler(state);

        // sample test setup (same as main)
        Grid grid = state.getGrid();

        //for test purposes



        // start the console input reader
        new Thread(new InputReader(), "InputReader-Thread").start();

        // start the simulation loop in its own thread
        Thread t = new Thread(() -> {
            try {
                loop.runSimulation(renderer, InputHandler);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "ConsoleMain-Thread");
        t.start();


        return new SimulationRun(t, state, loop);
    }

    /**
     *
     * @param width  of the simulation grid
     * @param height of the simulation grid
     * @param toric  true if toric grid
     * @return the simulation run containing the thread, state and loop
     */
    public static SimulationRun startSimulation(int width, int height, boolean toric) {
        SimulationState state = new SimulationState();
        // replace the default grid with requested dimensions
        state.setGrid(new Grid(width, height, toric));
        publishedState = state;

        SimulationLoop loop = new SimulationLoop(state);
        ConsoleRenderSystem renderer = new ConsoleRenderSystem(state);
        InputHandler InputHandler = new InputHandler(state);

        new Thread(new InputReader(), "InputReader-Thread").start();

        Thread t = new Thread(() -> {
            try {
                loop.runSimulation(renderer, InputHandler);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "ConsoleMain-Thread");
        t.start();

        Grid grid = state.getGrid();




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
