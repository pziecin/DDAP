package com.company;

import com.company.alghoritm.BruteForce;
import com.company.alghoritm.Evolutionary;
import com.company.filereader.OFMGraph;
import com.company.graph.Graph;

import java.util.*;

public class Main {
    private static String PATH = "../net4.txt";

    public static void main(String[] args) {
        int seed = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Podaj sciezke do grafu według tego wzoru jeżeli plik teksotwy z grafem znajduje się w tym folderze: \n schemat: ../nazwaPliku.txt \n przykład: ../net4.txt \n Wpisz tutaj...");
        String path = sc.nextLine();
        System.out.println(path);

        OFMGraph rf = new OFMGraph();
        Graph graph;
        graph = rf.mapFileToGraph(path);

        System.out.println("Czy graf ma byc rozwiazany algorytmem bruteForce (wpisz true) czy Evolutionary (wpisz false) \n");
        boolean brutForceBoolean = sc.nextBoolean();
        System.out.println("Czy chcesz rozwiązać problem DAP (wpisz true) czy DDAP (wpisz false)");
        boolean dapOrDdap = sc.nextBoolean();

        if(brutForceBoolean)
            bruteForce(graph, dapOrDdap);
        else {
            System.out.println("Podaj wielkosc populacji");
            int populationSize = sc.nextInt();
            System.out.println("Podaj warunek stopu dla algorytmu ewolucyjnego. liczba generacji, liczba mutacji, czas trwania algorytmu w milisekundach");
            System.out.println("Wpisz 0 jeżeli chcesz pominąć dany warunek, wpisz wartość jeżeli chcesz zeby dany warunek był aktywny");
            System.out.println("Wpisz liczbe generacji: 0 = nieaktywny");
            int iterations = sc.nextInt();
            System.out.println("Wpisz liczbe mutacji: 0 = nieaktywny");
            int mutationsNumber = sc.nextInt();
            System.out.println("Wpisz czas trwania: 0 = nieaktywny");
            long time = sc.nextLong();
            System.out.println("Podaj prawdopodobienstwo mutacji (TYLKO PSTWO CALKOWITOLICZBOWE tzn. 0,1,2,3,4 = 0%, 1%, 2%, 3%, 4%");
            int probabilityNominator = sc.nextInt();
            evolutionary(graph, seed, dapOrDdap, populationSize, iterations, mutationsNumber, time, probabilityNominator);
        }
    }

    private static void bruteForce(Graph graph, boolean dap) {
        BruteForce bruteForce = new BruteForce(graph);
        bruteForce.generateRoutingTable();
        bruteForce.printSolutions();
        if(dap == true){
            dapSolutions(bruteForce);
        }else
            ddapSolutions(bruteForce);
    }

    private static void dapSolutions(BruteForce bruteForce) {
        Map<List<int[]>, Integer> solvedDap = bruteForce.solveDAP();
        Map.Entry<List<int[]>, Integer> min = null;
        for (Map.Entry<List<int[]>, Integer> current : solvedDap.entrySet()) {
            if (min == null || min.getValue() > current.getValue()) {
                min = current;
            }
        }
        System.out.println("BEST SOLUTION FOR DAP");
        min.getKey().forEach(s-> System.out.println(Arrays.toString(s)));
        System.out.println(min.getValue());
    }

    private static void ddapSolutions(BruteForce bruteForce) {
        Map<List<int[]>, Float> solvedDap = bruteForce.solveDDAP();
        Map.Entry<List<int[]>, Float> min = null;
        for (Map.Entry<List<int[]>, Float> current : solvedDap.entrySet()) {
            if (min == null || min.getValue() > current.getValue()) {
                min = current;
            }
        }
        System.out.println("BEST SOLUTION FOR DDAP");
        min.getKey().forEach(s-> System.out.println(Arrays.toString(s)));
        System.out.println(min.getValue());
    }

    private static void evolutionary(Graph graph, int seed, boolean dap,int populationSize, int iterations, int mutations, long time, int probabilityNominator){
        Evolutionary evolutionary = new Evolutionary(graph, seed);
        evolutionary.generatePopulation(populationSize);
        evolutionary.startAlghoritm(iterations,mutations,time,dap, probabilityNominator);
        evolutionary.getPopulation().forEach(chromosome -> {
            chromosome.getGenesList().forEach(gene -> System.out.println(Arrays.toString(gene.getAllocationPaterForDemand())));
            System.out.println("NEW");
        });
        System.out.println("BEST SOLUTION");
        evolutionary.getPopulation().get(0).getGenesList().forEach(gene -> System.out.println(Arrays.toString(gene.getAllocationPaterForDemand())));
        System.out.println();
        System.out.println(evolutionary.getPopulation().size());
    }
}
