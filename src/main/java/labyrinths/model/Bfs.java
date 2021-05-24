package labyrinths.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Bfs{
    public static List<Field> startAlgorithm(Graph graph, int start, int target){
        List<Field> process=new LinkedList<>();
        List<Field> path=new LinkedList<>();
        if(!runBfs(graph, start, target, process, path) )return process;
        process.addAll(path);
        return process;
    }
    static boolean runBfs(Graph graph, int start, int target, List<Field> process, List<Field>path){
        int current;
        List<Integer> list=new ArrayList<>();
        list.add(start);
        Integer[] previous=new Integer[graph.height* graph.width];
        while(!list.isEmpty()){
            current=list.get(0);
            list.remove(0);
            if(current==target){
                process.add(new Field(graph.graph.get(current), Type.HIGHLIGHTED));

                graph.graph.get(current).field.type=Type.PATH;
                while(current!=start){
                    path.add(new Field(graph.graph.get(current), Type.PATH));
                    graph.graph.get(current).field.type=Type.PATH;
                    current=previous[current];
                }
                graph.graph.get(current).field.type=Type.PATH;
                path.add(new Field(graph.graph.get(current), Type.PATH));
                Collections.reverse(path);
                return true;
            }
            process.add(new Field(graph.graph.get(current), Type.HIGHLIGHTED));
            Collections.shuffle(graph.graph.get(current).neighbors);
            for(int x : graph.graph.get(current).neighbors){
                //System.out.println(x);
                if(graph.graph.get(x).field.type==Type.FREE) {
                    list.add(x);
                    graph.graph.get(x).field.type=Type.HIGHLIGHTED;
                    previous[x] = current;
                }
            }
        }
        return false;
    }
}
