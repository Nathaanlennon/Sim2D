package com.example.simul2d.Systems;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Entities.Propagate;
import com.example.simul2d.Entities.Mold.CoorWeight;
import com.example.simul2d.grid.Cell;
import com.example.simul2d.grid.Vec2;

import java.util.List;

public class PropagationSystem {
    private final SimulationState data;

    //constructors
    public PropagationSystem(SimulationState data) {
        this.data = data;
    }

//set methods
//get methods
//private methods

    /**
     * Selects a propagation target from a list of candidates, weighted by their respective weights.
     *
     * @param targets a list of candidate targets with associated weights
     * @return the coordinates of the selected target for propagation
     */
    private Vec2 getPropagationTarget(List<CoorWeight> targets) {
        if (targets == null || targets.isEmpty()) {
            return null; // No targets available
        }
        
        double totalWeight = 0.0;
        for (CoorWeight target : targets) {
            totalWeight += target.getWeight();
        }

        // Generate a random value between 0 and the total weight, then for eacgh target, subtract its weight from 
        // the random value until it becomes zero or negative, at which point we select that target.
        // basically, we turn the weights into an interval
        double randomValue = Math.random() * totalWeight;
        for (CoorWeight target : targets) {
            randomValue -= target.getWeight();
            if (randomValue <= 0) {
                return target.getCoordinates();
            }
        }
        return null; // This should never happen if the input list is valid (non-empty and positive weights)
    }
    
    private boolean shouldPropagate(Propagate propagate, Cell target){
        if (propagate == null || target == null) {
            return false; // Invalid input
        }
        double propagationProbability = propagate.getPropagationProbability();
        double cellVulnerability = target.getMaterial().getVulnerability();
        return Math.random() < propagationProbability * cellVulnerability; // Simple probabilistic check
    }

    //public methods
    public void propagation(Vec2 OGCoordinates, Propagate propagate) {
        if (propagate != null && propagate.isAbleToPropagate()) {
            Vec2 targetCoordinates = getPropagationTarget(propagate.getPropagationDistributionList());
            if (targetCoordinates != null) {
                Cell target = data.getGrid().getCell(OGCoordinates.add(targetCoordinates));
                if (target != null) {
                    if (shouldPropagate(propagate, target)) {
                        propagate.propagateTo(target);
                    }
                }
            }
        }
    }
//override methods

}
