package com.example.simul2d.Systems.input.Commands;

/**
 * Enumeration of the supported input commands.
 *
 * <p>Each constant is associated with a keyboard key used by the console input
 * system.</p>
 */
public enum CommandType {
    PAUSE("p"),
    SPEED1("1"),
    SPEED2("2"),
    SPEED3("3"),
    INCREASE_SPEED("a"),
    SAVE("save"),
    LOAD("load"),
    DECREASE_SPEED("s");
    
    //TODO: add commands directty to the enum

    
    private final String key;
    
    /**
     * Creates a command associated with a keyboard key.
     *
     * @param key the input key that triggers the command
     */
    CommandType(String key) {
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
    public static CommandType fromString(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }

        String normalizedInput = input.trim();

        for (CommandType commandType : CommandType.values()) {
            if (commandType.getKey().equalsIgnoreCase(normalizedInput)) {
                return commandType;
            }
        }
        return null;

    }
}

