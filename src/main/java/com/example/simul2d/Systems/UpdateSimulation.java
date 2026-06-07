package com.example.simul2d.Systems;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.grid.Cell;
import com.example.simul2d.Entities.Grow;
import com.example.simul2d.Entities.Propagate;
import com.example.simul2d.grid.Vec2;

/**
 * Applies one simulation step to the current state.
 */
public class UpdateSimulation {
    private final SimulationState data;
    private final PropagationSystem propagationSystem;
    
        //constructors
    /**
     * Creates an updater bound to a simulation state.
     *
     * @param data the simulation state to update
     */
    public UpdateSimulation(SimulationState data) {
        this.data = data;
        this.propagationSystem = new PropagationSystem(data);
    }

//set methods
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
        data.addTime(1);

        
        for (int y = 0; y < data.getGrid().getHeight(); y++) {
            for (int x = 0; x < data.getGrid().getWidth(); x++) {
                Cell cell = data.getGrid().getCell(x, y);
                Vec2 cellCoordinates = new Vec2(x, y);
                cell.step();
                cell.getEntities().values().forEach(entity -> {
                    if (entity instanceof Grow){
//                        Render.printSomething("Growing on cell (" + finalX + ", " + finalY + ") with growth " + ((Grow) entity).getGrowth());
                    }
                    
                    if (entity instanceof Propagate && ((Propagate) entity).isAbleToPropagate()) {
                        propagationSystem.propagation(cellCoordinates,(Propagate) entity);
                    }
                });


            }
        }
//override methods

    }
}