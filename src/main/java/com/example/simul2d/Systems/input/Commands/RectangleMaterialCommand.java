package com.example.simul2d.Systems.input.Commands;

import com.example.simul2d.grid.Material;
import com.example.simul2d.grid.Vec2;

/** Command to fill a rectangular area (inclusive) with the provided {@link #material()}. */
public record RectangleMaterialCommand(Vec2 start, Vec2 end, Material material) implements Command {
}
