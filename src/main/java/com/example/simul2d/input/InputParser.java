package com.example.simul2d.input;

import com.example.simul2d.input.Commands.*;

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
