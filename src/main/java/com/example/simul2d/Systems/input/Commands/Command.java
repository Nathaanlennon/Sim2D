package com.example.simul2d.Systems.input.Commands;

/**
 * Marker interface for parsed input commands.
 *
 * <p>Concrete command implementations are represented as records and enumerate
 * the arguments required to perform the action. The simulation's input
 * subsystem translates raw user input into these command objects which are
 * then executed by {@link com.example.simul2d.Systems.input.InputHandler}.
 */
public sealed interface Command
        permits PauseCommand,
        SpeedCommand,
        IncreaseSpeedCommand,
        DecreaseSpeedCommand,
        SetMaterialCommand,
        AddEntityCommand,
        RemoveEntityCommand,
        RectangleMaterialCommand,
        RectangleEntityCommand,
        SaveCommand,
        LoadCommand,
        ClearEntitiesCommand,
        StepCommand
{

}
