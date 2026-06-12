package com.example.simul2d.JavaFX;

import com.example.simul2d.Entities.Entities;
import com.example.simul2d.Systems.ConsoleRenderSystem;
import com.example.simul2d.grid.Material;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
            btn.setPrefWidth(110);
            btn.setMaxWidth(110);

            Region colorBox = new Region();
            colorBox.setPrefSize(16, 16);
            colorBox.setStyle("-fx-background-color: " + mat.getColorHex() + "; -fx-border-radius: 3; -fx-background-radius: 3;");

            HBox item = new HBox(8, colorBox, btn);
            item.setMaxWidth(140);
            item.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            VBox.setMargin(item, new Insets(4,0,4,0));
            buttonContainerMaterials.getChildren().add(item);
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
            btn.setPrefWidth(110);
            btn.setMaxWidth(110);

            Region colorBox = new Region();
            colorBox.setPrefSize(16, 16);
            colorBox.setStyle("-fx-background-color: " + ent.getColorHex() + "; -fx-border-radius: 3; -fx-background-radius: 3;");

            HBox item = new HBox(8, colorBox, btn);
            item.setMaxWidth(140);
            item.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            VBox.setMargin(item, new Insets(4,0,4,0));
            buttonContainerEntities.getChildren().add(item);
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
