package com.example.simul2d.Systems.input.Commands;

/** Command representing a request to load a saved simulation from {@link #filePath()}. */
public record LoadCommand(String filePath) implements Command{
}
