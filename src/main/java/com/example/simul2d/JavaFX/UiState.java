package com.example.simul2d.JavaFX;

import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Material;
import com.example.simul2d.grid.Vec2;

public class UiState {

    
    
    private ToolsType activeTool;
    private Material selectedMaterial;
    private Entities selectedEntity;
    private Vec2 firstClickPos = null;
    private ToolsType mode; // entity or material
    private boolean holdClick =  false;

    public boolean isHoldClick() {
        return holdClick;
    }
    public void changeHoldClick(boolean holdClick) {
        this.holdClick = holdClick;
    }
    public void changeHoldClick() {
        this.holdClick = !this.holdClick;
    }

    public ToolsType getMode() {
        return mode;
    }
    public void setMode(ToolsType mode) {
        this.mode = mode;
    }

    public ToolsType getActiveTool() {
        return activeTool;
    }

    public void setActiveTool(ToolsType activeTool) {
        this.activeTool = activeTool;
    }

    public Material getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(Material selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }
    public Vec2 getFirstClickPos() {
        return firstClickPos;
    }

    public void setFirstClickPos(Vec2 firstClickPos) {
        this.firstClickPos = firstClickPos;
    }

    public Entities getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(Entities selectedEntity) {
        this.selectedEntity = selectedEntity;
    }
    
    
}
