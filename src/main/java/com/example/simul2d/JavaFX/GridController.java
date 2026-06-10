package com.example.simul2d.JavaFX;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Systems.ConsoleRenderSystem;
import com.example.simul2d.Systems.input.Commands.*;
import com.example.simul2d.Systems.input.InputHandler;
import com.example.simul2d.grid.Cell;
import com.example.simul2d.grid.Grid;
import com.example.simul2d.grid.Material;

import com.example.simul2d.grid.Vec2;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class GridController implements NeedsSimulationState, NeedsUiState {

    // Reference to the shared simulation state (injected by HelloApplication)
    private SimulationState state;
    private UiState uiState;
    private Grid grid= null;
    private Rectangle[][] tiles; // 2D array to hold references to the grid cell rectangles

    
    
    private void clickOnCell(Vec2 position) {
        if (uiState.getActiveTool() == null || !state.isPaused()) return;
        switch (uiState.getActiveTool()) {
            case MATERIALS -> {
                if (uiState.getSelectedMaterial()!=null) {
                    Command command = new SetMaterialCommand(position, uiState.getSelectedMaterial());
                    InputHandler.COMMAND_QUEUE.add(command);
                }
            }
            case ERASE_MATERIAL -> {
                InputHandler.COMMAND_QUEUE.add(new SetMaterialCommand(position, Material.EMPTY));
            }
            case ENTITIES -> {
                if(uiState.getSelectedEntity()!=null) InputHandler.COMMAND_QUEUE.add(new AddEntityCommand(position, uiState.getSelectedEntity()));
            }
            case ERASE_ENTITY -> {
                InputHandler.COMMAND_QUEUE.add(new RemoveEntityCommand(position, uiState.getSelectedEntity()));
            }
            default -> {}
        }
        
    }
    
    @FXML
    private GridPane gridDisplay;
//
    @FXML
    private void initialize() {
        System.out.println("GridController initialized");
    }

    private void initializeGridDisplay() {
        if (grid == null) {
            return;
        }

        gridDisplay.getChildren().clear();

        int h = grid.getHeight();
        int w = grid.getWidth();

        tiles = new Rectangle[h][w];

        //todo: click and drag
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                Rectangle tile = new Rectangle(35, 35);
                tile.setFill(Color.web("#FFFFFF"));
                tile.setStroke(Color.web("#333333"));
                tile.setStrokeWidth(1.0);

                int cx = x;
                int cy = y;

                tile.setOnMouseClicked(event -> {
                    clickOnCell(new Vec2(cx, cy));
                    if (uiState.getActiveTool() == ToolsType.MATERIALS && state.isPaused()){
                        tile.setFill(Color.web(
                                uiState.getSelectedMaterial().getColorHex()
                        ));
                    }
                });

                tiles[y][x] = tile;
                gridDisplay.add(tile, x, y);
            }
        }
    }

    @Override
    public void setSimulationState(SimulationState state) {
        this.state = state;
        this.grid = state.getGrid();
        initializeGridDisplay();
        refreshUI(); // Dessine la grille dès qu'on reçoit les données
    }
    
    @Override
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
    
    @Override
    public void setUiState(UiState uiState) {
        this.uiState = uiState;
    }
}