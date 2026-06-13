package com.example.simul2d.Systems.input.Commands;

/**
 * Command representing a direct speed set request.
 *
 * @param speed the new simulation speed value
 */
public record SpeedCommand(int speed) implements Command {
}