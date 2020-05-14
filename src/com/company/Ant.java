package com.company;

import java.util.ArrayList;
import java.util.List;

public class Ant {
    private List<Edge> path;
    private int pathWeight;


    public int getPathWeight() {
        return pathWeight;
    }

    public void setPathWeight(int pathWeight) {
        this.pathWeight = pathWeight;
    }

    public void addWeight(int weight){
        this.pathWeight = this.pathWeight + weight;
    }

    public List<Edge> getPath() {
        return path;
    }


    public void setPath(List<Edge> path) {
        this.path = path;
    }

    public void clearPath(){
        this.path.clear();
        this.pathWeight = 0;
    }


    public Ant(List<Edge> path) {
        this.path = path;
        this.pathWeight = 0;
    }

    public Ant(Ant ant){
        this.path = new ArrayList<>(ant.getPath());
        this.pathWeight = ant.getPathWeight();
    }
}
