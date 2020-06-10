package com.company.alghoritm;

import java.util.List;

public class Chromosome {
    List<Gene> genesList;

    public Chromosome(List<Gene> chromosome) {
        this.genesList = chromosome;
    }

    public List<Gene> getGenesList() {
        return genesList;
    }

    public void setGenesList(List<Gene> genesList) {
        this.genesList = genesList;
    }
}
