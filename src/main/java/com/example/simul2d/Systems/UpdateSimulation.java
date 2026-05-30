package com.example.simul2d.Systems;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.grid.Cell;
import com.example.simul2d.grid.Propagate;

public class UpdateSimulation {
    private final SimulationState data;

    //constructors
    public UpdateSimulation(SimulationState data) {
        this.data = data;
    }

//set methods
//get methods
//private methods
//public methods

    //TODO: change that to real input
    public void contentUpdate(Object content) {

    }


    public void update() {
        data.addTime(data.getSpeed());

        //liste de dérivation de coordonnées pour les 4 casses adjecancetes 
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int y = 0; y < data.getGrid().getHeight(); y++) {
            for (int x = 0; x < data.getGrid().getWidth(); x++) {
                Cell cell = data.getGrid().getCell(x, y);
                cell.step();
                int finalX = x;
                int finalY = y;
                cell.getEntities().values().forEach(entity ->  {
                    if (entity instanceof Propagate && ((Propagate) entity).isAbleToPropagate()){
                        int[] dir = directions[(int) (Math.random() * directions.length)];
                        ((Propagate) entity).propagateTo(data.getGrid().getCell(finalX + dir[0], finalY + dir[1]));
                    }
                });
                

            }
        }
//override methods

    }
}