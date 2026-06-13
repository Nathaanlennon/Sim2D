package com.example.simul2d.Systems.input.Commands;

/**
 * Enumeration of the supported input commands.
 *
 * <p>Each constant is associated with a keyboard key used by the console input
 * system. Use {@link #fromString(String)} to parse a user input into the
 * corresponding command.</p>
 */
public enum CommandType {
    /** Pause or resume the simulation. */
    PAUSE("p"),
    /** Set simulation speed to slow (1x). */
    SPEED1("1"),
    /** Set simulation speed to medium (2x). */
    SPEED2("2"),
    /** Set simulation speed to fast (3x). */
    SPEED3("3"),
    /** Increase the simulation speed. */
    INCREASE_SPEED("a"),
    /** Save the current simulation state. */
    SAVE("save"),
    /** Load a previously saved simulation state. */
    LOAD("load"),
    /** Decrease the simulation speed. */
    DECREASE_SPEED("s");
    
    // TODO: add commands directly to the enum

    /** The keyboard key that triggers this command. */
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
     * @return the keyboard key as a string
     */
    public String getKey() {
        return key;
    }
    
    /**
     * Converts a raw input string into a command when possible.
     *
     * @param input the user input to parse; may be null or blank
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