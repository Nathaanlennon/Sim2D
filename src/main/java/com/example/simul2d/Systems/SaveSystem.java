package com.example.simul2d.Systems;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.example.simul2d.grid.Grid;


/**
 * Utility for serializing and deserializing the grid to disk.
 *
 * <p>The saved format is a Java object stream with the {@code .sim} extension.
 * These helpers are minimal and may throw IO or class-not-found exceptions
 * to the caller for handling.
 */
public class SaveSystem {

    /**
     * Serializes the provided grid to the given filename (appends ".sim").
     *
     * @param grid     the grid to serialize
     * @param filename the target filename without extension
     * @throws IOException if an I/O error occurs during writing
     */
    public static void saveSystem(Grid grid, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename + ".sim"))) {
            oos.writeObject(grid);
        }
    }

    /**
     * Overload that accepts a {@link File}.
     *
     * @param grid the grid to save
     * @param file the destination file
     * @throws IOException if an I/O error occurs during writing
     */
    public static void saveSystem(Grid grid, File file) throws IOException {
        saveSystem(grid, file.getAbsolutePath());
    }

    /**
     * Loads a serialized grid from the provided filename.
     *
     * @param filename file path to read
     * @return the deserialized {@link Grid}
     * @throws IOException if an I/O error occurs during reading
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    public static Grid loadSystem(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Grid) ois.readObject();
        }
    }

    /**
     * Overload that accepts a {@link File}.
     *
     * @throws IOException if an I/O error occurs during reading
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     * @param file the file to read from
     * @return the deserialized {@link Grid}
     */
    public static Grid loadSystem(File file) throws IOException, ClassNotFoundException { // Overloaded method to accept File directly
        return loadSystem(file.getAbsolutePath());
    }
}