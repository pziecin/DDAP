package com.company.alghoritm;

import com.company.graph.Demand;
import com.company.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


public class BruteForce {
    private Graph graph;
    private Integer[][] routingSolution;

    public BruteForce(Graph graph) {
        this.graph = graph;
        routingSolution = new Integer[graph.getDemandSize()][graph.getDemandMaxPathSize()];
    }

    public void generateRoutingTable() {
        List<List<int[]>> demandList = new ArrayList<>();
        for (Demand demand : graph.getDemandList()) {
            int[][] variationsInDecimal = generateNVariationsOnKPoints(demand.getVolume(), demand.getPathsNumber());
            List<int[]> variationsForVolumeDemand = filtrWrongVariationsInGraph(variationsInDecimal, demand.getVolume());
            demandList.add(variationsForVolumeDemand);
        }
        List<String> aa = new ArrayList<>();
        generatePermutations(demandList, aa, 0, "");
//        aa.forEach(s -> System.out.println(s));
//        System.out.println(aa.size());
        linkLoads();
    }

    public void linkLoads(){
        int test =0;
        int[] link = new int[graph.getNumberOfLinks()];
        for (int e=0; e< graph.getNumberOfLinks(); e++){
            link[e] = 0;
        }
        for(int d=0; d<graph.getDemandSize(); d++){
            System.out.println("DEMAND" + d);
            for(int p=0;p< graph.getDemandList().get(d).getDemandPathsListSize();p++){
                System.out.println("PATH" + p);
                for (int e=0; e< graph.getNumberOfLinks();e++){
                    System.out.println("LINK" + e);
                    if(isLinkInDemandPath(e,d,p)){
                        test++;
                        System.out.println(test);
//                    if(isLinkInDemandPath(e,d,p)){
//                        link[e] = link[e] +
                    }
                }
            }
        }
    }

    private boolean isLinkInDemandPath(int link, int demand, int path) {
        for(Integer link_id : graph.getDemandList().get(demand).getDemandPaths().get(path)){
            if(link_id.intValue() == link){
                return true;
            }
        }
        return false;
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
            if(tmp == volume){
                variationsForVolumeDemand.add(tmpArray);
            }
        }
        return variationsForVolumeDemand;
    }

    private void generatePermutations(List<List<int[]>> lists, List<String> result, int depth, String current) {
        if (depth == lists.size()) {
            result.add(current);
            return;
        }

        for (int i = 0; i < lists.get(depth).size(); i++) {
            generatePermutations(lists, result, depth + 1, current + Arrays.toString(lists.get(depth).get(i)));
        }
    }

    private int[][] generateNVariationsOnKPoints(int n, int k){
        int nBits = countBits(n);
        int[] variationsBeforeConversionToBinary = generateTable((int) Math.pow(2,nBits*k));   // n+1 beacuse of 0
        String[] variationsInBytesSeriesBeforeSplit = convertToBinary(variationsBeforeConversionToBinary, nBits, k);
        String[][] variationsInBytesSeriesAfterSplit = splitBytesSeries(variationsInBytesSeriesBeforeSplit, nBits);
        int[][] variationsInDecimal = convertToDecimal(variationsInBytesSeriesAfterSplit);
        return variationsInDecimal;
    }

    private String[][] splitBytesSeries(String[] bytesSeriesArray, int bits){
        String[][] bytesSeriesAfterSplit = new String[bytesSeriesArray.length][bytesSeriesArray[0].length()/bits];
        int row = 0;
        for(String bytesSeries : bytesSeriesArray) {
            bytesSeriesAfterSplit[row] = bytesSeries.split("(?<=\\G.{"+bits+"})");
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
