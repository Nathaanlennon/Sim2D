package com.example.simul2d.Core;

import com.example.simul2d.grid.Grid;

public class SimulationState {
    private double speed;
    private double time;
    private Grid grid;
    private boolean paused;


    //constructors
    SimulationState(double speed, double time, boolean paused, Grid grid) {
        this.speed = speed >= 0 ? speed : 1.0;
        this.time = time >= 0 ? time : 0.0; // as timecode progression but setTime may be useless and unusable?
        this.paused = paused;
        this.grid=grid;
    }
    
    public SimulationState(){
        this(1.0,0.0,false,  new Grid(10,10,false)); //TODO: change that to real input size
    }
    
    

    //set methods
    public void changePause() {
        this.paused = !this.paused;
    }
    
    public void setSpeed(double speed) {
        if (speed>0){
            this.speed = speed;
        }
    }

    //get methods

    public Grid getGrid() {
        return grid;
    }

    public boolean isPaused() {
        return paused;
    }
    public double getSpeed() {
        return speed;
    }

    public double getTime() {
        return time;
    }

    //private methods
//public methods
    
    
    public void addTime(double dt) {
        this.time += dt;
    }
//override methods

}
