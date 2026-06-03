package com.example.simul2d;

import com.example.simul2d.grid.GridData;
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
    private GridData data;

    private final int CELL_SIZE = 20;

    // 7 STATES (Materials + Actions)
    public enum ToolMode { CONCRETE, WOOD, PLASTER, GLASS, DIRT, INOCULATE, ERASE }
    private ToolMode currentTool = ToolMode.INOCULATE;

    @FXML
    public void initialize() {
        data = new GridData(45, 45, false);
        gc = simulationCanvas.getGraphicsContext2D();

        int totalCells = data.columns * data.rows;
        String analyticsText = "GRID SIZE : " + data.columns + " x " + data.rows + "\n\n" +
                "TOTAL : " + totalCells + " cells";
        analyticsLabel.setText(analyticsText);

        drawGraphics();
    }

    // CONNECTIONS WITH FXML BUTTONS ---
    @FXML protected void setModeConcrete() { currentTool = ToolMode.CONCRETE; }
    @FXML protected void setModeWood() { currentTool = ToolMode.WOOD; }
    @FXML protected void setModeDirt() { currentTool = ToolMode.DIRT; }
    @FXML protected void setModePlaster() { currentTool = ToolMode.PLASTER; }
    @FXML protected void setModeGlass() { currentTool = ToolMode.GLASS; }
    @FXML protected void setModeInoculate() { currentTool = ToolMode.INOCULATE; }
    @FXML protected void setModeErase() { currentTool = ToolMode.ERASE; }

    // PHASE 1 INPUT (WRITING TO DATA)
    @FXML protected void handleMousePressed(MouseEvent event) { writeToData(event); }
    @FXML protected void handleMouseDragged(MouseEvent event) { writeToData(event); }

    private void writeToData(MouseEvent event) {
        int x = (int) (event.getX() / CELL_SIZE);
        int y = (int) (event.getY() / CELL_SIZE);
        GridData.CellData cell = data.getCell(x, y);

        if (cell != null) {
            cell.dirt = cell.dirt + 1;
            switch (currentTool) {
                case CONCRETE:
                    cell.material = GridData.Material.CONCRETE;
                    break;
                case DIRT:
                    cell.material = GridData.Material.DIRT;
                    break;
                case WOOD:
                    cell.material = GridData.Material.WOOD;
                    break;
                case PLASTER:
                    cell.material = GridData.Material.PLASTER;
                    break;
                case GLASS:
                    cell.material = GridData.Material.GLASS;
                    break;

                // Painting Mold
                case INOCULATE:
                    cell.mold = GridData.Mold.ACTIVE;
                    break;
                case ERASE:
                    cell.mold = GridData.Mold.NONE;
                    break;
            }
        }
        drawGraphics();
    }

    // GRAPHICS PART (READING FROM DATA)
    private void drawGraphics() {
        // Absolute Canvas cleanup
        gc.clearRect(0, 0, simulationCanvas.getWidth(), simulationCanvas.getHeight());

        for (int x = 0; x < data.columns; x++) {
            for (int y = 0; y < data.rows; y++) {

                GridData.CellData cell = data.matrix[x][y];

                // Display mold
                if (cell.mold == GridData.Mold.ACTIVE) {
                    gc.setFill(Color.web("#00ffaa")); 
                }
                else if (cell.mold == GridData.Mold.SPAWNING) {
                    gc.setFill(Color.web("#008855"));
                }
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

        gc.setStroke(Color.web("#b0c4de")); 
        gc.setLineWidth(0.5);

        for (int x = 0; x <= data.columns; x++) {
            gc.strokeLine(x * CELL_SIZE, 0, x * CELL_SIZE, data.rows * CELL_SIZE);
        }
        for (int y = 0; y <= data.rows; y++) {
            gc.strokeLine(0, y * CELL_SIZE, data.columns * CELL_SIZE, y * CELL_SIZE);
        }
    }
}
