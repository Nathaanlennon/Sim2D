package com.example.simul2d.input;

/**
 * Enum représentant toutes les commandes possibles du jeu
 * Chaque commande peut être mappée à une ou plusieurs touches clavier
 */
public enum Command {
    PAUSE("p"),
    SPEED1("1"),
    SPEED2("2"),
    SPEED3("3"),
    INCREASE_SPEED("a"),
    DECREASE_SPEED("s");
    
    //TODO: add commands directty to the enum


    // La touche associée à la commande
    private final String key;

    // Constructeur
    Command(String key) {
        this.key = key;
    }

    // Getter
    public String getKey() {
        return key;
    }


    public static Command fromString(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }

        String normalizedInput = input.trim();

        for (Command command : Command.values()) {
            if (command.getKey().equalsIgnoreCase(normalizedInput)) {
                return command;
            }
        }
        return null;

    }
}

