package com.company.alghoritm;

import com.company.graph.Demand;
import com.company.graph.Graph;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;


public class BruteForce {
    private Graph graph;
    private Integer[][] routingSolution;

    public BruteForce(Graph graph) {
        this.graph = graph;
//        columns =
        routingSolution = new Integer[graph.getDemandSize()][graph.getDemandMaxPathSize()];
    }

    public void generateRoutingTable(){
        System.out.println(Arrays.toString(generateTable(5)));
        System.out.println(countBits(63));
        for (Demand demand : graph.getDemandList()) {
            generateNVariationsOnKPoints(demand.getVolume(), demand.getPathsNumber());
        }
    }


    private void generateNVariationsOnKPoints(int n, int k){
        System.out.println(n + " " + k);
        int nBits = countBits(n);
        System.out.println(nBits);
        int[] variationsBeforeConversionToBinary = generateTable((int) Math.pow(2,nBits*k));   // n+1 beacuse of 0
        String[] variationsInBytesSeriesBeforeSplit = convertToBinary(variationsBeforeConversionToBinary, nBits, k);
        System.out.println(Arrays.toString(variationsBeforeConversionToBinary));
        System.out.println(Arrays.toString(variationsInBytesSeriesBeforeSplit));
       splitBytesSeries(variationsInBytesSeriesBeforeSplit,nBits);


    }

    private void splitBytesSeries(String[] bytesSeriesArray, int bits){
        for(String bytesSeries : bytesSeriesArray) {
            System.out.println(Arrays.toString(bytesSeries.split("(?<=\\G..)")));
        }
    }

    private String[] convertToBinary(int[] table, int bits, int kPoints){
        String[] bytesTableAsString = new String[table.length];
        int i = 0;
        String formater = "%" + bits*kPoints + "s";
        for (int record : table){
           bytesTableAsString[i] = Integer.toBinaryString(record);
           bytesTableAsString[i] = String.format(formater, bytesTableAsString[i]).replace(' ', '0');
           i++;
        }
        return bytesTableAsString;
    }

    private int[] generateTable(int n){
        return IntStream.range(0, n).toArray();
    }

    private static int countBits(int number)
    {
        return (int)(Math.log(number) /
                Math.log(2) + 1);
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public Integer[][] getRoutingSolution() {
        return routingSolution;
    }

    public void setRoutingSolution(Integer[][] routingSolution) {
        this.routingSolution = routingSolution;
    }
}
