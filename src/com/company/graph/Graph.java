package com.company.graph;

import java.util.*;

public class Graph {

    private  Edge[][] graf;
    private List<Demand> demandList;
    private int numberOfLinks;

    public Edge[][] getGraf() {
        return graf;
    }

    public void setGraf(Edge[][] graf) {
        this.graf = graf;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(graf) + demandList.toString();
    }

    public void createGraph(List<Edge> edges, List<Demand> demandList){
        numberOfLinks = edges.size();
        edges.forEach(edge -> {
            int startNode = edge.getStartNode()-1;
            int endNode = edge.getEndNode()-1;
            graf[startNode][endNode] = edge;
            graf[endNode][startNode] = new Edge(graf[startNode][endNode]);
        });
        createDemand(demandList);
    }

    public int getNumberOfLinks(){
        return numberOfLinks;
    }

    private void createDemand(List<Demand> demandList){
        this.demandList = demandList;
    }

    public List<Demand> getDemandList() {
        return demandList;
    }

    public int getDemandSize() {
        return demandList.size();
    }

    public int getDemandMaxPathSize(){
        return demandList.stream().max(Comparator.comparingInt(Demand::getPathsNumber)).get().getPathsNumber();
    }

    public void setDemandList(List<Demand> demandList) {
        this.demandList = demandList;
    }

    //Reading Edge references in array
    public void printEdgeReferences() {
        for (int i = 0; i < graf[0].length; i++) {
            for (int j = 0; j < graf[1].length; j++) {
                System.out.print(graf[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
    //Reading Edge weight in array (symmetric through diagonal)
    public List<Integer> getCapacityOnLink() {
        List<Integer> capacity = new ArrayList<>();
        for (int i = 0; i < graf[0].length; i++) {
            for (int j = 0; j < graf[1].length; j++) {
                if(i < j)
                    continue;
                if (graf[i][j] != null)
                    capacity.add(graf[i][j].getNumberOfFibrePairs() * graf[i][j].getNumberOfLambdas());
            }
        }
        return capacity;
    }

    public List<Integer> getModularity(){
        List<Integer> modularity = new ArrayList<>();
        for (int i = 0; i < graf[0].length; i++) {
            for (int j = 0; j < graf[1].length; j++) {
                if(i < j)
                    continue;
                if(graf[i][j] != null)
                    modularity.add(graf[i][j].getNumberOfLambdas());
            }
        }
        return modularity;
    }

    public List<Float> getFibrePairCost(){
        List<Float> modularity = new ArrayList<>();
        for (int i = 0; i < graf[0].length; i++) {
            for (int j = 0; j < graf[1].length; j++) {
                if(i < j)
                    continue;
                if(graf[i][j] != null)
                    modularity.add(graf[i][j].getFibrePairCost());
            }
        }
        return modularity;
    }


    public Graph(Edge[][] graf) {
        this.graf = graf;
    }
}
