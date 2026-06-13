package com.example.simul2d.Systems.input.Commands;

import com.example.simul2d.grid.Vec2;

/**
 * Command to remove all entities from the cell at {@link #position()}.
 * @param position the grid position of the cell to clear
 */
public record ClearEntitiesCommand(Vec2 position) implements Command {
}
