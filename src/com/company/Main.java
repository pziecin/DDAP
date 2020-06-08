package com.company;

import com.company.alghoritm.BruteForce;
import com.company.filereader.OFMGraph;
import com.company.graph.Graph;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    private static String PATH = "C:/Users/lykan/Desktop/oast-dap/net4.txt";

    public static void main(String[] args) {
        OFMGraph rf = new OFMGraph();
        Graph graph;
        graph = rf.mapFileToGraph(PATH);
        graph.getCapacityOnLink().forEach(s-> System.out.println(s));

        BruteForce bruteForce = new BruteForce(graph);
        bruteForce.generateRoutingTable();
        bruteForce.printSolutions();
        bruteForce.solveDAP().forEach((key, value) -> {
            key.forEach(t -> System.out.println(Arrays.toString(t)));
            System.out.println("  " + value);
        });

        bruteForce.solveDDAP();



    }
}
