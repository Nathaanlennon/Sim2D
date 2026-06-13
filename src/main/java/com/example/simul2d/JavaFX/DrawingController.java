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

/**
 * Controller responsible for building and handling drawing/tool UI elements.
 *
 * <p>This controller populates material and entity selection buttons and
 * forwards user tool selections into the shared `UiState` model.
 */
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
    @FXML
    private Label mode;

    //set methods
//get methods
//private methods
    public void handleMaterialMode(){
        uiState.setMode(ToolsType.MATERIAL_MODE);
        mode.setText("Material");
    }
    public void handleEntityMode(){
        uiState.setMode(ToolsType.ENTITY_MODE);
        mode.setText("Entity");
    }
    public void handleErase() {
        this.uiState.setActiveTool(ToolsType.ERASE);
        this.currentToolLabel.setText("Selected Tool: Erase");
    }

    public void handleRectangleTool() {
        this.uiState.setActiveTool(ToolsType.RECTANGLE);
        this.currentToolLabel.setText("Selected Tool: Rectangle");
    }
    public void handleClearEntities() {
        this.uiState.setActiveTool(ToolsType.CLEAR_ENTITIES);
        this.currentToolLabel.setText("Selected Tool: Clear Entities");
    }
    public void handleDrawTool() {
        this.uiState.setActiveTool(ToolsType.DRAW);
        this.currentToolLabel.setText("Selected Tool: Draw");
    }
//public methods

    /**
     * initialize the tool buttons for materials and entities, setting up their event handlers to update the UiState accordingly.
     */
    @FXML
    public void initialize() {
        ConsoleRenderSystem.printSomething("DrawingController initialized");
        for (Material mat : Material.values()) {
            Button btn = new Button(mat.name());
            btn.setOnAction(e -> {
                this.uiState.setSelectedMaterial(mat);
                this.uiState.setMode(ToolsType.MATERIAL_MODE);
                this.mode.setText("Material");
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
                this.uiState.setMode(ToolsType.ENTITY_MODE);
                this.mode.setText("Entity");
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
