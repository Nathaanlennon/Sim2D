package com.example.simul2d;

import com.example.simul2d.grid.Grid;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class HelloController {
    @FXML
    private javafx.scene.control.Label analyticsLabel;
    @FXML
    private Canvas simulationCanvas;

    private GraphicsContext gc;
    private Grid data;

    private final int CELL_SIZE = 20;

    // STATES
    public enum ToolMode { CONCRETE, WOOD, PLASTER, GLASS, DIRT, INOCULATE, ERASE }
    private ToolMode currentTool = ToolMode.INOCULATE;

    public enum InputMode { CLICK, BRUSH, ZONE }
    private InputMode currentInputMode = InputMode.CLICK; // Click par défaut

    // ZONE TRACKING VARIABLES
    private boolean isDraggingZone = false;
    private int startCellX = -1;
    private int startCellY = -1;
    private int currentCellX = -1;
    private int currentCellY = -1;

    @FXML
    public void initialize() {
        data = new Grid(45, 45, false);
        gc = simulationCanvas.getGraphicsContext2D();

        int totalCells = data.columns * data.rows;
        String analyticsText = "GRID SIZE : " + data.columns + " x " + data.rows + "\n\n" +
                "TOTAL : " + totalCells + " cells";
        analyticsLabel.setText(analyticsText);

        drawGraphics();
    }

    // UI EVENT HANDLERS
    @FXML protected void setModeClick() { currentInputMode = InputMode.CLICK; } 
    @FXML protected void setModeBrush() { currentInputMode = InputMode.BRUSH; }
    @FXML protected void setModeZone() { currentInputMode = InputMode.ZONE; }

    @FXML protected void setModeConcrete() { currentTool = ToolMode.CONCRETE; }
    @FXML protected void setModeWood() { currentTool = ToolMode.WOOD; }
    @FXML protected void setModeDirt() { currentTool = ToolMode.DIRT; }
    @FXML protected void setModePlaster() { currentTool = ToolMode.PLASTER; }
    @FXML protected void setModeGlass() { currentTool = ToolMode.GLASS; }
    @FXML protected void setModeInoculate() { currentTool = ToolMode.INOCULATE; }
    @FXML protected void setModeErase() { currentTool = ToolMode.ERASE; }

    // PHASE 1: MOUSE INPUT LOGIC
    @FXML
    protected void handleMousePressed(MouseEvent event) {
        int x = (int) (event.getX() / CELL_SIZE);
        int y = (int) (event.getY() / CELL_SIZE);

        if (currentInputMode == InputMode.CLICK || currentInputMode == InputMode.BRUSH) {
            writeSingleCell(x, y);
        } else if (currentInputMode == InputMode.ZONE) {
            isDraggingZone = true;
            startCellX = x;
            startCellY = y;
            currentCellX = x;
            currentCellY = y;
            // No writing to memory yet, just visual tracking
        }
    }

    @FXML
    protected void handleMouseDragged(MouseEvent event) {
        int x = (int) (event.getX() / CELL_SIZE);
        int y = (int) (event.getY() / CELL_SIZE);

        if (currentInputMode == InputMode.BRUSH) {
            writeSingleCell(x, y);
        } else if (currentInputMode == InputMode.ZONE && isDraggingZone) {
            currentCellX = x;
            currentCellY = y;
            drawGraphics(); // Triggers the visual ghost box, without writing to memory
        }
    }

    @FXML
    protected void handleMouseReleased(MouseEvent event) {
        //  Validate the zone and write to memory
        if (currentInputMode == InputMode.ZONE && isDraggingZone) {
            fillZone();
            isDraggingZone = false;
            drawGraphics();
        }
    }

    // PHASE 2: MEMORY MUTATION (DATA WRITING)
    private void writeSingleCell(int x, int y) {
        Grid.CellData cell = data.getCell(x, y);
        if (cell != null) {
            applyToolToCell(cell);
            drawGraphics();
        }
    }

    private void fillZone() {
        // Robustness: Handle inverse dragging (bottom-right to top-left)
        int minX = Math.min(startCellX, currentCellX);
        int maxX = Math.max(startCellX, currentCellX);
        int minY = Math.min(startCellY, currentCellY);
        int maxY = Math.max(startCellY, currentCellY);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                Grid.CellData cell = data.getCell(x, y);
                if (cell != null) {
                    applyToolToCell(cell);
                }
            }
        }
    }

    private void applyToolToCell(Grid.CellData cell) {
        switch (currentTool) {
            case CONCRETE:
                cell.material = Grid.Material.CONCRETE;
                cell.mold = Grid.Mold.NONE;
                break;
            case DIRT:
                cell.material = Grid.Material.DIRT;
                cell.mold = Grid.Mold.NONE;
                break;
            case WOOD:
                cell.material = Grid.Material.WOOD;
                cell.mold = Grid.Mold.NONE;
                break;
            case PLASTER:
                cell.material = Grid.Material.PLASTER;
                cell.mold = Grid.Mold.NONE;
                break;
            case GLASS:
                cell.material = Grid.Material.GLASS;
                cell.mold = Grid.Mold.NONE;
                break;

            case INOCULATE:
                cell.mold = Grid.Mold.ACTIVE;
                break;
            case ERASE:
                cell.mold = Grid.Mold.NONE;
                break;
        }
    }

    // PHASE 3: RENDERING (READING FROM DATA)
    private void drawGraphics() {
        gc.clearRect(0, 0, simulationCanvas.getWidth(), simulationCanvas.getHeight());

        // Draw memory state
        for (int x = 0; x < data.columns; x++) {
            for (int y = 0; y < data.rows; y++) {
                Grid.CellData cell = data.matrix[x][y];

                if (cell.mold == Grid.Mold.ACTIVE) { gc.setFill(Color.web("#00ffaa")); }
                else if (cell.mold == Grid.Mold.SPAWNING) { gc.setFill(Color.web("#008855")); }
                else {
                    switch (cell.material) {
                        case CONCRETE:  gc.setFill(Color.web("#556b7d")); break;
                        case DIRT:      gc.setFill(Color.web("#522b4e")); break;
                        case WOOD:      gc.setFill(Color.web("#2b1d14")); break;
                        case PLASTER:   gc.setFill(Color.web("#e6e6e6")); break;
                        case GLASS:     gc.setFill(Color.web("#e8f4f8")); break;
                    }
                }
                gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // draw blueprint grid overlay
        gc.setStroke(Color.web("#b0c4de"));
        gc.setLineWidth(0.5);
        for (int x = 0; x <= data.columns; x++) {
            gc.strokeLine(x * CELL_SIZE, 0, x * CELL_SIZE, data.rows * CELL_SIZE);
        }
        for (int y = 0; y <= data.rows; y++) {
            gc.strokeLine(0, y * CELL_SIZE, data.columns * CELL_SIZE, y * CELL_SIZE);
        }

        // draw ghost box if currently dragging a zone
        if (isDraggingZone) {
            int minX = Math.min(startCellX, currentCellX);
            int maxX = Math.max(startCellX, currentCellX);
            int minY = Math.min(startCellY, currentCellY);
            int maxY = Math.max(startCellY, currentCellY);

            // calculate width and height in pixels (+1 to include the end cell)
            double rectX = minX * CELL_SIZE;
            double rectY = minY * CELL_SIZE;
            double rectW = (maxX - minX + 1) * CELL_SIZE;
            double rectH = (maxY - minY + 1) * CELL_SIZE;

            // semi-transparent overlay to show the selection
            gc.setFill(Color.color(1, 1, 1, 0.2));
            gc.fillRect(rectX, rectY, rectW, rectH);

            //  border for the selection box
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(5.0);
            gc.strokeRect(rectX, rectY, rectW, rectH);
        }
    }
}
