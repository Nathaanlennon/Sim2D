package com.example.simul2d.Systems;

import java.util.List;

import com.example.simul2d.Core.SimulationState;
import com.example.simul2d.Entities.CanPropagate;
import com.example.simul2d.Entities.CoorWeight;
import com.example.simul2d.grid.Cell;
import com.example.simul2d.grid.Vec2;
//TODO: add growth to propagation probability calcul
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
    
    private boolean shouldPropagate(CanPropagate canPropagate, Cell target){
        if (canPropagate == null || target == null) {
            return false; // Invalid input
        }
        double propagationProbability = canPropagate.getPropagationProbability();
        double cellVulnerability = target.getMaterial().getVulnerability();
        return Math.random() < propagationProbability * cellVulnerability; // Simple probabilistic check
    }

    //public methods
    public void propagation(Vec2 OGCoordinates, CanPropagate canPropagate) {
        if (canPropagate != null && canPropagate.isAbleToPropagate()) {
            Vec2 targetCoordinates = getPropagationTarget(canPropagate.getPropagationDistributionList());
            if (targetCoordinates != null) {
                Cell target = data.getGrid().getCell(OGCoordinates.add(targetCoordinates));
                if (target != null) {
                    if (shouldPropagate(canPropagate, target)) {
                        canPropagate.propagateTo(target);
                    }
                }
            }
        }
    }
//override methods

}
