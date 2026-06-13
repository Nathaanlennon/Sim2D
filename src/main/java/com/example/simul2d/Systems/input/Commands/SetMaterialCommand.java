package com.example.simul2d.Systems.input.Commands;

import com.example.simul2d.grid.Material;
import com.example.simul2d.grid.Vec2;

/** Command to set the material at {@link #position()} to {@link #material()}. */
public record SetMaterialCommand(Vec2 position, Material material) implements Command {
}
