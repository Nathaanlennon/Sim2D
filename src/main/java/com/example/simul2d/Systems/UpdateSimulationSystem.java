package com.example.simul2d.Systems;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Entities.CanGrow;
import com.example.simul2d.Entities.CanPropagate;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.Entities.Entity;
import com.example.simul2d.JavaFX.NeedsGraphValues;
import com.example.simul2d.grid.Cell;
import com.example.simul2d.grid.Vec2;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Applies one simulation step to the current state.
 */
public class UpdateSimulationSystem {
    private final SimulationState data;
    private final PropagationSystem propagationSystem;
    private final CombatSystem combatSystem = new CombatSystem();
    private volatile List<NeedsGraphValues> graphicsUpdateCallbacks;
    private double timeBetweenGraphicsUpdates; // Update graphics every 5 simulation steps

    //constructors

    /**
     * Creates an updater bound to a simulation state.
     *
     * @param data the simulation state to update
     */
    public UpdateSimulationSystem(SimulationState data) {
        this.data = data;
        this.propagationSystem = new PropagationSystem(data);
        this.timeBetweenGraphicsUpdates = 5 * data.getSpeed();
        
    }

    //set methods
    public void setGraphicsUpdateCallbacks(List<NeedsGraphValues> graphicsUpdateCallbacks) {
        this.graphicsUpdateCallbacks = graphicsUpdateCallbacks;
    }
//get methods
//private methods
//public methods

    //TODO: change that to real input size

    /**
     * Placeholder hook for content-driven updates.
     *
     * @param content arbitrary content to process
     */
    public void contentUpdate(Object content) {

    }


    /**
     * Advances the simulation by one update tick.
     */
    public void update() {
        this.timeBetweenGraphicsUpdates = 5 * data.getSpeed();
        data.addTime(1);
        Map<Entities, Integer> entitiesGrowthCount;
        Map<Entities, Integer> infectedCells = new HashMap<>();

        if (data.getTime() % timeBetweenGraphicsUpdates == 0) {
            entitiesGrowthCount = new HashMap<>();
        } else {
            entitiesGrowthCount = null;
        }

        for (int y = 0; y < data.getGrid().getHeight(); y++) {
            for (int x = 0; x < data.getGrid().getWidth(); x++) {
                Cell cell = data.getGrid().getCell(x, y);
                Vec2 cellCoordinates = new Vec2(x, y);

                cell.step();



                cell.getEntities().values().forEach(entity -> {

                    if (data.getTime() % timeBetweenGraphicsUpdates == 0) {
                        if (entitiesGrowthCount != null) {
                            entitiesGrowthCount.put(entity.getEntityType(), entitiesGrowthCount.getOrDefault(entity.getEntityType(), 0) + entity.getGrowth()); // Update the growth count for this entity type if it exists
                        }
                        // if entity is in infectedCell, add +1 or just add the entity to the map :
                        infectedCells.put(entity.getEntityType(), infectedCells.getOrDefault(entity.getEntityType(), 0) + 1);
                    }

                    if (entity instanceof CanGrow) {
//                        ConsoleRenderSystem.printSomething("Growing on cell (" + finalX + ", " + finalY + ") with growth " + ((CanGrow) entity).getGrowth());
                    }

                    if (entity instanceof CanPropagate && ((CanPropagate) entity).isAbleToPropagate()) {
                        propagationSystem.propagation(cellCoordinates, (CanPropagate) entity);
                    }

                });
                
                
                
                combatSystem.inCellCombat(cell);


            }
        }
        if (data.getTime() % timeBetweenGraphicsUpdates == 0 && graphicsUpdateCallbacks != null && entitiesGrowthCount != null) {
            for (NeedsGraphValues callback : graphicsUpdateCallbacks) {
                Platform.runLater(() -> callback.graphStep(data.getTime(), new HashMap<>(entitiesGrowthCount), new HashMap<>(infectedCells)));
            }
        }
//override methods

    }
}