package com.example.simul2d.Console;

import com.example.simul2d.render.Render;

public class ClearTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Test 1: Avant le clear()");
        System.out.println("Ligne 2");
        System.out.println("Ligne 3");
        
        Thread.sleep(2000);
        
        System.out.println("\n=== En train d'appeler clear() ===\n");
        Thread.sleep(1000);
        
        Render.clear();
        
        Thread.sleep(1000);
        System.out.println("Test 2: Après le clear() - ceci devrait être seul sur l'écran");
    }
}

