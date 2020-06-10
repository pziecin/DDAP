package com.company.alghoritm;

import com.company.graph.Graph;

import java.util.*;

public class LinkLoads {
    private void testAfterIteration(int[] link, int iter) {
        for (int e : link) {
            System.out.print(e + " ");

        }
        System.out.println("BREAK");

        if (iter == 17)
            System.exit(1);
    }

    public int[] linkLoads(Graph graph, List<int[]> table) {
//       int iter = test(table);
        int[] link = new int[graph.getNumberOfLinks()];
        for (int e = 0; e < graph.getNumberOfLinks(); e++) {
            link[e] = 0;
        }
        for (int d = 0; d < graph.getDemandSize(); d++) {
            for (int p = 0; p < graph.getDemandList().get(d).getDemandPathsListSize(); p++) {
                for (int e = 0; e < graph.getNumberOfLinks(); e++) {
                    if (isLinkInDemandPath(graph,e + 1, d, p)) {    //e+1 Beacuse numeration of Links beginning from 1 (not from 0)
                        link[e] = link[e] + table.get(d)[p];
                    }
                }
            }
        }

        return link;
//        testAfterIteration(link, iter);
    }

    private boolean isLinkInDemandPath(Graph graph, int link, int demand, int path) {
        for (Integer link_id : graph.getDemandList().get(demand).getDemandPaths().get(path)) {
            if (link_id.intValue() == link) {
                return true;
            }
        }
        return false;
    }
}
