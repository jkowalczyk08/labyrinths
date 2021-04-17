package labyrinths.model;

import java.util.LinkedList;
import java.util.List;

public class Dfs {
    public static List<Field> startDfs(Graph graph, int start, int target){
        List<Field> process=new LinkedList<>();
        List<Field> path=new LinkedList<>();
        if(!runDfs(graph, start, target, process, path) )return process;
        process.addAll(path);
        return process;
    }
    static boolean runDfs(Graph graph, int current, int target, List<Field> process, List<Field>path){
        if(current==target){
            path.add(new Field(graph.graph.get(current), Type.PATH));
            return true;
        }path.add(new Field(graph.graph.get(current), Type.PATH));
        if(graph.graph.get(current).field.type!=Type.FREE){
            return false;
        }
        process.add(new Field(graph.graph.get(current), Type.HIGHLIGHTED));
        System.out.println(process);

        graph.graph.get(current).field.type=Type.PATH;
        for(int x : graph.graph.get(current).neighbors){
            System.out.println(x);
                if(runDfs(graph, x, target, process, path))
                    return true;
                path.remove(path.size()-1);
        }graph.graph.get(current).field.type=Type.HIGHLIGHTED;
        return false;
    }
}
