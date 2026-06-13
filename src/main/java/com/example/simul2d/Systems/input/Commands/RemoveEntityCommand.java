package com.example.simul2d.Systems.input.Commands;

import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Vec2;

/**
 * Command to remove one entity of the specified type at the given position.
 *
 * @param position the grid position where the entity should be removed
 * @param entityType the type of entity to remove
 */
public record RemoveEntityCommand(Vec2 position, Entities entityType) implements Command {
}