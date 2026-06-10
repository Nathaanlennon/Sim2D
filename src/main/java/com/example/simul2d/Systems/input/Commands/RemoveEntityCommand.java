package com.example.simul2d.Systems.input.Commands;

import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Vec2;

public record RemoveEntityCommand(Vec2 position, Entities entityType) implements Command {
}
