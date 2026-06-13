package com.example.simul2d.Systems.input.Commands;

import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Vec2;

public record RectangleEntityCommand(Vec2 start, Vec2 end, Entities entityType) implements Command {
}
