package com.example.simul2d.Entities;

import com.example.simul2d.Entities.Mold.AxialMold1;
import com.example.simul2d.Entities.Mold.CircMold1;
import com.example.simul2d.Entities.Mold.DividedMold1;

public enum Entities {
    AXIAL_MOLD1,
    CIRC_MOLD1,
    DIVIDED_MOLD1;
    
    
    public Entity createEntity() {
        return switch (this) {
            case AXIAL_MOLD1 -> new AxialMold1();
            case CIRC_MOLD1 -> new CircMold1();
            case DIVIDED_MOLD1 -> new DividedMold1();
        };
    }
}
