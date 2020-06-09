package com.company.alghoritm;

import com.company.graph.Demand;
import com.company.graph.Graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Evolutionary {
    List<Chromosome> population;
    Graph graph;

    public List<Chromosome> getPopulation() {
        return population;
    }

    public void setPopulation(List<Chromosome> population) {
        this.population = population;
    }

    public Evolutionary(Graph graph) {
        this.population = new LinkedList<>();
        this.graph = graph;
    }

    public void generatePopulation(int sizeOfPopulation){
        while(sizeOfPopulation != population.size()) {
            Chromosome chromosome = new Chromosome(generateGenesForChromosome());
            population.add(chromosome);
        }
    }

    private List<Gene> generateGenesForChromosome(){
        List<Gene> chromosome = new LinkedList<>();
        for(int demandColumn = 0; demandColumn < graph.getDemandSize(); demandColumn++) {
            Demand demand = graph.getDemandList().get(demandColumn);
            int allocationPaternSize = demand.getDemandPathsListSize();
            Gene gene = new Gene(new int[allocationPaternSize]);
            int volume = demand.getVolume();
            for(int i = 0; i< gene.getGeneLenght(); i++){
                if(i == gene.getGeneLenght()-1){
                    gene.setValue(i,volume);
                    continue;
                }
                int randomValue = rand(volume);
                gene.setValue(i,randomValue);
                volume = volume - randomValue;
            }
            chromosome.add(gene);
        }
        return chromosome;
    }

    public int rand(int volume){
        Random rand = new Random();
        return rand.nextInt(volume);
    }

}
