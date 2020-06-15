package com.company.graph;

import java.util.List;

public class Demand {
    private List<List<Integer>> demandPaths;  //Link_id list
    private int startNode;
    private int endNode;
    private int volume;
    private int pathsNumber;

    public Demand(List<List<Integer>> demandPaths) {
        this.demandPaths = demandPaths;
    }

    public Demand(List<List<Integer>> demandPaths, int startNode, int endNode, int volume, int pathsNumber) {
        this.demandPaths = demandPaths;
        this.startNode = startNode;
        this.endNode = endNode;
        this.volume = volume;
        this.pathsNumber = pathsNumber;
    }

    public int getDemandPathsListSize(){
        return demandPaths.size();
    }

    public List<List<Integer>> getDemandPaths() {
        return demandPaths;
    }

    public void printDemandPath(){
        System.out.println("DEMAND PATHS");
        for (int i = 0; i < demandPaths.size() ; i++) {
            for (int j = 0; j < demandPaths.get(i).size() ; j++) {
                System.out.print(demandPaths.get(i).get(j));
            }
            System.out.println();
        }
    }

    public int getStartNode() {
        return startNode;
    }

    public void setStartNode(int startNode) {
        this.startNode = startNode;
    }

    public int getEndNode() {
        return endNode;
    }

    public void setEndNode(int endNode) {
        this.endNode = endNode;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getPathsNumber() {
        return pathsNumber;
    }

    public void setPathsNumber(int pathsNumber) {
        this.pathsNumber = pathsNumber;
    }

    @Override
    public String toString() {
        return "Demand{" +
                "demandPaths=" + demandPaths +
                ", startNode=" + startNode +
                ", endNode=" + endNode +
                ", volume=" + volume +
                ", pathsNumber=" + pathsNumber +
                '}';
    }
}
