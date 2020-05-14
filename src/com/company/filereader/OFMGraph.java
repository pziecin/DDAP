package com.company.filereader;

import com.company.Edge;
import com.company.Graph;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


//Object File Mapping for Graph
public class OFMGraph {
    private FileReader fileReader;
    private String fileAsAString;
    private int linkNumbers;

    private Graph graph;
    private List<Edge> loadedEdgeList;

    public OFMGraph() {
    }

    public void loadFile(String path){
        File file = new File(path);
        try {
            fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            linkNumbers = Integer.parseInt(line);

            loadedEdgeList = new ArrayList<>();
            line = br.readLine();
            while(!line.equals("-1")){
                String[] array = line.split("\\s+");
                System.out.println(array[0].toString());
                List<Integer> link = Arrays.stream(array).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
                loadedEdgeList.add(new Edge(link.get(0),link.get(1),link.get(2),link.get(3),link.get(4)));
                sb.append(line);                //Mozna usunac
                sb.append(System.lineSeparator()); //Mozna usunac
                line = br.readLine();
            }

            loadedEdgeList.forEach(s->System.out.println(s.toString()));
            System.out.println((loadedEdgeList.get(2).toString()));
            while(line!=null){
                sb.append(line);                //Mozna usunac
                sb.append(System.lineSeparator()); //Mozna usunac
                line = br.readLine();
            }
            fileAsAString = sb.toString();
            System.out.println(fileAsAString);
        }catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void divideFile(){
        fileAsAString.endsWith("-1");
    }


}
