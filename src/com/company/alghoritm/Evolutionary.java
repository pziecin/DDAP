package com.company.alghoritm;

import com.company.graph.Demand;
import com.company.graph.Graph;
import org.w3c.dom.ls.LSOutput;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.IntStream;

public class Evolutionary extends LinkLoads {
    List<Chromosome> population;
    int sizeOfPopulation;
    int seed;
    Graph graph;

    private int mutationIterator;
    private int alghoritmIterator;
    private long startTime;

    public List<Chromosome> getPopulation() {
        return population;
    }

    public void setPopulation(List<Chromosome> population) {
        this.population = population;
    }

    public Evolutionary(Graph graph, int seed) {
        this.population = new LinkedList<>();
        this.graph = graph;
        this.seed = seed;
        this.mutationIterator = 0;
        this.alghoritmIterator = 0;
    }


    private boolean timerCondition(long time){
        long end = System.currentTimeMillis();
        long elapsedTime = end - startTime;
        if(time > elapsedTime && time != 0)
            return true;
        else
            return false;
    }

    public void alghoritmBody(boolean trueForDap){
        List<Chromosome> offspring = crossover();
//        offspring.forEach(s->s.getGenesList().forEach(e -> Arrays.toString(e.getAllocationPaterForDemand())));
        mutation(offspring);
        System.out.println(mutationIterator);
//        e(offspring);
        population.addAll(offspring);
        if(trueForDap == true)
            calculateFitnessForDAP();
        else
            calculateFitnessForDDAP();
    }

    private void e(List<Chromosome> offspring) {
        System.out.println(offspring.get(1).getGenesList());
        offspring.get(1).getGenesList().set(1,new Gene(1,2,3));
        System.out.println(offspring.get(1).getGenesList());
        System.out.println(offspring.get(2).getGenesList());
    }

    public void startAlghoritm(int iterations, int numberOfMutations, long time, boolean trueForDap){
        startTime = System.currentTimeMillis();
        while(iteratorCondition(iterations) || mutationCondition(numberOfMutations) || timerCondition(time)){
            alghoritmBody(trueForDap);
        }
    }

    private boolean iteratorCondition(int iterations){
        if(alghoritmIterator != iterations && iterations != 0) {
            alghoritmIterator++;
            return true;
        }
        else
            return false;
    }

    private boolean mutationCondition(int numberOfMutations){
        if(numberOfMutations > mutationIterator && numberOfMutations != 0)
            return true;
        else
            return false;
    }

    public List<Chromosome> crossover(){
        List<Chromosome> childs = new LinkedList<>();
        List<Chromosome> tmpPopulation = new LinkedList<>();
        tmpPopulation.addAll(population);
        for(int j=0;j<population.size()/2; j++) {
            List<Gene> offspringGenes = new LinkedList<>();
            List<Gene> offspringGenes2 = new LinkedList<>();
            int chromosomeToChose = rand(tmpPopulation.size());
            Chromosome chromosome = population.get(chromosomeToChose);
            tmpPopulation.remove(chromosomeToChose);
            Chromosome chromosome2 = population.get(rand(tmpPopulation.size()));
            for (int i = 0; i < chromosome.getGenesList().size(); i++) {
                int fromWhichChromosome = rand(1);
                if (fromWhichChromosome == 0) {
                    offspringGenes.add(new Gene(chromosome.getGenesList().get(i)));
                    offspringGenes2.add(new Gene(chromosome2.getGenesList().get(i)));
                } else {
                    offspringGenes.add(new Gene(chromosome2.getGenesList().get(i)));
                    offspringGenes2.add(new Gene(chromosome.getGenesList().get(i)));
                }
            }
            Chromosome offspring = new Chromosome(offspringGenes);
            Chromosome offspring2 = new Chromosome(offspringGenes2);
            childs.add(offspring);
            childs.add(offspring2);
        }
        return childs;
    }

    public void mutation(List<Chromosome> offspring){
        int probability = 10;
        int probabilityNominatior = 1;
//        System.out.println("OFFSPRING BEFORE");
//        offspring.forEach(s->s.getGenesList().forEach(e-> System.out.println(Arrays.toString(e.getAllocationPaterForDemand()))));
        for(int i=0; i<offspring.size();i++){
            if(probabilityNominatior > rand(probability)){
                int numbersFromWhichToRand = offspring.get(i).genesList.size();
                int geneNumberToMutate = rand(numbersFromWhichToRand);
//                Gene gene = new Gene(new int[offspring.get(i).getGenesList().get(geneNumberToMutate).getGeneLenght()]);
                Gene gene = offspring.get(i).getGenesList().get(geneNumberToMutate);
//                System.out.println("GENE TO MUTATE + chromosome nr: " + i + " gene to mutate: "+ geneNumberToMutate);
//                System.out.println(Arrays.toString(gene.getAllocationPaterForDemand()));
                int volume = IntStream.of(gene.getAllocationPaterForDemand()).sum();
                generateDemandFlowsForGene(gene, volume);
//                List<Gene> genesList = new ArrayList<>(offspring.get(i).getGenesList());
//                System.out.println("BEFORE");
//                genesList.forEach(s -> System.out.println(Arrays.toString(s.getAllocationPaterForDemand())));
//                System.out.println("GENE" + Arrays.toString(gene.getAllocationPaterForDemand()));
//                System.out.println("AFTER");
//                System.out.println("REF" + genesList);
//                genesList.forEach(s-> System.out.println(s));
//                genesList.forEach(s -> System.out.println(Arrays.toString(s.getAllocationPaterForDemand())));
                mutationIterator++;
            }else
                continue;
        }
//        System.out.println("OFFSPRING AFTER");
//        System.out.println("REF OFF" + offspring);
//        offspring.forEach(s-> s.getGenesList().forEach(g-> System.out.println(g)));
//        offspring.forEach(s->s.getGenesList().forEach(e-> System.out.println(Arrays.toString(e.getAllocationPaterForDemand()))));
    }

    private void generateDemandFlowsForGene(Gene gene, int volume) {
        for(int j = 0; j< gene.getGeneLenght(); j++){
            if(j == gene.getGeneLenght()-1){
                int e = gene.getGeneLenght()-1;
                gene.setValue(j,volume);
                continue;
            }
            int randomValue = rand(volume);
            gene.setValue(j,randomValue);
            volume = volume - randomValue;
        }
    }

    public void generatePopulation(int sizeOfPopulation){
        this.sizeOfPopulation = sizeOfPopulation;
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
            generateDemandFlowsForGene(gene, volume);
            chromosome.add(gene);
        }
        return chromosome;
    }

    public int rand(int number){
        Random rand = new Random();
        return rand.nextInt(number);
    }

    private void calculateFitnessForDAP() {
        List<int[]> linkLoadsList = initializeLinkLoadList();
        List<Integer> fitnessList = new LinkedList<>();
        for (int i = 0; i < population.size(); i++) {
            List linkLoads = new ArrayList();
            for (int j = 0; j < linkLoadsList.get(i).length; j++) {
                linkLoads.add(linkLoadsList.get(i)[j] - graph.getCapacityOnLink().get(j));
            }
            fitnessList.add((int) Collections.max(linkLoads));
        }

        while(population.size() != sizeOfPopulation){
            int tmp = -144;
            int indexToDelte = 0;
            for (int i = 0; i < fitnessList.size(); i++) {
                if (tmp < fitnessList.get(i).intValue()) {
                    tmp = fitnessList.get(i);
                    indexToDelte = i;
                }
            }
            fitnessList.remove(indexToDelte);
            population.remove(indexToDelte);
        }
        fitnessList.forEach(s -> System.out.print(s + " "));
        System.out.println();
    }

    private List<int[]> initializeLinkLoadList() {
        List<int[]> linkLoadsList = new LinkedList<>();
        for (int i = 0; i < population.size(); i++) {
            List<int[]> conversionFromGene = new LinkedList<>();
            for (int j = 0; j < population.get(i).getGenesList().size(); j++) {
                conversionFromGene.add(population.get(i).getGenesList().get(j).getAllocationPaterForDemand());
            }
            linkLoadsList.add(linkLoads(graph, conversionFromGene));
        }
        return linkLoadsList;
    }

    private void calculateFitnessForDDAP() {
        List<int[]> linkLoadsList = initializeLinkLoadList();
        List<Float> fitnessList = new LinkedList<>();
        for (int i = 0; i < population.size(); i++) {
            float cost =0;
            for (int j = 0; j < linkLoadsList.get(i).length; j++) {
                cost = cost + (float) linkLoadsList.get(i)[j]/ graph.getModularity().get(j)*graph.getFibrePairCost().get(j);
            }
            fitnessList.add(cost);
        }

        while(population.size() != sizeOfPopulation ){
            float tmp = 0;
            int indexToDelte = 0;
            for (int i = 0; i < fitnessList.size(); i++) {
                if (tmp < fitnessList.get(i).floatValue()) {
                    tmp = fitnessList.get(i).floatValue();
                    indexToDelte = i;
                }
            }
            fitnessList.remove(indexToDelte);
            population.remove(indexToDelte);
        }
        fitnessList.forEach(s -> System.out.print(s + " "));
        System.out.println();
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
