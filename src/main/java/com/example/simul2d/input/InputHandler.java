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
    public static final ConcurrentLinkedQueue<Command> commandQueue =
            new ConcurrentLinkedQueue<>();  // it's the cue for the input system, but with commands instead of raw strings
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

    /**
     * Applies the effects of a command to the simulation state.
     * @param command the {@link Command} to process
     */
    private void processCommand(Command command) {
        switch (command) {
            case INCREASE_SPEED -> data.setSpeed(data.getSpeed() + 1);
            case DECREASE_SPEED -> data.setSpeed(Math.max(0, data.getSpeed() - 1));
            case SPEED1 -> data.setSpeed(1);
            case SPEED2 -> data.setSpeed(2);
            case SPEED3 -> data.setSpeed(3);
            case PAUSE -> data.changePause();
        }
    }
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

            processCommand(command);
        }
        while (!commandQueue.isEmpty()) {
            Command command = commandQueue.poll();
            processCommand(command);
        }
    }

  
    
//override methods

}
