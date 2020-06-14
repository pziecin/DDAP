package com.company.alghoritm;

import com.company.graph.Demand;
import com.company.graph.Graph;

import java.util.*;
import java.util.stream.IntStream;


public class BruteForce extends LinkLoads{
    private Graph graph;
    private Map<List<int[]>, int[]> linkLoadsList;

    public BruteForce(Graph graph) {
        this.graph = graph;
    }

    public void printSolutions() {
        linkLoadsList.forEach((key, value) -> {
            key.forEach(t -> System.out.println(Arrays.toString(t)));
            System.out.println("  " + Arrays.toString(value));
        });
    }

    public Map<List<int[]>, int[]> getLinkLoadsList() {
        return linkLoadsList;
    }

    public void generateRoutingTable() {
        List<List<int[]>> demandList = new ArrayList<>();
        for (Demand demand : graph.getDemandList()) {
            int[][] variationsInDecimal = generateNVariationsOnKPoints(demand.getVolume(), demand.getPathsNumber());
            List<int[]> variationsForVolumeDemand = filtrWrongVariationsInGraph(variationsInDecimal, demand.getVolume());
            demandList.add(variationsForVolumeDemand);
        }
        List<List<int[]>> combinationsOfDemand = new ArrayList<>();
        generatePermutations(demandList, combinationsOfDemand, 0, new ArrayList<>());

        linkLoadsList = new HashMap<>();
        for (int i = 0; i < combinationsOfDemand.size(); i++) {
            linkLoadsList.put(combinationsOfDemand.get(i), linkLoads(graph, combinationsOfDemand.get(i)));
        }
    }


    public Map<List<int[]>, Integer> solveDAP() {
        Map<List<int[]>, Integer> linkLoadForDap = new HashMap<>();
        for (Map.Entry<List<int[]>, int[]> entry : linkLoadsList.entrySet()) {
            List linkLoads = new ArrayList();
            for (int i = 0; i < entry.getValue().length; i++) {
                linkLoads.add(entry.getValue()[i] - graph.getCapacityOnLink().get(i));
            }
            linkLoadForDap.put(entry.getKey(), (int) Collections.max(linkLoads));
        }
        return linkLoadForDap;
    }

    public Map<List<int[]>, Float> solveDDAP() {
        Map<List<int[]>, Float> linkLoadForDDAP = new HashMap<>();
        for (Map.Entry<List<int[]>, int[]> entry : linkLoadsList.entrySet()) {
            float cost =0;
            for (int i = 0; i < entry.getValue().length; i++) {
                cost = cost + (float) entry.getValue()[i]/graph.getModularity().get(i)*graph.getFibrePairCost().get(i);
            }
            System.out.println(cost);
            linkLoadForDDAP.put(entry.getKey(), cost);
        }
        return linkLoadForDDAP;
    }


    private static void generatePermutations(List<List<int[]>> ori, List<List<int[]>> res, int d, List<int[]> current) {
        if (d == ori.size()) {
            res.add(current);
            return;
        }
        List<int[]> currentCollection = ori.get(d);
        for (int[] element : currentCollection) {
            List<int[]> copy = new ArrayList<>(current);
            copy.add(element);
            generatePermutations(ori, res, d + 1, copy);
        }
    }





//    private void testAfterIteration(int[] link, int iter) {
//        for (int e : link) {
//            System.out.print(e + " ");
//
//        }
//        System.out.println("BREAK");
//
//        if (iter == 17)
//            System.exit(1);
//    }
//
//    public int[] linkLoads(List<int[]> table) {
////       int iter = test(table);
//        int[] link = new int[graph.getNumberOfLinks()];
//        for (int e = 0; e < graph.getNumberOfLinks(); e++) {
//            link[e] = 0;
//        }
//        for (int d = 0; d < graph.getDemandSize(); d++) {
//            for (int p = 0; p < graph.getDemandList().get(d).getDemandPathsListSize(); p++) {
//                for (int e = 0; e < graph.getNumberOfLinks(); e++) {
//                    if (isLinkInDemandPath(e + 1, d, p)) {    //e+1 Beacuse numeration of Links beginning from 1 (not from 0)
//                        link[e] = link[e] + table.get(d)[p];
//                    }
//                }
//            }
//        }
//
//        return link;
////        testAfterIteration(link, iter);
//    }
//
//    private boolean isLinkInDemandPath(int link, int demand, int path) {
//        for (Integer link_id : graph.getDemandList().get(demand).getDemandPaths().get(path)) {
//            if (link_id.intValue() == link) {
//                return true;
//            }
//        }
//        return false;
//    }

    private List<int[]> filtrWrongVariationsInGraph(int[][] variationsDecimal, int volume) {
        List<int[]> variationsForVolumeDemand = new ArrayList<>();
        for (int i = 0; i < variationsDecimal.length; i++) {
            int tmp = 0;
            int[] tmpArray = new int[variationsDecimal[i].length];
            for (int j = 0; j < variationsDecimal[i].length; j++) {
                tmp = tmp + variationsDecimal[i][j];
                tmpArray[j] = variationsDecimal[i][j];
            }
            if (tmp == volume) {
                variationsForVolumeDemand.add(tmpArray);
            }
        }
        return variationsForVolumeDemand;
    }

    private int[][] generateNVariationsOnKPoints(int n, int k) {
        int nBits = countBits(n);
        int[] variationsBeforeConversionToBinary = generateTable((int) Math.pow(2, nBits * k));   // n+1 beacuse of 0
        String[] variationsInBytesSeriesBeforeSplit = convertToBinary(variationsBeforeConversionToBinary, nBits, k);
        String[][] variationsInBytesSeriesAfterSplit = splitBytesSeries(variationsInBytesSeriesBeforeSplit, nBits);
        int[][] variationsInDecimal = convertToDecimal(variationsInBytesSeriesAfterSplit);
        return variationsInDecimal;
    }

    private String[][] splitBytesSeries(String[] bytesSeriesArray, int bits) {
        String[][] bytesSeriesAfterSplit = new String[bytesSeriesArray.length][bytesSeriesArray[0].length() / bits];
        int row = 0;
        for (String bytesSeries : bytesSeriesArray) {
            bytesSeriesAfterSplit[row] = bytesSeries.split("(?<=\\G.{" + bits + "})");
            row++;
        }
        return bytesSeriesAfterSplit;
    }

    private int[][] convertToDecimal(String[][] table) {
        int[][] tableWithVariations = new int[table.length][table[0].length];
        int rowIndex = 0, colIndex = 0;
        for (String[] row : table) {
            for (String col : row) {
                tableWithVariations[rowIndex][colIndex] = Integer.parseInt(col, 2);
                colIndex++;
            }
            rowIndex++;
            colIndex = 0;
        }
        return tableWithVariations;
    }

    private String[] convertToBinary(int[] table, int bits, int kPoints) {
        String[] bytesTableAsString = new String[table.length];
        int i = 0;
        String formater = "%" + bits * kPoints + "s";
        for (int record : table) {
            bytesTableAsString[i] = Integer.toBinaryString(record);
            bytesTableAsString[i] = String.format(formater, bytesTableAsString[i]).replace(' ', '0');
            i++;
        }
        return bytesTableAsString;
    }

    private int[] generateTable(int n) {
        return IntStream.range(0, n).toArray();
    }

    private static int countBits(int number) {
        return (int) (Math.log(number) /
                Math.log(2) + 1);
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
}
