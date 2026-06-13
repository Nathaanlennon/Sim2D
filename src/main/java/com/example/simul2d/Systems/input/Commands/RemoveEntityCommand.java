package com.example.simul2d.Systems.input.Commands;

import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Vec2;

/** Command to remove one entity of {@link #entityType()} at {@link #position()}. */
public record RemoveEntityCommand(Vec2 position, Entities entityType) implements Command {
}
