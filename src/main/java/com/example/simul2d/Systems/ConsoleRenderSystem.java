package com.example.simul2d.Systems;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Entities.Entities;

/**
 * Handles console rendering of the simulation state.
 * <p>
 * This system provides both static utility methods for printing messages
 * and clearing the terminal, and an instance method that prints a full
 * textual representation of the current grid together with simulation
 * time and speed.
 * </p>
 */
public class ConsoleRenderSystem {

    /** The simulation state to be rendered. */
    private final SimulationState data;

    /**
     * Creates a renderer bound to a specific simulation state.
     *
     * @param data the simulation state to display; must not be {@code null}
     */
    public ConsoleRenderSystem(SimulationState data) {
        this.data = data;
    }

    /**
     * Prints a message to the console for debugging purposes.
     * <p>
     * If {@code clear} is {@code true}, the terminal is cleared before the
     * message is printed.
     * </p>
     *
     * @param message the message to display
     * @param clear   whether the terminal should be cleared before printing
     */
    public static void printSomething(String message, boolean clear) {
        if (clear) clear();
        System.out.println(message);
    }

    /**
     * Prints a message to the console without clearing the terminal first.
     * <p>
     * This is a convenience overload that calls {@link #printSomething(String, boolean)}
     * with {@code clear = false}.
     * </p>
     *
     * @param message the message to display
     */
    public static void printSomething(String message) {
        printSomething(message, false);
    }

    /**
     * Clears the terminal using ANSI escape sequences.
     * <p>
     * The escape codes used are:
     * <ul>
     *   <li>{@code \u001B[2J} – clear the screen</li>
     *   <li>{@code \u001B[H}  – move cursor to home position (top‑left)</li>
     *   <li>{@code \u001B[3J} – clear the scroll‑back buffer (optional, may not work in all terminals)</li>
     * </ul>
     * The output stream is flushed immediately.
     * </p>
     */
    public static void clear() {
        // ANSI codes: clear screen, reset cursor, clear scrollback buffer
        System.out.print("\u001B[H\u001B[2J\u001B[3J");
        System.out.flush();
    }

    /**
     * Prints the current simulation state to the console.
     * <p>
     * The output consists of:
     * <ul>
     *   <li>a textual grid representation obtained from {@code data.getGrid().toString()}</li>
     *   <li>a debug message showing the growth of the {@link Entities#CIRC_MOLD1} entity
     *       at cell (0,0), if present; otherwise a message indicating its absence</li>
     *   <li>the current simulation time and speed</li>
     * </ul>
     * The terminal is cleared before printing.
     * </p>
     */
    public void printSimulation() {
        clear();

        System.out.println(data.getGrid().toString());

        var cell = data.getGrid().getCell(0, 0);
        var ent = cell.getEntity(Entities.CIRC_MOLD1);
        if (ent != null) {
            ConsoleRenderSystem.printSomething(ent.getGrowth() + "");
        } else {
            ConsoleRenderSystem.printSomething("no entity CIRC_MOLD1 at (0,0)");
        }

        System.out.printf("time : %f, speed : %f \n", data.getTime(), data.getSpeed());
        System.out.flush();  // ensure immediate output
    }
}