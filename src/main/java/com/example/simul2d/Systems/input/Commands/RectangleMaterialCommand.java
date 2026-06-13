package com.example.simul2d.Systems.input.Commands;

import com.example.simul2d.grid.Material;
import com.example.simul2d.grid.Vec2;

/**
 * Command to fill a rectangular area (inclusive) with the provided material.
 *
 * @param start the first corner of the rectangle
 * @param end the opposite corner of the rectangle
 * @param material the material to fill the area with
 */
public record RectangleMaterialCommand(Vec2 start, Vec2 end, Material material) implements Command {
}
