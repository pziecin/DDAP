package com.company;

import com.company.alghoritm.BruteForce;
import com.company.alghoritm.Evolutionary;
import com.company.filereader.OFMGraph;
import com.company.graph.Graph;

import java.util.*;

public class Main {
    private static String PATH = "C:/Users/lykan/Desktop/oast-dap/net4.txt";

    public static void main(String[] args) {
        int seed = 0;
        OFMGraph rf = new OFMGraph();
        Graph graph;
        graph = rf.mapFileToGraph(PATH);
        graph.getCapacityOnLink().forEach(s-> System.out.println(s));

//        bruteForce(graph);
        evolutionary(graph, seed);
    }

    private static void bruteForce(Graph graph) {
        BruteForce bruteForce = new BruteForce(graph);
        bruteForce.generateRoutingTable();
        bruteForce.printSolutions();
//        bruteForce.solveDAP().forEach((key, value) -> {
//            key.forEach(t -> System.out.println(Arrays.toString(t)));
//            System.out.println("  " + value);
//        });
//        System.out.println("MIN VALUE " + Collections.min(bruteForce.solveDDAP().values()));
        Map<List<int[]>, Integer> e = bruteForce.solveDAP();
        Map.Entry<List<int[]>, Integer> min = null;
        for (Map.Entry<List<int[]>, Integer> ee : e.entrySet()) {
            if (min == null || min.getValue() > ee.getValue()) {
                min = ee;
            }
        }
        min.getKey().forEach(s-> System.out.println(Arrays.toString(s)));
        System.out.println(min.getValue());

        for(Map.Entry<List<int[]>, int[]> entry : bruteForce.getLinkLoadsList().entrySet()){
           if(17 == test(entry.getKey())){
               System.out.println(Arrays.toString(entry.getValue()));
           }
        }
    }

    private static int test(List<int[]> table) {
        int[][] TABL = {{3, 0, 0}, {4, 0, 0}, {0, 5}, {2, 0, 0}, {3, 0, 0}, {4, 0, 0}};
        int iter = 0;
        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.get(i).length; j++) {
                if (table.get(i)[j] == TABL[i][j]) {
                    iter++;
                }
            }
        }
        return iter;
    }

    private static void evolutionary(Graph graph, int seed){
        Evolutionary evolutionary = new Evolutionary(graph, seed);
        evolutionary.generatePopulation(100);
        evolutionary.startAlghoritm(1000,0,0,false);
//        evolutionary.getPopulation().forEach(chromosome -> {chromosome.getGenesList().forEach(gene -> System.out.println(Arrays.toString(gene.getAllocationPaterForDemand())));
//            System.out.println();
//        });
//        System.out.println();
//        System.out.println(evolutionary.getPopulation().size());
    }
}
