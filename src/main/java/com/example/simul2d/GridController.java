package com.example.simul2d;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.grid.Cell;
import com.example.simul2d.grid.Grid;
import com.example.simul2d.grid.Material;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridController implements NeedsSimulationState {

    // Reference to the shared simulation state (injected by HelloApplication)
    private SimulationState state;
    private Grid grid= null;
    private Rectangle[][] tiles; // 2D array to hold references to the grid cell rectangles

    @FXML
    private GridPane gridDisplay;
//
    @FXML
    private void initialize() {
        System.out.println("GridController initialized");
    }

    @Override
    public void setSimulationState(SimulationState state) {
        this.state = state;
        this.grid = state.getGrid();
        initializeGridDisplay();
        refreshUI(); // Dessine la grille dès qu'on reçoit les données
    }

    private void initializeGridDisplay() {
        if (grid == null) {
            return;
        }
        gridDisplay.getChildren().clear();

        int h = grid.getHeight();
        int w = grid.getWidth();
        tiles = new Rectangle[h][w];
        for(int y = 0; y< grid.getHeight();y++){
            for (int x = 0; x < grid.getWidth(); x++) {
                Rectangle tile = new Rectangle(35, 35);
                tile.setFill(Color.web("#FFFFFF"));
                tile.setStroke(Color.web("#333333"));
                tile.setStrokeWidth(1.0);
                tiles[y][x] = tile; // Store reference for later updates
                gridDisplay.add(tile, x, y);
            }
        }
    }


    public void refreshUI() {

        if (grid == null) {
            return;
        }

        for(int y = 0; y< grid.getHeight();y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Cell cell = grid.getCell(x, y);
                String hex = cell != null ? cell.getColorHex() : Material.EMPTY.getColorHex();
                tiles[y][x].setFill(Color.web(hex));

            }
        }
    }
}