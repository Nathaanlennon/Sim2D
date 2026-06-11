package com.example.simul2d.Systems.input.Commands;

public sealed interface Command
        permits PauseCommand,
        SpeedCommand,
        IncreaseSpeedCommand,
        DecreaseSpeedCommand,
        SetMaterialCommand,
        AddEntityCommand,
        RemoveEntityCommand,
        RectangleMaterialCommand,
        RectangleEntityCommand {
}
