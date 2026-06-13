package com.example.simul2d.Systems;

import java.io.*;

import com.example.simul2d.grid.Grid;


public class SaveSystem {

    public static void saveSystem(Grid grid, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename+".sim"))) {
            oos.writeObject(grid);
        }
    }

    public static void saveSystem(Grid grid, File file) throws IOException { // Overloaded method to accept File directly
        saveSystem(grid, file.getAbsolutePath() + ".sim");
    }

    public static Grid loadSystem(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Grid) ois.readObject();
        }
    }

    public static Grid loadSystem(File file) throws IOException, ClassNotFoundException { // Overloaded method to accept File directly
        return loadSystem(file.getAbsolutePath());
    }
}