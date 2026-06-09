package com.example.simul2d.Systems.input;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Systems.input.Commands.*;

 
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
    public static final ConcurrentLinkedQueue<Command> COMMAND_QUEUE =
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
            case PauseCommand p -> data.changePause();
            case SpeedCommand s -> data.setSpeed(s.speed());
            case DecreaseSpeedCommand d-> data.setSpeed(Math.max(0, data.getSpeed() - 1));
            case IncreaseSpeedCommand i -> data.setSpeed(data.getSpeed() + 1);
        }
    }
//public methods

    /**
     * Processes every queued input command until the queue becomes empty.
     */
    public void handleInput() {
        while (!queue.isEmpty()) {
            String rawInput = queue.poll();
            Command command = InputParser.parseInput(rawInput);
            if (command == null) {
                continue;
            }

          

            processCommand(command);
        }
        while (!COMMAND_QUEUE.isEmpty()) {
            Command command = COMMAND_QUEUE.poll();
            processCommand(command);
        }
    }

  
    
//override methods

}
