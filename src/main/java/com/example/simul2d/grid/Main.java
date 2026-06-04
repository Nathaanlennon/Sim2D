package com.example.simul2d.grid;

public class Main {
    public static void main(String[] args) {

        Cell initialCell = new Cell();
        Mold slowMold = new FastMold();
        initialCell.addEntity(slowMold);

        initialCell.getEntities().forEach((type, entity) -> {
            System.out.println("Entity type: " + type);
            if (entity instanceof Mold mold) {
                System.out.println("Growth: " + mold.getGrowth());
                System.out.println("Growth Rate: " + mold.getGrowthRate());
                System.out.println("Can Propagate: " + mold.isAbleToPropagate());
                System.out.println("Propagation Distribution: " + mold.getPropagationDistributionList());
                System.out.println("Propagation Distribution Size: " + mold.getPropagationDistributionList().size());
            }
        });
    }
}
