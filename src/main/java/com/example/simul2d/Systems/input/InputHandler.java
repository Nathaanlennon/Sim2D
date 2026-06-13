package com.example.simul2d.Systems.input;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.example.simul2d.Core.SimulationLoop;
import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Systems.ConsoleRenderSystem;
import com.example.simul2d.Systems.SaveSystem;
import com.example.simul2d.Systems.input.Commands.*;


/**
 * Consumes raw input strings and applies the matching command to the
 * {@link com.example.simul2d.Core.SimulationState}.
 *
 * <p>Commands are read from the shared `queue` and translated into
 * {@link com.example.simul2d.Systems.input.Commands.Command} instances
 * before being applied. This class is designed to be invoked regularly by
 * the simulation loop or by a dedicated input-handling thread.
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
     *
     * @param command the {@link Command} to process
     */
    private void processCommand(Command command) {
        switch (command) {
            case PauseCommand p -> data.changePause();
            case SpeedCommand s -> data.setSpeed(s.speed());
            case DecreaseSpeedCommand d -> data.setSpeed(Math.max(0, data.getSpeed() - 1));
            case IncreaseSpeedCommand i -> data.setSpeed(data.getSpeed() + 1);
            case SetMaterialCommand s -> data.getGrid().getCell(s.position()).setMaterial(s.material());
            case AddEntityCommand a -> data.getGrid().getCell(a.position()).addEntity(a.entityType().createEntity());
            case RemoveEntityCommand r -> data.getGrid().getCell(r.position()).removeEntity(r.entityType());
            case ClearEntitiesCommand c -> data.getGrid().getCell(c.position()).clearEntities();
            case RectangleMaterialCommand r -> {
                for (int y = Math.min(r.start().y(), r.end().y()); y <= Math.max(r.start().y(), r.end().y()); y++) {
                    for (int x = Math.min(r.start().x(), r.end().x()); x <= Math.max(r.start().x(), r.end().x()); x++) {
                        data.getGrid().getCell(x, y).setMaterial(r.material());
                    }
                }
            }
            case RectangleEntityCommand r -> {
                for (int y = Math.min(r.start().y(), r.end().y()); y <= Math.max(r.start().y(), r.end().y()); y++) {
                    for (int x = Math.min(r.start().x(), r.end().x()); x <= Math.max(r.start().x(), r.end().x()); x++) {
                        data.getGrid().getCell(x, y).addEntity(r.entityType().createEntity());
                    }
                }
            }
            case SaveCommand s -> {
                try {
                    SaveSystem.saveSystem(data.getGrid(), s.filePath());
                } catch (Exception e) {
                    ConsoleRenderSystem.printSomething("Failed to save: " + e.getMessage());
                }
            }
            case LoadCommand l -> {
                try {
//                    data.resetSimulation();
                    data.setGrid(SaveSystem.loadSystem(l.filePath()));
                } catch (Exception e) {
                    ConsoleRenderSystem.printSomething("Failed to load: " + e.getMessage());
                }
                data.changePause(false); //TODO: problems while loading, needs lock unlock reading writting 
            }
            case StepCommand sp -> {
                for (int i = 0; i < sp.stepsNumber();i++){
                    SimulationLoop.self.update();
                }
                SimulationLoop.self.render();

            }
            default -> {
            }
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
