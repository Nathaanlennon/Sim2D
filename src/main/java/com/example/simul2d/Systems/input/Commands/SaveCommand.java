package com.example.simul2d.Systems.input.Commands;

/** Command to save the current simulation to {@link #filePath()}. */
public record SaveCommand(String filePath) implements Command{
}
