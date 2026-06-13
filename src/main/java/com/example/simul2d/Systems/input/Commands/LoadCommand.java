package com.example.simul2d.Systems.input.Commands;

/**
 * Command representing a request to load a saved simulation from the given file path.
 *
 * @param filePath path of the save file to load
 */
public record LoadCommand(String filePath) implements Command {
}