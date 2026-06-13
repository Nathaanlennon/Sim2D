package com.example.sim2d.JavaFX;

import java.util.Map;

import com.example.simul2d.Entities.Entities;

import java.util.Map;

public interface NeedsGraphValues {
    void graphStep(double timeStep, Map<Entities, Integer> populationsWeight, Map<Entities, Integer> infectedCells);

}
