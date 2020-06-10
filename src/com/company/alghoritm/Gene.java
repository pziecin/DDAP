package com.company.alghoritm;

public class Gene {
    int[] allocationPaterForDemand;

    public Gene(int[] allocationPaterForDemand) {
        this.allocationPaterForDemand = allocationPaterForDemand;
    }

    public Gene(Gene gene){
        this.allocationPaterForDemand = gene.getAllocationPaterForDemand();
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
