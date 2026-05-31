package com.example.simul2d.input;

import com.example.simul2d.Core.SimulationState;

import java.util.concurrent.ConcurrentLinkedQueue;




/**
 * Consumes raw input strings and applies the matching command to the simulation state.
 */
public class InputHandler {
    private final SimulationState data;
    /**
     * Shared queue that stores raw input lines read from the console.
     */
    public static final ConcurrentLinkedQueue<String> queue =
            new ConcurrentLinkedQueue<>();  // it's the cue for the input system
    //constructor
    /**
     * Creates a handler bound to a simulation state.
     *
     * @param data the simulation state to update
     */
    public InputHandler(SimulationState data) {
        this.data = data;
    }

    //set methods
//get methods
//private methods
//public methods
    /**
     * Processes every queued input command until the queue becomes empty.
     */
    public void handleInput() {
        while (!queue.isEmpty()) {
            String rawInput = queue.poll();
            Command command = Command.fromString(rawInput);

            if (command == null) {
                continue;
            }

            switch (command) {
                case INCREASE_SPEED -> data.setSpeed(data.getSpeed() + 1);
                case DECREASE_SPEED -> data.setSpeed(Math.max(0, data.getSpeed() - 1));
                case SPEED1 -> data.setSpeed(1);
                case SPEED2 -> data.setSpeed(2);
                case SPEED3 -> data.setSpeed(3);
                case PAUSE -> data.changePause();
            }
        }
    }
//override methods

}
