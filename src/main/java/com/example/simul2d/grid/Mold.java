package com.example.simul2d.grid;
import java.util.Objects;

public abstract class Mold implements Grow {

    private int growth;
    private int growthRate;


    public Mold(int growth, int growthRate) {
        this.growth = growth;
        this.growthRate = growthRate;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public int getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(int growthRate) {
        this.growthRate = growthRate;
    }

    @Override
    public void grow() {

        if (growthRate <= 0) {
            return; // No growth if growth rate is zero or negative
        }

        if (growth >= 100) {
            return; // Maximum growth reached
        }

        // Default growth behavior (can be overridden by subclasses)
        this.growth += this.growthRate;
    }

    public void resetGrowth() {
        this.growth = 0;
    }

    @Override
    public boolean equals(Object other) {
        
        if (this == other) {
            return true; // Check if both references point to the same object
        }

        if (other == null || getClass() != other.getClass()) {
            return false; // Check if the other object is null or of a different class
        }

        Mold otherMold = (Mold) other; // Cast the other object to Mold
        return this.growth == otherMold.growth && this.growthRate == otherMold.growthRate; // Check if growth and growth rate are equal
    
    }

    @Override
    public int hashCode() {
        return Objects.hash(growth, growthRate); // Generate a hash code based on growth and growth rate
    }



}