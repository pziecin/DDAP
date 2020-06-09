package com.company.alghoritm;

import java.util.List;

public class Chromosome {
    List<Gene> chromosome;

    public Chromosome(List<Gene> chromosome) {
        this.chromosome = chromosome;
    }

    public List<Gene> getChromosome() {
        return chromosome;
    }

    public void setChromosome(List<Gene> chromosome) {
        this.chromosome = chromosome;
    }
}
