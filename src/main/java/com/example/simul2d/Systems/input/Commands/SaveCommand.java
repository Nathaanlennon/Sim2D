package com.example.simul2d.Systems.input.Commands;

/**
 * Command to save the current simulation to the given file path.
 *
 * @param filePath destination path where the simulation will be saved
 */
public record SaveCommand(String filePath) implements Command {
}