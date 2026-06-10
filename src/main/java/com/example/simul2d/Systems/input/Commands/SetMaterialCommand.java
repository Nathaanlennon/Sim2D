package com.example.simul2d.Systems.input.Commands;

import com.example.simul2d.grid.Material;
import com.example.simul2d.grid.Vec2;

public record SetMaterialCommand(Vec2 position, Material material) implements Command {
}
