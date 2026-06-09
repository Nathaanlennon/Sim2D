package com.example.simul2d;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.grid.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class HelloController implements NeedsSimulationState {

    @FXML private Canvas simulationCanvas;

    @FXML private Slider densitySlider;
    @FXML private Slider minPowerSlider;
    @FXML private Slider maxPowerSlider;

    private GraphicsContext gc;
    private SimulationState simulationState;
    private final int CELL_SIZE = 20;

    // STATES UNIFIÉS
    public enum ToolMode {
        CONCRETE, WOOD, PLASTER,
        CIRC_MOLD, AXIAL_MOLD, DIVIDED_MOLD,
        ERASE
    }
    private ToolMode currentTool = ToolMode.CIRC_MOLD;

    public enum InputMode { CLICK, BRUSH, ZONE }
    private InputMode currentInputMode = InputMode.CLICK;

    private boolean isDraggingZone = false;
    private int startCellX = -1, startCellY = -1, currentCellX = -1, currentCellY = -1;

    @FXML
    public void initialize() {
        gc = simulationCanvas.getGraphicsContext2D();
    }

    @Override
    public void setSimulationState(SimulationState state) {
        this.simulationState = state;
        drawGraphics();
    }

    @Override
    public void refreshUI() {
        drawGraphics();
    }

    // --- UI EVENT HANDLERS ---
    @FXML protected void setModeClick() { currentInputMode = InputMode.CLICK; }
    @FXML protected void setModeBrush() { currentInputMode = InputMode.BRUSH; }
    @FXML protected void setModeZone() { currentInputMode = InputMode.ZONE; }

    @FXML protected void setModeConcrete() { currentTool = ToolMode.CONCRETE; }
    @FXML protected void setModeWood() { currentTool = ToolMode.WOOD; }
    @FXML protected void setModePlaster() { currentTool = ToolMode.PLASTER; }

    @FXML protected void setModeCirc() { currentTool = ToolMode.CIRC_MOLD; }
    @FXML protected void setModeAxial() { currentTool = ToolMode.AXIAL_MOLD; }
    @FXML protected void setModeDivided() { currentTool = ToolMode.DIVIDED_MOLD; }
    @FXML protected void setModeErase() { currentTool = ToolMode.ERASE; }

    // --- MOUSE INPUT LOGIC ---
    @FXML protected void handleMousePressed(MouseEvent event) {
        if (simulationState == null) return;
        int x = (int) (event.getX() / CELL_SIZE);
        int y = (int) (event.getY() / CELL_SIZE);

        if (currentInputMode == InputMode.CLICK || currentInputMode == InputMode.BRUSH) {
            writeSingleCell(x, y);
        } else if (currentInputMode == InputMode.ZONE) {
            isDraggingZone = true;
            startCellX = x; startCellY = y;
            currentCellX = x; currentCellY = y;
        }
    }

    @FXML protected void handleMouseDragged(MouseEvent event) {
        if (simulationState == null) return;
        int x = (int) (event.getX() / CELL_SIZE);
        int y = (int) (event.getY() / CELL_SIZE);

        if (currentInputMode == InputMode.BRUSH) {
            writeSingleCell(x, y);
        } else if (currentInputMode == InputMode.ZONE && isDraggingZone) {
            currentCellX = x; currentCellY = y;
            drawGraphics();
        }
    }

    @FXML protected void handleMouseReleased(MouseEvent event) {
        if (currentInputMode == InputMode.ZONE && isDraggingZone) {
            fillZone();
            isDraggingZone = false;
            drawGraphics();
        }
    }

    //  DATA WRITING
    private void writeSingleCell(int x, int y) {
        int minD = (int) minPowerSlider.getValue();
        int maxD = (int) maxPowerSlider.getValue();
        int trueMin = Math.min(minD, maxD);
        int trueMax = Math.max(minD, maxD);

        Cell cell = simulationState.getGrid().getCell(x, y);
        if (cell != null) {
            applyToolToCell(cell, trueMin, trueMax);
            drawGraphics();
        }
    }

    private void fillZone() {
        int minX = Math.min(startCellX, currentCellX);
        int maxX = Math.max(startCellX, currentCellX);
        int minY = Math.min(startCellY, currentCellY);
        int maxY = Math.max(startCellY, currentCellY);

        double density = densitySlider.getValue() / 100.0;
        int minD = (int) minPowerSlider.getValue();
        int maxD = (int) maxPowerSlider.getValue();
        int trueMin = Math.min(minD, maxD);
        int trueMax = Math.max(minD, maxD);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (Math.random() <= density) {
                    Cell cell = simulationState.getGrid().getCell(x, y);
                    if (cell != null) {
                        applyToolToCell(cell, trueMin, trueMax);
                    }
                }
            }
        }
    }

    private void applyToolToCell(Cell cell, int minPower, int maxPower) {
        if (currentTool == ToolMode.ERASE) {
            cell.clearBiologicalState();
            return;
        }

        // LOGIQUE D'APPLICATION DES MATÉRIAUX
        if (currentTool == ToolMode.CONCRETE) {
            cell.setMaterial(Material.CONCRETE);
            cell.clearBiologicalState();
            return;
        }
        if (currentTool == ToolMode.WOOD) {
            cell.setMaterial(Material.WOOD);
            cell.clearBiologicalState();
            return;
        }
        if (currentTool == ToolMode.PLASTER) {
            cell.setMaterial(Material.PLASTER);
            cell.clearBiologicalState();
            return;
        }

        // LOGIQUE D'APPLICATION DE LA BIOLOGIE
        int generatedGrowth = minPower + (int)(Math.random() * ((maxPower - minPower) + 1));

        Mold newMold = null;
        switch (currentTool) {
            case CIRC_MOLD: newMold = new CircMold1(); break;
            case AXIAL_MOLD: newMold = new AxialMold1(); break;
            case DIVIDED_MOLD: newMold = new DividedMold1(); break;
        }

        if (newMold != null) {
            newMold.setGrowth(generatedGrowth);
            cell.addEntity(newMold);
        }
    }

    // --- RENDERING STRATEGY ---
    private void drawGraphics() {
        if (simulationState == null) return;
        gc.clearRect(0, 0, simulationCanvas.getWidth(), simulationCanvas.getHeight());

        Grid grid = simulationState.getGrid();

        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                Cell cell = grid.getCell(x, y);

                // DESSINER LE MATÉRIAU
                if (cell.getMaterial() != null) {
                    switch (cell.getMaterial()) {
                        case CONCRETE:  gc.setFill(Color.web("#556b7d")); break;
                        case WOOD:      gc.setFill(Color.web("#8b5a2b")); break;
                        case PLASTER:   gc.setFill(Color.web("#e6e6e6")); break;
                        case EMPTY:     gc.setFill(Color.web("#1e1e1e")); break;
                    }
                } else {
                    gc.setFill(Color.web("#1e1e1e"));
                }
                gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                // DESSINER LA MOISISSURE
                if (!cell.getEntities().isEmpty()) {
                    for (Entity entity : cell.getEntities().values()) {
                        if (entity instanceof Mold mold) {
                            double opacity = Math.max(0.1, mold.getGrowth() / 100.0);

                            if (mold instanceof CircMold1) { gc.setFill(Color.web("#00ffaa", opacity)); }
                            else if (mold instanceof AxialMold1) { gc.setFill(Color.web("#ff00aa", opacity)); }
                            else if (mold instanceof DividedMold1) { gc.setFill(Color.web("#00aaff", opacity)); }

                            gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                            break;
                        }
                    }
                }
            }
        }

        // Grille Vectorielle
        gc.setStroke(Color.web("#333333"));
        gc.setLineWidth(0.5);
        for (int x = 0; x <= grid.getWidth(); x++) { gc.strokeLine(x * CELL_SIZE, 0, x * CELL_SIZE, grid.getHeight() * CELL_SIZE); }
        for (int y = 0; y <= grid.getHeight(); y++) { gc.strokeLine(0, y * CELL_SIZE, grid.getWidth() * CELL_SIZE, y * CELL_SIZE); }

        // Bounding Box Visuelle
        if (isDraggingZone) {
            int minX = Math.min(startCellX, currentCellX);
            int maxX = Math.max(startCellX, currentCellX);
            int minY = Math.min(startCellY, currentCellY);
            int maxY = Math.max(startCellY, currentCellY);

            double rectX = minX * CELL_SIZE;
            double rectY = minY * CELL_SIZE;
            double rectW = (maxX - minX + 1) * CELL_SIZE;
            double rectH = (maxY - minY + 1) * CELL_SIZE;

            gc.setFill(Color.color(1, 1, 1, 0.2));
            gc.fillRect(rectX, rectY, rectW, rectH);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(2.0);
            gc.strokeRect(rectX, rectY, rectW, rectH);
        }
    }
}