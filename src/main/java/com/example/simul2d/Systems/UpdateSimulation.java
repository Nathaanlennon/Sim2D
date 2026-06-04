package com.example.simul2d.Systems;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.grid.Cell;
import com.example.simul2d.grid.Grow;
import com.example.simul2d.grid.Propagate;
import com.example.simul2d.render.Render;

/**
 * Applies one simulation step to the current state.
 */
public class UpdateSimulation {
    private final SimulationState data;
    
        //constructors
    /**
     * Creates an updater bound to a simulation state.
     *
     * @param data the simulation state to update
     */
    public UpdateSimulation(SimulationState data) {
        this.data = data;
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

        //liste de dérivation de coordonnées pour les 4 casses adjecancetes 
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int y = 0; y < data.getGrid().getHeight(); y++) {
            for (int x = 0; x < data.getGrid().getWidth(); x++) {
                Cell cell = data.getGrid().getCell(x, y);
                cell.step();
                int finalX = x;
                int finalY = y;
                cell.getEntities().values().forEach(entity -> {
                    if (entity instanceof Grow){
//                        Render.printSomething("Growing on cell (" + finalX + ", " + finalY + ") with growth " + ((Grow) entity).getGrowth());
                    }
                    
                    if (entity instanceof Propagate && ((Propagate) entity).isAbleToPropagate()) {
                        Cell target = null;
                        while (target == null) {
                            int[] dir = directions[(int) (Math.random() * directions.length)];
                            target = data.getGrid().getCell(finalX + dir[0], finalY + dir[1]);
                        }
//                        Render.printSomething("Propagating from (" + finalX + ", " + finalY + ") to (" + target.getPos());
                        ((Propagate) entity).propagateTo(target);
                    }
                });


            }
        }
//override methods

    }
}