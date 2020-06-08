package com.company.graph;

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


    public int getNumberOfFibrePairs() {
        return numberOfFibrePairs;
    }

    public void setNumberOfFibrePairs(int numberOfFibrePairs) {
        this.numberOfFibrePairs = numberOfFibrePairs;
    }

    public float getFibrePairCost() {
        return fibrePairCost;
    }

    public void setFibrePairCost(float fibrePairCost) {
        this.fibrePairCost = fibrePairCost;
    }

    public int getNumberOfLambdas() {
        return numberOfLambdas;
    }

    public void setNumberOfLambdas(int numberOfLambdas) {
        this.numberOfLambdas = numberOfLambdas;
    }

    public Edge(Edge ed) {
        this.numberOfFibrePairs = ed.numberOfFibrePairs;
        this.fibrePairCost = ed.fibrePairCost;
        this.numberOfLambdas = ed.numberOfLambdas;
        this.startNode = ed.endNode;
        this.endNode = ed.startNode;
    }

    public Edge(int startNode, int endNode, int fibrePairs, float fibreCost, int numberOfLambdas) {
        this.numberOfFibrePairs = fibrePairs;
        this.fibrePairCost = fibreCost;
        this.numberOfLambdas = numberOfLambdas;
        this.startNode = startNode;
        this.endNode = endNode;
    }

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