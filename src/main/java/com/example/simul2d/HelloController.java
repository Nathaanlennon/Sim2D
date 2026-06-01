package com.example.simul2d;

import com.example.simul2d.grid.GridData;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class HelloController {

    @FXML
    private Canvas simulationCanvas;

    private GraphicsContext gc;
    private GridData data;
    private final int CELL_SIZE = 5;

    // 6 ÉTATS (Matériaux + Actions)
    public enum ModeOutil { BETON, BOIS, PLATRE, VERRE,CACA, INOCULER, EFFACER }
    private ModeOutil outilActuel = ModeOutil.INOCULER;

    @FXML
    public void initialize() {
        data = new GridData(176, 144, false);
        gc = simulationCanvas.getGraphicsContext2D();
        drawGraphics();
    }

    // CONNEXIONS AVEC LES BOUTONS FXML
    @FXML protected void setModeBeton() { outilActuel = ModeOutil.BETON; }
    @FXML protected void setModeBois() { outilActuel = ModeOutil.BOIS; }
    @FXML protected void setModeCACA() { outilActuel = ModeOutil.CACA; }
    @FXML protected void setModePlatre() { outilActuel = ModeOutil.PLATRE; }
    @FXML protected void setModeVerre() { outilActuel = ModeOutil.VERRE; }
    @FXML protected void setModeInoculer() { outilActuel = ModeOutil.INOCULER; }
    @FXML protected void setModeEffacer() { outilActuel = ModeOutil.EFFACER; }

    // PHASE 1 INPUT (ÉCRITURE DANS LA DATA)
    @FXML protected void handleMousePressed(MouseEvent event) { ecrireDansData(event); }
    @FXML protected void handleMouseDragged(MouseEvent event) { ecrireDansData(event); }

    private void ecrireDansData(MouseEvent event) {
        int x = (int) (event.getX() / CELL_SIZE);
        int y = (int) (event.getY() / CELL_SIZE);
        GridData.CellData cell = data.getCell(x, y);

        if (cell != null) {
            cell.caca = cell.caca+1;
            switch (outilActuel) {
                case BETON:
                    cell.materiau = GridData.Materiau.BETON;
                    break;
                case CACA:
                    cell.materiau = GridData.Materiau.CACA;
                    break;
                case BOIS:
                    cell.materiau = GridData.Materiau.BOIS;
                    break;
                case PLATRE:
                    cell.materiau = GridData.Materiau.PLATRE;
                    break;
                case VERRE:
                    cell.materiau = GridData.Materiau.VERRE;
                    break;


                case INOCULER:
                    cell.moisissure = GridData.Moisissure.ACTIVE;
                    break;
                case EFFACER:
                    cell.moisissure = GridData.Moisissure.AUCUNE;
                    break;
            }
        }
        drawGraphics();
    }

    // Partie GRAPHIQUE (LECTURE DE LA DATA)
    private void drawGraphics() {
        for (int x = 0; x < data.columns; x++) {
            for (int y = 0; y < data.rows; y++) {

                GridData.CellData cell = data.matrice[x][y];

                // Affiche la moisissure
                if (cell.moisissure == GridData.Moisissure.ACTIVE) {
                    gc.setFill(Color.web("#00ffaa")); 
                }
                else if (cell.moisissure == GridData.Moisissure.NAISSANTE) {
                    gc.setFill(Color.web("#008855")); 
                }
  
                else {
                    switch (cell.materiau) {
                        case BETON:  gc.setFill(Color.web("#556b7d")); break;
                        case CACA:  gc.setFill(Color.web("#522b4e")); break;
                        case BOIS:   gc.setFill(Color.web("#2b1d14")); break; 
                        case PLATRE: gc.setFill(Color.web("#e6e6e6")); break;
                        case VERRE:  gc.setFill(Color.web("#87cefa")); break;
                    }
                }
                gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }
}
