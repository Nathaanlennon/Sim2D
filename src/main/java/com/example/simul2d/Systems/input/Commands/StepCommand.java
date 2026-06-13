package com.example.simul2d.Systems.input.Commands;

/**
 * Command to advance the simulation a fixed number of steps.
 *
 * @param stepsNumber number of simulation steps to execute
 */
public record StepCommand(int stepsNumber) implements Command {
}
