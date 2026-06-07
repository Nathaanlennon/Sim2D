package com.example.simul2d.Systems;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.example.simul2d.grid.Grid;


public class SaveSystem {

    public static void saveSystem(Grid grid, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(grid);
        }
    }

    public static Grid loadSystem(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Grid) ois.readObject();
        }
    }
}