package com.company;

import java.util.*;

public class Graph {

    private  Edge[][] graf;

    public Edge[][] getGraf() {
        return graf;
    }

    public void setGraf(Edge[][] graf) {
        this.graf = graf;
    }


    public Edge[][] createRandomGraph(int maxWeight){  //p
            Random rand = new Random();
            double pheromone = 0.001;// rand.nextDouble();
            for (int i = 0; i < graf[0].length; i++) {
                for (int j = i; j < graf[1].length; j++) {
                    if (i == j) {
//                        graf[i][j] = new Edge(0,i,j);
                        continue;
                    }
////                #Wpisywanie reczne wag wezla
                    Scanner sc = new Scanner(System.in);
	                System.out.println("Podaj wage pomiedzy węzłem " + i + " , a " + j);
//	                graf[i][j] = new Edge(sc.nextInt());
                    graf[j][i] = new Edge(graf[i][j]);            //same references on Edge (undirected graph 1->2 and 2->1 are equals)
                }
            }
            return graf;
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
    public void readEdgeWeight() {
        for (int i = 0; i < graf[0].length; i++) {
            for (int j = 0; j < graf[1].length; j++) {
//                System.out.print(graf[i][j].getWeight() + " ");
            }
            System.out.print("\n");
        }
    }

//    public void readEdgePheromone(){
//        for (int i = 0; i < graf[0].length; i++) {
//            for (int j = 0; j < graf[1].length; j++) {
//                    System.out.print(graf[i][j].getPheromone() + " ");
//            }
//            System.out.print("\n");
//        }
//    }

//    public void resetEdgePheromone(){
//        for (int i = 0; i < graf[0].length; i++) {
//            for (int j = 0; j < graf[1].length; j++) {
//                if(i==j){continue;}
//                graf[i][j].setPheromone(0.001);
//            }
//        }
//    }

//    public List<Edge> findRouteForAnt(int startNode, double alfa){
//        //Edge[] path = new Edge[this.graf[0].length];
//        List<Edge> path = new ArrayList<>();
//
//        int currentNode = startNode;
//        double tempPheromone = 0;
//        double tempSumPheromone = 0;
//
//        List<Integer> nodeList = new ArrayList<>();
//        for(int i=0;i<graf[0].length;i++)
//            nodeList.add(i);
//        nodeList.remove(currentNode);
//
//        Map<Double,Integer> pbbltMap = new LinkedHashMap<>();
//        //While ant visit all nodes
//        while(!nodeList.isEmpty()){
//            //Counting sum of pheromones on all edges
//            for (int j = 0; j < graf[1].length; j++) {
//                if (j == currentNode) {
//                    continue;
//                }
//                if(nodeList.contains(j)) {
//                    tempPheromone = tempPheromone + Math.pow(graf[currentNode][j].getPheromone(),alfa);
//                }else
//                    continue;
//            }
//            //Counting probability for every edge
//            for (int j = 0; j < graf[1].length; j++) {
//                if (j == currentNode) {
//                    continue;
//                }
//                if(nodeList.contains(j)) {  // UPDATE EQUATIONS LIKE ON 365 PAGE in Computational Inteligence
//                    tempSumPheromone = tempSumPheromone + Math.pow(graf[currentNode][j].getPheromone(),alfa)/tempPheromone;
//                    pbbltMap.put(tempSumPheromone,j);
//                }else
//                    continue;
//            }
//            //Decision where to go from currentNode
//            double randOncePerDecision = Math.random();
//            for (Map.Entry<Double,Integer> entry : pbbltMap.entrySet()) {
//                if (randOncePerDecision - entry.getKey() < 0.0001) {
//                    path.add(graf[currentNode][entry.getValue()]);
//                    currentNode = entry.getValue();
//                    nodeList.remove(entry.getValue());
//                    break;
//                }
//            }
//            tempPheromone=0;
//            tempSumPheromone=0;
//            pbbltMap.clear();
//        }
//        path.add(graf[currentNode][startNode]);
//        return path;
//    }

//    public void evaporation(double factor){
//        for (int i = 0; i < graf.length; i++) {
//            for (int j = 0; j < graf[i].length; j++) {
//                if(factor > 0.999999){    //to avoid pheromone = 0
//                    break;
//                }
//                if(i==j){continue;}
//                double tmPhrmn = graf[i][j].getPheromone();
//                graf[i][j].setPheromone(tmPhrmn * (1-factor));
//            }
//        }
//    }

//    public Pair<Integer,Integer> findEdgeInGraph(Edge edge){
//        for (int i = 0; i < graf[0].length; i++) {
//            for (int j = 0; j < graf[1].length; j++) {
//                if(graf[i][j].equals(edge))
//                    return new Pair(i,j);
//            }
//        }
//        return new Pair(0,0);
//    }

    public Graph(Edge[][] graf) {
        this.graf = graf;
    }
}