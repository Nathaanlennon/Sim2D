package com.example.simul2d.Console;


import com.example.simul2d.Core.*;
import com.example.simul2d.input.InputHandler;
import com.example.simul2d.render.Render;

public class ConsoleMain {

    public static void main(String[] args) throws InterruptedException {

        SimulationState state = new SimulationState();

        SimulationLoop loop = new SimulationLoop(state);
        Render renderer = new Render(state);
        loop.runSimulation(renderer);
        
    }

}
