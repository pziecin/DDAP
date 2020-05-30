package com.company.alghoritm;

import com.company.graph.Demand;
import com.company.graph.Graph;

import java.util.*;
import java.util.stream.IntStream;


public class BruteForce {
    private Graph graph;
    private Integer[][] routingSolution;
    private Map<List<int[]>,int[]> linkLoadsList;

    public BruteForce(Graph graph) {
        this.graph = graph;
        routingSolution = new Integer[graph.getDemandSize()][graph.getDemandMaxPathSize()];
    }

    public Map<List<int[]>, int[]> generateRoutingTable() {
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
            linkLoadsList.put(combinationsOfDemand.get(i),linkLoads(combinationsOfDemand.get(i)));
        }
        return linkLoadsList;
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


    private int test(List<int[]> table) {
        int[][] TABL = {{0, 3, 0}, {2, 0, 2}, {2, 3}, {1, 0, 1}, {1, 2, 0}, {2, 2, 0}};
        int iter = 0;
        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.get(i).length; j++) {
                System.out.print(table.get(i)[j]);
                if (table.get(i)[j] == TABL[i][j]) {
                    iter++;
                }
            }
            System.out.println();
        }
        return iter;
    }


    private void testAfterIteration(int[] link, int iter) {
        for (int e : link) {
            System.out.print(e + " ");

        }
        System.out.println("BREAK");

        if (iter == 17)
            System.exit(1);
    }

    public int[] linkLoads(List<int[]> table) {
//       int iter = test(table);
        int[] link = new int[graph.getNumberOfLinks()];
        for (int e = 0; e < graph.getNumberOfLinks(); e++) {
            link[e] = 0;
        }
        for (int d = 0; d < graph.getDemandSize(); d++) {
            for (int p = 0; p < graph.getDemandList().get(d).getDemandPathsListSize(); p++) {
                for (int e = 0; e < graph.getNumberOfLinks(); e++) {
                    if (isLinkInDemandPath(e + 1, d, p)) {    //e+1 Beacuse numeration of Links beginning from 1 (not from 0)
                        link[e] = link[e] + table.get(d)[p];
                    }
                }
            }
        }

        return link;
//        testAfterIteration(link, iter);
    }

    private boolean isLinkInDemandPath(int link, int demand, int path) {
        for (Integer link_id : graph.getDemandList().get(demand).getDemandPaths().get(path)) {
            if (link_id.intValue() == link) {
                return true;
            }
        }
        return false;
    }

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

    public Integer[][] getRoutingSolution() {
        return routingSolution;
    }

    public void setRoutingSolution(Integer[][] routingSolution) {
        this.routingSolution = routingSolution;
    }
}
