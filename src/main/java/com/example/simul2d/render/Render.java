package com.example.simul2d.render;

import com.example.simul2d.Core.SimulationState;

public class Render {
    private final SimulationState data;

    //constructors
    public Render(SimulationState data) {
        this.data=data;
    }
//set methods
//get methods
//private methods
    
    
//public methods

    public static void clear() {
        System.out.print("\u001B[2J\u001B[H");
        System.out.flush();
    }
    public void printSimulation(){
        clear();
        
        System.out.printf("time : %f, speed : %f \n", data.getTime(), data.getSpeed());
        System.out.flush();  // Force le flush après chaque affichage
    }
//override methods

}
