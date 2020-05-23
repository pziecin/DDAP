package com.company.filereader;

import com.company.graph.Demand;
import com.company.graph.Edge;
import com.company.graph.Graph;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


//Object File Mapping for Graph
public class OFMGraph {
    private FileReader fileReader;
    private String fileAsAString;
    private int linkNumbers;

    private List<Edge> loadedEdgeList;
    private List<Demand> demandList;

    public OFMGraph() {
    }

    public Graph mapFileToGraph(String path){                                                                                      //SPECIFIC FILE TXT PARSER GRAPH WITH DEMANDS
        File file = new File(path);
        try {
            fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            linkNumbers = Integer.parseInt(line);

            loadedEdgeList = new ArrayList<>();
            demandList = new ArrayList<>();
            line = br.readLine();
            while (!line.equals("-1")) {
                String[] array = line.split("\\s+");
                List<Integer> link = Arrays.stream(array).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
                loadedEdgeList.add(new Edge(link.get(0), link.get(1), link.get(2), link.get(3), link.get(4)));
                sb.append(line);                //Mozna usunac
                sb.append(System.lineSeparator()); //Mozna usunac
                line = br.readLine();
            }

            br.readLine();                                                                                                      //SPACE
            br.readLine();                                                                                                      //NR OF DEMANDS
            line = br.readLine();                                                                                               //SPACE
//            loadedEdgeList.forEach(s->System.out.println(s.toString()));
            while (line != null) {
                if (line.isEmpty()) {
                    line = br.readLine();                                                                                       //example: 1 2 3
                    if (line == null) {                                                                                             //at the end if there is null line
                        break;
                    }
                    String[] array = line.split("\\s+");
                    List<Integer> link = Arrays.stream(array).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
                    int startNode = link.get(0);
                    int endNode = link.get(1);
                    int volume = link.get(2);
                    int pathsNumber = Integer.parseInt(br.readLine());                                                         //example: 3
                    List<List<Integer>> edgeIdReference = new ArrayList<>();
                    List<Integer> oneEdgeItDemand;

                    for (int i = 0; i < pathsNumber; i++) {
                        line = br.readLine();                                                                                  //example: 1 1
                        array = line.split("\\s+");
                        link = Arrays.stream(array).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
                        link.remove(0);
                        edgeIdReference.add(link);
//                        link.forEach(s->System.out.print(s));
//                        System.out.println();
                    }
                    demandList.add(new Demand(edgeIdReference, startNode, endNode, volume, pathsNumber));
                }
                line = br.readLine();
            }
        }catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }finally {
            return createGraph();
        }
    }

    private Graph createGraph(){
        Graph graph;
        int graphSize = loadedEdgeList.size()-1;
        graph = new Graph(new Edge[graphSize][graphSize]);
        graph.createGraph(loadedEdgeList, demandList);
        return graph;
    }

    public void divideFile(){
        fileAsAString.endsWith("-1");
    }


}
