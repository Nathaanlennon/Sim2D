package com.example.simul2d.Systems.input.Commands;

/** Command to advance the simulation a fixed number of steps. */
public record StepCommand(int StepsNumber) implements Command {
}
