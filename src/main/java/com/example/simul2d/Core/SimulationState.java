package com.example.simul2d.Core;

import com.example.simul2d.grid.Grid;

/**
 * Holds the mutable state of the simulation. (It's like the data)
 */
public class SimulationState {
    private double speed;
    private double time;
    private Grid grid;
    private boolean paused;


    //constructors
    /**
     * Creates a simulation state with explicit values. Precursor to loader-based initialization
     *
     * @param speed the initial speed, clamped to a positive value
     * @param time the initial simulation time, clamped to zero or above
     * @param paused whether the simulation starts paused
     * @param grid the grid backing the simulation
     */
    SimulationState(double speed, double time, boolean paused, Grid grid) {
        this.speed = speed >= 0 ? speed : 1.0;
        this.time = time >= 0 ? time : 0.0; // as timecode progression but setTime may be useless and unusable?
        this.paused = paused;
        this.grid=grid;
    }
    
    /**
     * Creates a default simulation state.
     */
    public SimulationState(){
        this(1.0,0.0,false,  new Grid(10,10,false)); //TODO: change that to real input size
    }
    
    

    //set methods
    /**
     * Toggles the paused state.
     */
    public void changePause() {
        this.paused = !this.paused;
    }
    
    /**
     * Updates the simulation speed when the provided value is positive.
     *
     * @param speed the new speed value
     */
    public void setSpeed(double speed) {
        if (speed>0){
            this.speed = speed;
        }
    }

    //get methods

    public Grid getGrid() {
        return grid;
    }

    /**
     * Returns whether the simulation is paused.
     *
     * @return {@code true} when paused
     */
    public boolean isPaused() {
        return paused;
    }
    /**
     * Returns the current simulation speed.
     *
     * @return the speed value
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Returns the current simulation time.
     *
     * @return the elapsed time
     */
    public double getTime() {
        return time;
    }

    //private methods
//public methods
    
    
    /**
     * Adds the given delta time to the simulation clock.
     *
     * @param dt the time increment to add
     */
    public void addTime(double dt) {
        this.time += dt;
    }
//override methods

}
