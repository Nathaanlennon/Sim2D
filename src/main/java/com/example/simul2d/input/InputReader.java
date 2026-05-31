package com.example.simul2d.input;
import java.util.Scanner;

/**
 * Continuously reads lines from standard input and pushes them into the shared queue.
 */
public class InputReader implements Runnable {

    /**
     * Starts the blocking input loop.
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