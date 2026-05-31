package com.example.simul2d.Console;


import com.example.simul2d.Core.*;
import com.example.simul2d.Systems.UpdateSimulation;
import com.example.simul2d.grid.FastMold;
import com.example.simul2d.grid.Grid;
import com.example.simul2d.grid.SlowMold;
import com.example.simul2d.input.InputHandler;
import com.example.simul2d.input.InputReader;
import com.example.simul2d.render.Render;

public class ConsoleMain {

    public static void main(String[] args) throws InterruptedException {

        SimulationState state = new SimulationState();

        SimulationLoop loop = new SimulationLoop(state);
        Render renderer = new Render(state);
        InputHandler inputHandler = new InputHandler(state);
        
        // for test purposes for now
        Grid grid = state.getGrid();
        grid.getCell(0,0).addEntity(new SlowMold());
        grid.getCell(grid.getWidth()-1, grid.getHeight()-1).addEntity(new FastMold());
        
        new Thread(new InputReader()).start();
        loop.runSimulation(renderer, inputHandler);
        
        
        
    }

}
