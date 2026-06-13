package com.example.simul2d.Systems.input.Commands;

import com.example.simul2d.grid.Vec2;

public record ClearEntitiesCommand(Vec2 position) implements Command {
}
