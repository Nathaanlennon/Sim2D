package com.example.simul2d.Systems.input.Commands;

import com.example.simul2d.grid.Material;
import com.example.simul2d.grid.Vec2;

/**
 * Command to set the material at the specified position.
 *
 * @param position the grid position to modify
 * @param material the material to apply at the given position
 */
public record SetMaterialCommand(Vec2 position, Material material) implements Command {
}