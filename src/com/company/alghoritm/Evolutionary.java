package com.company.alghoritm;

import com.company.graph.Demand;
import com.company.graph.Graph;

import java.util.*;
import java.util.stream.IntStream;

public class Evolutionary extends LinkLoads {
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


    public void startAlghoritm(){
        int i =0;
        while(i != 100){
            List<Chromosome> offspring = crossover();
            offspring = mutation(offspring);
            population.addAll(offspring);
            i++;
        }
    }

    public List<Chromosome> crossover(){
        List<Gene> offspringGenes = new LinkedList<>();
        List<Gene> offspringGenes2 = new LinkedList<>();
        Chromosome chromosome = population.get(rand(population.size()));
        Chromosome chromosome2 = population.get(rand(population.size()));
        for(int i = 0; i<chromosome.getGenesList().size(); i++){
            int fromWhichChromosome = rand(1);
            if(fromWhichChromosome == 0){
                offspringGenes.add(new Gene(chromosome.getGenesList().get(i)));
                offspringGenes2.add(new Gene(chromosome2.getGenesList().get(i)));
            }else {
                offspringGenes.add(new Gene(chromosome2.getGenesList().get(i)));
                offspringGenes2.add(new Gene(chromosome.getGenesList().get(i)));
            }
        }
        Chromosome offspring = new Chromosome(offspringGenes);
        Chromosome offspring2 = new Chromosome(offspringGenes2);
        List<Chromosome> childs = new LinkedList<>();
        childs.add(offspring);
        childs.add(offspring2);
        return childs;
    }

    public List<Chromosome> mutation(List<Chromosome> offspring){
        int probability = 99;
        int probabilityNominatior = 1;
        for(int i=0; i<offspring.size();i++){
            if(probabilityNominatior > rand(probability)){
                int geneNumberToMutate = rand(offspring.get(i).genesList.size());
                Gene gene = offspring.get(i).getGenesList().get(geneNumberToMutate);
                int volume = IntStream.of(gene.getAllocationPaterForDemand()).sum();
                for(int j = 0; j< gene.getGeneLenght(); j++){
                    if(j == gene.getGeneLenght()-1){
                        gene.setValue(j,volume);
                        continue;
                    }
                    int randomValue = rand(volume);
                    gene.setValue(i,randomValue);
                    volume = volume - randomValue;
                }
                offspring.get(i).getGenesList().set(geneNumberToMutate,gene);
            }else
                continue;
        }
        return offspring;
    }

    public void generatePopulation(int sizeOfPopulation){
        while(sizeOfPopulation != population.size()) {
            Chromosome chromosome = new Chromosome(generateGenesForChromosome());
            population.add(chromosome);
        }
//        calculateFitnessForDAP();
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

    public int rand(int number){
        Random rand = new Random();
        return rand.nextInt(number);
    }

    private void calculateFitnessForDAP(){
        List<int[]> linkLoadsList = new LinkedList<>();
        for (int i = 0; i < population.size(); i++) {
            List<int[]> conversionFromGene = new LinkedList<>();
            for(int j = 0; j< population.get(i).getGenesList().size(); j++) {
                conversionFromGene.add(population.get(i).getGenesList().get(j).getAllocationPaterForDemand());
            }
            linkLoadsList.add(linkLoads(graph, conversionFromGene));
        }
        List<Integer> fitnessList = new LinkedList<>();
        for (int i = 0; i < population.size(); i++) {
            List linkLoads = new ArrayList();
            for (int j=0; j<linkLoadsList.get(i).length; j++) {
                linkLoads.add(linkLoadsList.get(i)[j] - graph.getCapacityOnLink().get(j));
            }
            fitnessList.add((int) Collections.min(linkLoads));
            System.out.println("e");
        }
    }
//
//    public int[] linkLoads(List<int[]> table) {
////       int iter = test(table);
//        int[] link = new int[graph.getNumberOfLinks()];
//        for (int e = 0; e < graph.getNumberOfLinks(); e++) {
//            link[e] = 0;
//        }
//        for (int d = 0; d < graph.getDemandSize(); d++) {
//            for (int p = 0; p < graph.getDemandList().get(d).getDemandPathsListSize(); p++) {
//                for (int e = 0; e < graph.getNumberOfLinks(); e++) {
//                    if (isLinkInDemandPath(e + 1, d, p)) {    //e+1 Beacuse numeration of Links beginning from 1 (not from 0)
//                        link[e] = link[e] + table.get(d)[p];
//                    }
//                }
//            }
//        }
//
//        return link;
////        testAfterIteration(link, iter);
//    }
//
//    private boolean isLinkInDemandPath(int link, int demand, int path) {
//        for (Integer link_id : graph.getDemandList().get(demand).getDemandPaths().get(path)) {
//            if (link_id.intValue() == link) {
//                return true;
//            }
//        }
//        return false;
//    }

}
