package com.example.simul2d.Systems.input.Commands;

import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Vec2;

/** Command to add a new entity of type {@link #entityType()} at {@link #position()}. */
public record AddEntityCommand(Vec2 position, Entities entityType) implements Command {
}
