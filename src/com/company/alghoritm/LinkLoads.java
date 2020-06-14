package com.company.alghoritm;

import com.company.graph.Graph;

import java.util.*;

public class LinkLoads {
    public int[] linkLoads(Graph graph, List<int[]> table) {
//       int iter = test(table);
        int[] link = new int[graph.getNumberOfLinks()];
        for (int e = 0; e < graph.getNumberOfLinks(); e++) {
            link[e] = 0;
        }
        for (int d = 0; d < graph.getDemandSize(); d++) {
            for (int p = 0; p < graph.getDemandList().get(d).getDemandPathsListSize(); p++) {
                for (int e = 0; e < graph.getNumberOfLinks(); e++) {
                    if (isLinkInDemandPath(graph,e+1, d, p)) {    //e+1 Beacuse numeration of Links beginning from 1 (not from 0)
                        link[e] = link[e] + table.get(d)[p];
//                        System.out.println("LINK: " + e + " DODAJE " + table.get(d)[p]);
                    }
                }
            }
        }

//        System.out.println("NEXT");
//        testAfterIteration(link, iter);
        return link;
    }

    private boolean isLinkInDemandPath(Graph graph, int link, int demand, int path) {
        for (Integer link_id : graph.getDemandList().get(demand).getDemandPaths().get(path)) {
            if (link_id.intValue() == link) {
                return true;
            }
        }
        return false;
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
}
