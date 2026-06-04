package com.example.simul2d.Console;


import com.example.simul2d.Core.*;
import com.example.simul2d.Systems.UpdateSimulation;
import com.example.simul2d.grid.Grid;
import com.example.simul2d.grid.AxialMold1;
import com.example.simul2d.grid.CircMold1;
import com.example.simul2d.grid.CircularMold;
import com.example.simul2d.grid.DividedMold1;
import com.example.simul2d.input.InputHandler;
import com.example.simul2d.input.InputReader;
import com.example.simul2d.render.Render;

/**
 * Console-based entry point for running the simulation loop.
 */
public class ConsoleMain {

    /**
     * Starts the simulation and the background input reader.
     *
     * @param args command-line arguments, unused by this launcher
     * @throws InterruptedException if the simulation loop is interrupted while sleeping
     */
    public static void main(String[] args) throws InterruptedException {

        SimulationState state = new SimulationState();

        SimulationLoop loop = new SimulationLoop(state);
        Render renderer = new Render(state);
        InputHandler inputHandler = new InputHandler(state);
        
        // for test purposes for now
        Grid grid = state.getGrid();
        grid.getCell(0,0).addEntity(new CircMold1());
        grid.getCell(grid.getWidth()-1, grid.getHeight()-1).addEntity(new DividedMold1());
        grid.getCell(0, grid.getHeight()-1).addEntity(new AxialMold1());

        
        new Thread(new InputReader()).start();
        loop.runSimulation(renderer, inputHandler);
        
        
        
    }

}
