package com.example.simul2d.Systems.input;

import com.example.simul2d.Entities.Entities;
import com.example.simul2d.Systems.input.Commands.AddEntityCommand;
import com.example.simul2d.Systems.input.Commands.ClearEntitiesCommand;
import com.example.simul2d.Systems.input.Commands.Command;
import com.example.simul2d.Systems.input.Commands.DecreaseSpeedCommand;
import com.example.simul2d.Systems.input.Commands.IncreaseSpeedCommand;
import com.example.simul2d.Systems.input.Commands.LoadCommand;
import com.example.simul2d.Systems.input.Commands.PauseCommand;
import com.example.simul2d.Systems.input.Commands.RectangleEntityCommand;
import com.example.simul2d.Systems.input.Commands.RectangleMaterialCommand;
import com.example.simul2d.Systems.input.Commands.RemoveEntityCommand;
import com.example.simul2d.Systems.input.Commands.SaveCommand;
import com.example.simul2d.Systems.input.Commands.SetMaterialCommand;
import com.example.simul2d.Systems.input.Commands.SpeedCommand;
import com.example.simul2d.grid.Material;
import com.example.simul2d.grid.Vec2;

public class InputParser {

    //constructors
    public InputParser() {
    }

    //set methods
//get methods
//private methods
//public methods
    public static Command parseInput(String input) {
        input = input.trim();
        switch (input.toLowerCase()) {
            case "pause", "p" -> {
                return new PauseCommand();
            }
            case "1", "2", "3", "4", "5", "6", "7", "8", "9" -> {
                int speed = Integer.parseInt(input);
                return new SpeedCommand(speed);
            }
            case "a", "+" -> {
                return new IncreaseSpeedCommand();
            }
            case "d", "-" -> {
                return new DecreaseSpeedCommand();
            }
        }
        
        return null;
    }

//override methods

}
