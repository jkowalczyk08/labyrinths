package labyrinths.model;

import java.util.LinkedList;
import java.util.List;

public class Dfs {
    static List<Field> process=new LinkedList<>();
    static List<Field> path=new LinkedList<>();
    public static List<Field> startDfs(Graph graph, Type[][] table, Node start){
        if(!runDfs(graph, table, new Node(1, 1))) return process;
        process.addAll(path);
        return process;
    }
    static boolean runDfs(Graph graph, Type[][] table, Node current){
        if(table[current.h][current.w]==Type.TARGET){
            table[current.h][current.w]=Type.PATH;
            path.add(new Field(current, Type.PATH));
            return true;
        }
        process.add(new Field(current, Type.HIGHLIGHTED));
        //System.out.println(process);
        table[current.h][current.w]=Type.PATH;
        path.add(new Field(current, Type.PATH));
        for(Node x : graph.graph.get(current)){
            //System.out.println(x);
            if(table[x.h][x.w]==Type.FREE||table[x.h][x.w]==Type.TARGET){
                if(runDfs(graph, table, x))
                    return true;
                table[x.h][x.w]=Type.HIGHLIGHTED;
                path.remove(path.size()-1);
            }
        }
        return false;
    }
}
