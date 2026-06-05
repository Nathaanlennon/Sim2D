package com.example.simul2d;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.grid.Grid;
import com.example.simul2d.grid.Cell;
import com.example.simul2d.grid.Material;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridController implements NeedsSimulationState {

    // Reference to the shared simulation state (injected by HelloApplication)
    private SimulationState state;

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
        refreshUI(); // Dessine la grille dès qu'on reçoit les données
    }

    @Override
    public void refreshUI() {

        if (state == null || gridDisplay == null) {
            return;
        }

        gridDisplay.getChildren().clear();  // 1. On efface les carrés du tour précédent


        // we recup grid from SimulationState
        Grid grid = state.getGrid();
        if (grid == null) {
            return;
        }

        int columns = grid.getWidth();
        int rows = grid.getHeight();

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {

                // we recupe cell who corspond
                Cell cell = grid.getCell(x, y);

                // Valeur de sécurité au cas où la cellule serait null
                String hexColor = Material.EMPTY.getColorHex();

                if (cell != null) {
                    hexColor = cell.getMaterial().getColorHex();
                }

                // we create square graphique in jave fx
                Rectangle tile = new Rectangle(35, 35);
                tile.setFill(Color.web(hexColor));
                tile.setStroke(Color.web("#333333"));
                tile.setStrokeWidth(1.0);

                gridDisplay.add(tile, x, y);
            }
        }
    }
}