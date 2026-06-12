package com.example.simul2d.Systems;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.Entities.Entity;
import com.example.simul2d.Entities.Mold.CircMold1;
import com.example.simul2d.grid.Cell;

import javax.swing.*;

/**
 * Handles console rendering of the simulation state.
 */
public class ConsoleRenderSystem {
    private final SimulationState data;
    //constructors
    /**
     * Creates a renderer bound to a simulation state.
     *
     * @param data the simulation state to display
     */
    public ConsoleRenderSystem(SimulationState data) {
        this.data=data;
    }
//set methods
//get methods
//private methods
    
    
//public methods

    /**
     * Prints a message to the console for debugging purposes only.
     * 
     * @param message the message to display
     * @param clear whether the terminal should be cleared before printing
     */
    public static void printSomething(String message, boolean clear){
        if (clear) clear();
        System.out.println(message);
    }
    /**
     * Prints a message to the console without clearing the terminal first.
     *
     * @param message the message to display
     */
    public static void printSomething(String message){
        printSomething(message, false);
    }

    /**
     * Clears the terminal using ANSI escape sequences.
     */
    public static void clear() {
        // ANSI code to clear the terminal and reset the cursor position
        // \u001B[2J = clear the screen
        // \u001B[H = cursor to home position (top-left corner)
        // \u001B[3J = clear the scrollback buffer (optional, may not work in all terminals)
        System.out.print("\u001B[H\u001B[2J\u001B[3J");
        System.out.flush();
    }
    /**
     * Prints the current simulation state to the console.
     */
    public void printSimulation(){
        clear();

        System.out.println(data.getGrid().toString());
        Cell cell = data.getGrid().getCell(0,0);
        Entity entity = cell.getEntity(Entities.CIRC_MOLD1);
        ConsoleRenderSystem.printSomething(entity.getGrowth()+"");
        
        System.out.printf("time : %f, speed : %f \n", data.getTime(), data.getSpeed());
        System.out.flush();  // Flush the output to ensure it appears immediately
    }
//override methods

}
