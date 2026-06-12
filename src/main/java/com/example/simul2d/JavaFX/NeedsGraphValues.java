package com.example.simul2d.JavaFX;

import com.example.simul2d.Entities.Entities;

import java.util.Map;

public interface NeedsGraphValues {
    void addDataPoint(double timeStep, Map<Entities, Integer> populations);

}
