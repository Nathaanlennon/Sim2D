package com.example.simul2d.Core;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.example.simul2d.grid.Grid;

/**
 * Encapsulates the mutable state of the simulation.
 *
 * <p>This class stores the simulation-wide data such as the {@link com.example.simul2d.grid.Grid},
 * current time and execution speed. It also exposes a {@link java.util.concurrent.locks.ReadWriteLock}
 * which callers can use to obtain thread-safe access to the state from different
 * threads (for example the simulation thread and UI threads).
 */
public class SimulationState {
    private volatile double speed;
    private volatile double time;
    private Grid grid;
    private volatile boolean paused;
    // A thread-safe pre-rendered snapshot of the grid for UI consumption.
    // The simulation thread should call updateGridSnapshot() after it mutates the grid.
    private final AtomicReference<String> gridSnapshot = new AtomicReference<>("");

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    //constructors
    /**
     * Creates a simulation state with explicit initial values.
     *
     * @param speed initial simulation speed (will be clamped to a positive value)
     * @param time initial simulation time (non-negative)
     * @param paused whether the simulation should start paused
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
        this(1.0,0.0,false,  new Grid(30,30,false)); //TODO: change that to real input size
    }
    
    

    //set methods
    /**
     * Toggles the paused flag.
     *
     * <p>Convenience method to flip between paused and running states.
     */
    public void changePause() {
        boolean copyPaused = this.paused;
        this.paused = !copyPaused;
    }
    public void changePause(boolean paused) {
        this.paused = paused;
    }
    
    /**
     * Sets the simulation speed when the provided value is positive.
     *
     * @param speed the new speed value (must be &gt; 0 to take effect)
     */
    public void setSpeed(double speed) {
        if (speed>0){
            this.speed = speed;
        }
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    //get methods
    public ReadWriteLock getLock() {
        return lock;
    }

    public Grid getGrid() {
        return grid;
    }

    /**
     * Publishes an atomic string snapshot of the grid for UI consumption.
     *
     * <p>Call this method from the simulation thread after mutating the grid to
     * produce a textual representation which UI threads can read without acquiring
     * the state lock.
     */
    public void updateGridSnapshot() {
        // create a textual representation once and publish it atomically
        String snap = grid == null ? "" : grid.toString();
        gridSnapshot.set(snap);
    }

    /**
     * Returns the most recent pre-rendered grid snapshot.
     *
     * <p>If no snapshot has been published yet this method will fall back to
     * returning the current {@link Grid#toString()} value.
     *
     * @return a textual representation of the grid suitable for display
     */
    public String getGridSnapshot() {
        String s = gridSnapshot.get();
        return (s == null || s.isEmpty()) ? (grid == null ? "" : grid.toString()) : s;
    }

    /**
     * Returns whether the simulation is currently paused.
     *
     * @return {@code true} when paused
     */
    public boolean isPaused() {
        return paused;
    }
    /**
     * Returns the current simulation speed.
     *
     * @return the speed value used to control loop timing
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Returns the current simulation time.
     *
     * @return the elapsed time (in simulation steps)
     */
    public double getTime() {
        return time;
    }

    //private methods
//public methods

    public void resetSimulation(){
        setSpeed(1.0);
        time = 0.0;
        grid = new Grid(30,30,false);
    }
    
    
    /**
     * Adds the given delta time to the simulation clock.
     *
     * @param dt the time increment to add (non-negative values will advance time)
     */
    public void addTime(double dt) {
        double newTime = this.time + dt;
        this.time = newTime >= 0 ? newTime : 0.0; // Prevent negative time values
    }
//override methods

}
