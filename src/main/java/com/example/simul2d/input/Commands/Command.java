package com.example.simul2d.input.Commands;

public sealed interface Command
permits PauseCommand, SpeedCommand, IncreaseSpeedCommand, DecreaseSpeedCommand {
}
