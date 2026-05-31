package com.example.simul2d.input;

/**
 * Enumeration of the supported input commands.
 *
 * <p>Each constant is associated with a keyboard key used by the console input
 * system.</p>
 */
public enum Command {
    PAUSE("p"),
    SPEED1("1"),
    SPEED2("2"),
    SPEED3("3"),
    INCREASE_SPEED("a"),
    DECREASE_SPEED("s");
    
    //TODO: add commands directty to the enum

    
    private final String key;
    
    /**
     * Creates a command associated with a keyboard key.
     *
     * @param key the input key that triggers the command
     */
    Command(String key) {
        this.key = key;
    }

    /**
     * Returns the key mapped to this command.
     *
     * @return the keyboard key as a string
     */
    public String getKey() {
        return key;
    }
    
  
    /**
     * Converts a raw input string into a command when possible.
     *
     * @param input the user input to parse
     * @return the matching command, or {@code null} if no command matches
     */
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

