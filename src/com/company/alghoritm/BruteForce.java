package com.company.alghoritm;

import com.company.graph.Demand;
import com.company.graph.Graph;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
//        System.out.println(Arrays.toString(generateTable(5)));
//        System.out.println(countBits(63));
        for (Demand demand : graph.getDemandList()) {
            int[][] variationsInDecimal = generateNVariationsOnKPoints(demand.getVolume(), demand.getPathsNumber());
            List<int[]> variationsForVolumeDemand = filtrWrongVariationsInGraph(variationsInDecimal, demand.getVolume());
        }
    }

    private List<int[]> filtrWrongVariationsInGraph(int[][] variationsDecimal, int volume) {
        List<int[]> variationsForVolumeDemand = new ArrayList<>();
        for(int i=0; i< variationsDecimal.length; i++){
            int tmp = 0;
            int[] tmpArray = new int[variationsDecimal[i].length];
            for(int j=0; j < variationsDecimal[i].length; j++){
                tmp = tmp + variationsDecimal[i][j];
                tmpArray[j] = variationsDecimal[i][j];
            }
            if(tmp <= volume){
                variationsForVolumeDemand.add(tmpArray);
            }
        }
        variationsForVolumeDemand.forEach(s -> System.out.println(Arrays.toString(s)));
        System.out.println(variationsForVolumeDemand.size());
        return variationsForVolumeDemand;
    }


    private int[][] generateNVariationsOnKPoints(int n, int k){
        System.out.println(n + " " + k);
        int nBits = countBits(n);
        System.out.println(nBits);
        int[] variationsBeforeConversionToBinary = generateTable((int) Math.pow(2,nBits*k));   // n+1 beacuse of 0
        String[] variationsInBytesSeriesBeforeSplit = convertToBinary(variationsBeforeConversionToBinary, nBits, k);
        System.out.println(Arrays.toString(variationsBeforeConversionToBinary));
        System.out.println(Arrays.toString(variationsInBytesSeriesBeforeSplit));
        String[][] variationsInBytesSeriesAfterSplit = splitBytesSeries(variationsInBytesSeriesBeforeSplit, nBits);
        int[][] variationsInDecimal = convertToDecimal(variationsInBytesSeriesAfterSplit);
        return variationsInDecimal;
    }

    private String[][] splitBytesSeries(String[] bytesSeriesArray, int bits){
        String[][] bytesSeriesAfterSplit = new String[bytesSeriesArray.length][bytesSeriesArray[0].length()/bits];
        System.out.println(bytesSeriesArray.length+ " SPACE " + bytesSeriesArray[0].length()/bits);
        int row = 0;
        for(String bytesSeries : bytesSeriesArray) {
            bytesSeriesAfterSplit[row] = bytesSeries.split("(?<=\\G.{"+bits+"})");
//            System.out.println(Arrays.toString(bytesSeries.split("(?<=\\G.{"+bits+"})")));
//            System.out.println(Arrays.toString(bytesSeriesAfterSplit[row]));
            row++;
        }
        return bytesSeriesAfterSplit;
    }

    private int[][] convertToDecimal(String[][] table){
        int[][] tableWithVariations = new int[table.length][table[0].length];
        int rowIndex = 0, colIndex = 0;
        for(String[] row : table){
            for(String col: row){
                tableWithVariations[rowIndex][colIndex] = Integer.parseInt(col,2);
                colIndex++;
            }
            rowIndex++;
            colIndex=0;
        }
        return tableWithVariations;
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
