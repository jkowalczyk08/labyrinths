package labyrinths.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Dfs {
    public static List<Field> startAlgorithm(Graph graph, int start, int target){
        if(start==0)return new LinkedList<>();
        List<Field> process=new LinkedList<>();
        List<Field> path=new LinkedList<>();
        if(!runDfs(graph, start, target, process, path) )return process;
        process.addAll(path);
        return process;
    }
    static boolean runDfs(Graph graph, int current, int target, List<Field> process, List<Field>path){
        if(current==target){
            process.add(new Field(graph.graph.get(current), Type.HIGHLIGHTED));
            path.add(new Field(graph.graph.get(current), Type.PATH));
            graph.graph.get(current).field.type=Type.PATH;
            return true;
        }path.add(new Field(graph.graph.get(current), Type.PATH));
        if(graph.graph.get(current).field.type!=Type.FREE&&graph.graph.get(current).field.type!=Type.START&&graph.graph.get(current).field.type!=Type.TELEPORT&&graph.graph.get(current).field.type!=Type.TARGET){
            return false;
        }
        process.add(new Field(graph.graph.get(current), Type.HIGHLIGHTED));
        //System.out.println(process);

        graph.graph.get(current).field.type=Type.PATH;
        Collections.shuffle(graph.graph.get(current).neighbors);
        for(int x : graph.graph.get(current).neighbors){
            //System.out.println(x);
                if(runDfs(graph, x, target, process, path))
                    return true;
                path.remove(path.size()-1);
        }graph.graph.get(current).field.type=Type.HIGHLIGHTED;
        return false;
    }
}
