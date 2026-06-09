package com.example.simul2d.Console;

import com.example.simul2d.Systems.ConsoleRenderSystem;

/**
 * Small console program that demonstrates the terminal clearing behavior.
 */
public class ClearTest {
    /**
     * Runs the clear-screen demonstration.
     *
     * @param args command-line arguments, unused in this test
     * @throws InterruptedException if the sleep pauses are interrupted
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Test 1: Avant le clear()");
        System.out.println("Ligne 2");
        System.out.println("Ligne 3");
        
        Thread.sleep(2000);
        
        System.out.println("\n=== En train d'appeler clear() ===\n");
        Thread.sleep(1000);
        
        ConsoleRenderSystem.clear();
        
        Thread.sleep(1000);
        System.out.println("Test 2: Après le clear() - ceci devrait être seul sur l'écran");
    }
}

