package com.example.simul2d.Systems.input;
import java.util.Scanner;

/**
 * Background reader that continuously consumes lines from standard input
 * and enqueues them for the input handling subsystem.
 *
 * <p>This class is intended to be executed on its own thread. It blocks
 * on {@link Scanner#nextLine()} until the user provides input.
 */
public class InputReader implements Runnable {

    /**
     * Starts the blocking input loop and forwards lines to {@link InputHandler#queue}.
     */
    @Override
    public void run() {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            String line = scanner.nextLine();

            InputHandler.queue.add(line);
        }
    }
}