package com.example.simul2d.Systems.input.Commands;

import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Vec2;

/**
 * Command to add a new entity of the specified type at the given position.
 *
 * @param position the grid position where the entity will be added
 * @param entityType the type of entity to create and place
 */
public record AddEntityCommand(Vec2 position, Entities entityType) implements Command {
}