package com.example.simul2d.JavaFX;

import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Material;

public class UiState {

    
    
    private ToolsType activeTool;
    private Material selectedMaterial;
    private Entities selectedEntity;
    
    private int caca;
    
    public UiState(int caca) {
        this.caca = caca;
        
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

    public Entities getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(Entities selectedEntity) {
        this.selectedEntity = selectedEntity;
    }
    
    
}
