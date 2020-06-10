package com.company;

import com.company.alghoritm.BruteForce;
import com.company.alghoritm.Evolutionary;
import com.company.filereader.OFMGraph;
import com.company.graph.Graph;

import java.util.Arrays;

public class Main {
    private static String PATH = "C:/Users/lykan/Desktop/oast-dap/net4.txt";

    public static void main(String[] args) {
        OFMGraph rf = new OFMGraph();
        Graph graph;
        graph = rf.mapFileToGraph(PATH);
        graph.getCapacityOnLink().forEach(s-> System.out.println(s));

//        bruteForce(graph);
        evolutionary(graph);

    }

    private static void bruteForce(Graph graph) {
        BruteForce bruteForce = new BruteForce(graph);
        bruteForce.generateRoutingTable();
        bruteForce.printSolutions();
        bruteForce.solveDAP().forEach((key, value) -> {
            key.forEach(t -> System.out.println(Arrays.toString(t)));
            System.out.println("  " + value);
        });
        bruteForce.solveDDAP();
    }

    private static void evolutionary(Graph graph){
        Evolutionary evolutionary = new Evolutionary(graph);
        evolutionary.generatePopulation(10);
        evolutionary.startAlghoritm();
        evolutionary.getPopulation().forEach(chromosome -> {chromosome.getGenesList().forEach(gene -> System.out.println(Arrays.toString(gene.getAllocationPaterForDemand())));
            System.out.println();
        });
        System.out.println(evolutionary.getPopulation().size());
    }
}
