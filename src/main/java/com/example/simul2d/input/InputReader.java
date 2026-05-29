package com.example.simul2d.input;
import java.util.Scanner;

public class InputReader implements Runnable {

    @Override
    public void run() {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            String line = scanner.nextLine();

            InputHandler.queue.add(line);
        }
    }
}