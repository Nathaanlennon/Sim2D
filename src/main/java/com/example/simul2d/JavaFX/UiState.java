package com.example.simul2d.JavaFX;

import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Material;
import com.example.simul2d.grid.Vec2;

/**
 * Holds the mutable UI state shared between the simulation canvas and toolbars.
 * <p>
 * This includes the active tool, selected material or entity, the position of
 * the first click for area operations, the current editing mode (entity or
 * material), and whether a continuous click-and-drag operation is in progress.
 * </p>
 */
public class UiState {

    /** The tool currently active (e.g., pencil, eraser, fill). */
    private ToolsType activeTool;

    /** The material selected for painting operations. */
    private Material selectedMaterial;

    /** The entity type selected for placement operations. */
    private Entities selectedEntity;

    /** The grid position of the first mouse click during an area operation (may be {@code null}). */
    private Vec2 firstClickPos = null;

    /**
     * The current editing mode: either entity placement or material painting.
     * @see ToolsType
     */
    private ToolsType mode;

    /** Whether a click-and-drag (hold) operation is currently active. */
    private boolean holdClick = false;

    /**
     * Returns whether a continuous click operation is in progress.
     *
     * @return {@code true} if the mouse button is being held down after a click
     */
    public boolean isHoldClick() {
        return holdClick;
    }

    /**
     * Sets the hold-click state.
     *
     * @param holdClick the new state
     */
    public void changeHoldClick(boolean holdClick) {
        this.holdClick = holdClick;
    }

    /**
     * Toggles the hold-click state.
     */
    public void changeHoldClick() {
        this.holdClick = !this.holdClick;
    }

    /**
     * Returns the current editing mode.
     *
     * @return the mode (entity or material)
     */
    public ToolsType getMode() {
        return mode;
    }

    /**
     * Sets the editing mode.
     *
     * @param mode the new mode
     */
    public void setMode(ToolsType mode) {
        this.mode = mode;
    }

    /**
     * Returns the currently active tool.
     *
     * @return the active tool
     */
    public ToolsType getActiveTool() {
        return activeTool;
    }

    /**
     * Sets the active tool.
     *
     * @param activeTool the tool to activate
     */
    public void setActiveTool(ToolsType activeTool) {
        this.activeTool = activeTool;
    }

    /**
     * Returns the currently selected material.
     *
     * @return the selected material, or {@code null} if none
     */
    public Material getSelectedMaterial() {
        return selectedMaterial;
    }

    /**
     * Sets the selected material.
     *
     * @param selectedMaterial the material to select
     */
    public void setSelectedMaterial(Material selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    /**
     * Returns the position of the first mouse click during an area operation.
     *
     * @return the grid position, or {@code null} if no click has been recorded
     */
    public Vec2 getFirstClickPos() {
        return firstClickPos;
    }

    /**
     * Sets the position of the first click.
     *
     * @param firstClickPos the grid position of the first click
     */
    public void setFirstClickPos(Vec2 firstClickPos) {
        this.firstClickPos = firstClickPos;
    }

    /**
     * Returns the currently selected entity type.
     *
     * @return the selected entity, or {@code null} if none
     */
    public Entities getSelectedEntity() {
        return selectedEntity;
    }

    /**
     * Sets the selected entity type.
     *
     * @param selectedEntity the entity to select
     */
    public void setSelectedEntity(Entities selectedEntity) {
        this.selectedEntity = selectedEntity;
    }
}