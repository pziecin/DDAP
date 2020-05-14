package com.company;

import com.company.filereader.OFMGraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static String PATH = "C:/Users/lykan/Desktop/oast-dap/net4.txt" ;

    public static void main(String[] args) {
        OFMGraph rf = new OFMGraph();
        rf.loadFile(PATH);
//        System.out.println("Podaj liczbę wierzchołków \n");
//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();
//        System.out.println("Podaj maksymalna wagę krawedzi \n");
//        int maxWaga = sc.nextInt();
//        Graph graf = new Graph(new Edge[n][n]);
//        graf.createRandomGraph(maxWaga);
//        graf.readEdgeWeight();
    }
}
