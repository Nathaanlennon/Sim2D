package com.example.simul2d.JavaFX;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.Systems.ConsoleRenderSystem;
import com.example.simul2d.grid.Material;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DrawingController implements NeedsUiState{

    private UiState uiState;

    @FXML
    private VBox buttonContainerMaterials;
    @FXML
    private VBox buttonContainerEntities;
    @FXML
    private Label currentToolLabel;
    @FXML
    private Label selected;

    //set methods
//get methods
//private methods
    public void handleEraseMaterials() {
        this.uiState.setActiveTool(ToolsType.ERASE_MATERIAL);
        this.currentToolLabel.setText("Selected Tool: Erase Material");
        this.uiState.setSelectedMaterial(null); 
        this.uiState.setSelectedEntity(null);
    }
    public void handleEraseEntities() {
        this.uiState.setActiveTool(ToolsType.ERASE_ENTITY);
        this.currentToolLabel.setText("Selected Tool: Erase Entity");
        this.uiState.setSelectedMaterial(null);
        this.uiState.setSelectedEntity(null);
    }
    public void handleRectangleTool() {
        this.uiState.setActiveTool(ToolsType.RECTANGLE);
        this.currentToolLabel.setText("Selected Tool: Rectangle");
    }
//public methods
    @FXML
    public void initialize() {
        ConsoleRenderSystem.printSomething("DrawingController initialized");
        for (Material mat : Material.values()) {
            Button btn = new Button(mat.name());
            btn.setOnAction(e -> {
                this.uiState.setSelectedMaterial(mat);
                this.uiState.setActiveTool(ToolsType.DRAW_MATERIAL);
                this.currentToolLabel.setText("Selected tool : draw material" );
                this.uiState.setSelectedEntity(null); // Clear selected entity when selecting a material
                this.selected.setText(mat.name());
            });
            btn.setText(mat.name());
            buttonContainerMaterials.getChildren().add(btn);
        }
        for (Entities ent : Entities.values()) {
            Button btn = new Button(ent.name());
            btn.setOnAction(e -> {
                this.uiState.setSelectedEntity(ent);
                this.uiState.setActiveTool(ToolsType.DRAW_ENTITY);
                this.currentToolLabel.setText("Selected tool : Draw entity" );
                this.uiState.setSelectedMaterial(null); // Clear selected material when selecting an entity
                this.selected.setText(ent.name());
            });
            btn.setText(ent.name());
            buttonContainerEntities.getChildren().add(btn);
        }
    }
//override methods
    @Override
    public void setUiState(UiState uiState) {
        ConsoleRenderSystem.printSomething("DrawingController received UiState");
        this.uiState = uiState;
        this.uiState.setActiveTool(null);
    }
    
}
