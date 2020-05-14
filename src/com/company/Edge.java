package com.company;

import java.util.Objects;

public class Edge {
    private int startNode;
    private int endNode;
    private int numberOfFibrePairs;
    private float fibrePairCost;
    private int numberOfLambdas;

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

    public Edge(int startNode, int endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public Edge(Edge ed) {
        this.numberOfFibrePairs = ed.numberOfFibrePairs;
        this.fibrePairCost = ed.fibrePairCost;
        this.numberOfLambdas = ed.numberOfLambdas;
        this.startNode = ed.startNode;
        this.endNode = ed.endNode;
    }

    public Edge(int fibrePairs, float fibreCost, int numberOfLambdas, int startNode, int endNode) {
        this.numberOfFibrePairs = fibrePairs;
        this.fibrePairCost = fibreCost;
        this.numberOfLambdas = numberOfLambdas;
        this.startNode = startNode;
        this.endNode = endNode;
    }
    //    public double getPheromone() {
//        return pheromone;
//    }

//    public void setPheromone(double pheromone) {
//        this.pheromone = pheromone;
//    }
//
//    public void updatePheromone(double pheromone){
//        this.pheromone = this.pheromone + pheromone;
//    }


    @Override
    public String toString() {
        return "Edge{" +
                "startNode=" + startNode +
                ", endNode=" + endNode +
                ", numberOfFibrePairs=" + numberOfFibrePairs +
                ", fibrePairCost=" + fibrePairCost +
                ", numberOfLambdas=" + numberOfLambdas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return numberOfFibrePairs == edge.numberOfFibrePairs &&
                Float.compare(edge.fibrePairCost, fibrePairCost) == 0 &&
                numberOfLambdas == edge.numberOfLambdas &&
                startNode == edge.startNode &&
                endNode == edge.endNode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfFibrePairs, fibrePairCost, numberOfLambdas, startNode, endNode);
    }
}



//1 2 3 4 5
//2 3 4 5 - > idziemy np do 2
//z 2 wybieramy sposrod 3 4 5 ? -> idziemy do np 4
//z 4 wybieramy sposrod 3 5 ? -> idziemy do np. 3
//z 3 wybieramy 5 ? -> idziemy do 5
//z 5 idziemy do startu 1