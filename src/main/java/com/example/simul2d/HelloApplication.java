package com.example.simul2d;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void init() {
        // Called exactly once before the window is displayed.
        System.out.println("init(): JavaFX application is starting.");
    }

    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader reads the FXML file and builds the graphical interface.
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        // Increased base resolution to hide OS resizing transition
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);

        stage.setTitle("Simul2D - JavaFX demo");
        stage.setScene(scene);

        // ENGINEERING DIRECTIVE: Force the OS to maximize the window before rendering
        stage.setMaximized(true);

        // show() performs the global Draw Call and displays the window on screen.
        stage.show();
    }

    @Override
    public void stop() {
        // Called when the application closes (memory deallocation).
        System.out.println("stop(): JavaFX application closing.");
    }

    public static void main(String[] args) {
        launch();
    }
}
