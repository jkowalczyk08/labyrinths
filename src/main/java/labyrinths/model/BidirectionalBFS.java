package labyrinths.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BidirectionalBFS{
    public static List<Field> startAlgorithm(Graph graph, int start, int target){
        List<Field> process=new LinkedList<>();
        List<Field> path=new LinkedList<>();
        if(!runBfs(graph, start, target, process, path) )return process;
        process.addAll(path);
        return process;
    }
    static boolean runBfs(Graph graph, int start, int target, List<Field> process, List<Field>path){
        int current1;
        int current2;
        List<Integer> list1=new ArrayList<>();
        List<Integer> list2=new ArrayList<>();
        list1.add(start);
        list2.add(target);
        Integer[] previous1=new Integer[graph.height* graph.width];
        Integer[] previous2=new Integer[graph.height* graph.width];
        while(!list1.isEmpty()&&!list2.isEmpty()){
            current1=list1.get(0);
            list1.remove(0);
            current2=list2.get(0);
            list2.remove(0);
            if(graph.graph.get(current1).field.type==Type.HIGHLIGHTED2){
                process.add(new Field(graph.graph.get(current1), Type.HIGHLIGHTED));
                graph.graph.get(current1).field.type=Type.PATH;
                current2=current1;
                boolean i=true;
                while(i){
                    i=false;
                    if(current2!=target){
                        path.add(new Field(graph.graph.get(current2), Type.PATH));
                        graph.graph.get(current2).field.type=Type.PATH;
                        current2=previous2[current2];
                        i=true;
                    }
                    if(current1!=start){
                        path.add(new Field(graph.graph.get(current1), Type.PATH));
                        graph.graph.get(current1).field.type=Type.PATH;
                        current1=previous1[current1];
                        i=true;
                    }
                }
                graph.graph.get(current1).field.type=Type.PATH;
                path.add(new Field(graph.graph.get(current1), Type.PATH));
                graph.graph.get(current2).field.type=Type.PATH;
                path.add(new Field(graph.graph.get(current2), Type.PATH));
                return true;
            }
            process.add(new Field(graph.graph.get(current1), Type.HIGHLIGHTED));
            graph.graph.get(current1).field.type=Type.HIGHLIGHTED;
            Collections.shuffle(graph.graph.get(current1).neighbors);
            for(int x : graph.graph.get(current1).neighbors){
                //System.out.println(x);
                if(graph.graph.get(x).field.type!=Type.WALL&&graph.graph.get(x).field.type!=Type.HIGHLIGHTED) {
                    list1.add(x);
                    previous1[x] = current1;
                }
            }
            if(graph.graph.get(current2).field.type==Type.HIGHLIGHTED){
                process.add(new Field(graph.graph.get(current2), Type.HIGHLIGHTED2));
                graph.graph.get(current2).field.type=Type.PATH;
                current1=current2;
                boolean i=true;
                while(i){
                    i=false;
                    if(current2!=target){
                        path.add(new Field(graph.graph.get(current2), Type.PATH));
                        graph.graph.get(current2).field.type=Type.PATH;
                        current2=previous2[current2];
                        i=true;
                    }
                    if(current1!=start){
                        path.add(new Field(graph.graph.get(current1), Type.PATH));
                        graph.graph.get(current1).field.type=Type.PATH;
                        current1=previous1[current1];
                        i=true;
                    }
                }
                graph.graph.get(current1).field.type=Type.PATH;
                path.add(new Field(graph.graph.get(current1), Type.PATH));
                graph.graph.get(current2).field.type=Type.PATH;
                path.add(new Field(graph.graph.get(current2), Type.PATH));
                return true;
            }
            graph.graph.get(current2).field.type=Type.HIGHLIGHTED2;
            process.add(new Field(graph.graph.get(current2), Type.HIGHLIGHTED2));
            Collections.shuffle(graph.graph.get(current2).neighbors);
            for(int x : graph.graph.get(current2).neighbors){
                //System.out.println(x);
                if(graph.graph.get(x).field.type!=Type.WALL&&graph.graph.get(x).field.type!=Type.HIGHLIGHTED2) {
                    list2.add(x);
                    previous2[x] = current2;
                }
            }
        }
        return false;
    }
}
