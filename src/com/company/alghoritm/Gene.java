package com.company.alghoritm;

public class Gene {
    int[] allocationPaterForDemand;

    public Gene(int[] allocationPaterForDemand) {
        this.allocationPaterForDemand = allocationPaterForDemand;
    }

    public Gene(int a, int b, int c) {
        this.allocationPaterForDemand = new int[]{a,b,c};
    }

    public Gene(Gene gene){
        this.allocationPaterForDemand = new int[gene.getAllocationPaterForDemand().length];
        for(int i = 0; i < gene.getAllocationPaterForDemand().length; i++){
            this.allocationPaterForDemand[i] = gene.getAllocationPaterForDemand()[i];
        }
    }

    public int[] getAllocationPaterForDemand() {
        return allocationPaterForDemand;
    }

    public void setAllocationPaterForDemand(int[] allocationPaterForDemand) {
        this.allocationPaterForDemand = allocationPaterForDemand;
    }

    public int getGeneLenght(){
        return allocationPaterForDemand.length;
    }

    public void setValue(int column, int value){
        if (allocationPaterForDemand!=null)
            this.allocationPaterForDemand[column] = value;
    }
}
