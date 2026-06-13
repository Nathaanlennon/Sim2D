package com.example.simul2d.JavaFX;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Systems.input.Commands.AddEntityCommand;
import com.example.simul2d.Systems.input.Commands.ClearEntitiesCommand;
import com.example.simul2d.Systems.input.Commands.RectangleEntityCommand;
import com.example.simul2d.Systems.input.Commands.RectangleMaterialCommand;
import com.example.simul2d.Systems.input.Commands.RemoveEntityCommand;
import com.example.simul2d.Systems.input.Commands.SetMaterialCommand;
import com.example.simul2d.Systems.input.InputHandler;
import com.example.simul2d.grid.Cell;
import com.example.simul2d.grid.Grid;
import com.example.simul2d.grid.Material;
import com.example.simul2d.grid.Vec2;

import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class GridController implements NeedsSimulationState, NeedsUiState {

    // Reference to the shared simulation state (injected by HelloApplication)
    private SimulationState state;
    private UiState uiState;
    private Grid grid = null;
    private Rectangle[][] tiles; // 2D array to hold references to the grid cell rectangles


    private void clickOnCell(Vec2 position) {
        if (uiState.getActiveTool() == null) return;
        switch (uiState.getActiveTool()) {
            case DRAW -> {
                if (uiState.getMode() == ToolsType.ENTITY_MODE) {
                    if (uiState.getSelectedEntity() != null) {
                        InputHandler.COMMAND_QUEUE.add(new AddEntityCommand(position, uiState.getSelectedEntity()));
                    }
                } else if (uiState.getMode() == ToolsType.MATERIAL_MODE) {
                    if (uiState.getSelectedMaterial() != null) {
                        InputHandler.COMMAND_QUEUE.add(new SetMaterialCommand(position, uiState.getSelectedMaterial()));
                    }
                }
            }
            case ERASE -> {
                if (uiState.getMode() == ToolsType.ENTITY_MODE) {
                    if (uiState.getSelectedEntity() != null) {
                        InputHandler.COMMAND_QUEUE.add(new RemoveEntityCommand(position, uiState.getSelectedEntity()));
                    }
                }
                else if (uiState.getMode() == ToolsType.MATERIAL_MODE) {
                    if (uiState.getSelectedMaterial() != null) {
                        InputHandler.COMMAND_QUEUE.add(new SetMaterialCommand(position, Material.EMPTY));
                    }
                }
            }
            case CLEAR_ENTITIES -> {
                InputHandler.COMMAND_QUEUE.add(new ClearEntitiesCommand(position));
            }
            case RECTANGLE -> {
                if (uiState.getFirstClickPos() == null) {
                    uiState.setFirstClickPos(position);
                } else {
                    Vec2 start = uiState.getFirstClickPos();
                    Vec2 end = position;
                    if (uiState.getMode() == ToolsType.MATERIAL_MODE) {
                        if (uiState.getSelectedMaterial() != null) {
                            InputHandler.COMMAND_QUEUE.add(new RectangleMaterialCommand(start, end, uiState.getSelectedMaterial()));
                        }
                    }
                    else if (uiState.getMode() == ToolsType.ENTITY_MODE) {
                        if (uiState.getSelectedEntity() != null) {
                            InputHandler.COMMAND_QUEUE.add(new RectangleEntityCommand(start, end, uiState.getSelectedEntity()));
                        }
                    }
                    uiState.setFirstClickPos(null);
                }
            }
            default -> {
            }
        }

    }

    @FXML
    private GridPane gridDisplay;

    //
    @FXML
    private void initialize() {
        System.out.println("GridController initialized");
        installZoomHandler();
    }

    // Current zoom scale for the grid display
    private double gridScale = 1.0;
    private static final double GRID_SCALE_MIN = 0.2;
    private static final double GRID_SCALE_MAX = 6.0;

    private void installZoomHandler() {
        // zoom with scroll wheel when pointer is over gridDisplay
        gridDisplay.setOnScroll(e -> {
            if (e.isControlDown()) return; // allow ctrl+scroll for OS-level behavior if desired
            double delta = e.getDeltaY();
            // smooth exponential zooming
            double factor = Math.pow(1.0015, delta);
            double next = gridScale * factor;
            if (next < GRID_SCALE_MIN) factor = GRID_SCALE_MIN / gridScale;
            if (next > GRID_SCALE_MAX) factor = GRID_SCALE_MAX / gridScale;

            gridScale = gridScale * factor;
            gridDisplay.setScaleX(gridScale);
            gridDisplay.setScaleY(gridScale);

            e.consume();
        });
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

                tile.setOnMouseEntered(event -> {
                    if (uiState.isHoldClick()) {
                        clickOnCell(new Vec2(cx, cy));
                        if (uiState.getMode() == ToolsType.MATERIAL_MODE && uiState.getActiveTool() == ToolsType.DRAW) {
                            tile.setFill(Color.web(
                                    uiState.getSelectedMaterial().getColorHex()
                            ));
                        }
                    }
                });


                tile.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY){
                        uiState.changeHoldClick();
                    }
                    clickOnCell(new Vec2(cx, cy));
                    if (uiState.getMode() == ToolsType.MATERIAL_MODE && uiState.getActiveTool() == ToolsType.DRAW) {
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

        // Always read the current grid from the shared SimulationState so we don't
        // keep a stale reference when the grid is replaced (e.g., after loading).
        Grid current = (state == null) ? null : state.getGrid();
        if (current == null) {
            return;
        }

        // If the grid instance changed (load), reinitialize the display to match dimensions.
        if (this.grid != current) {
            this.grid = current;
            initializeGridDisplay();
        }

        if (tiles == null) return;

        int h = grid.getHeight();
        int w = grid.getWidth();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
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