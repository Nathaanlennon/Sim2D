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

    /**
     * for debug purposes only
     * 
     * @param message lessage to send
     * @param clear boolean to see if we clear the terminal or not, default false through surcharge
     */
    public static void printSomething(String message, boolean clear){
        if (clear) clear();
        System.out.println(message);
    }
    public static void printSomething(String message){
        printSomething(message, false);
    }

    public static void clear() {
        // Code ANSI pour clear le terminal
        // \u001B[2J = clear toute l'écran
        // \u001B[H = cursor au début (0,0)
        // \u001B[3J = clear aussi l'historique du scrollback
        System.out.print("\u001B[H\u001B[2J\u001B[3J");
        System.out.flush();
    }
    public void printSimulation(){
        clear();
        
        System.out.printf("time : %f, speed : %f \n", data.getTime(), data.getSpeed());
        System.out.flush();  // Force le flush après chaque affichage
    }
//override methods

}
