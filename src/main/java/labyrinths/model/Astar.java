package labyrinths.model;

import labyrinths.model.Field;
import labyrinths.model.Graph;
import labyrinths.model.Type;

import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class Astar{
    public static List<Field> startAlgorithm(Graph graph, int start, int target){
        if(start==0)return new LinkedList<>();
        List<Field> process=new LinkedList<>();
        List<Field> path=new LinkedList<>();
        if(!runA(graph, start, target, process, path) )return process;
        process.addAll(path);
        return process;
    }
    static boolean runA(Graph graph, int start, int target, List<Field> process, List<Field>path){
        Integer current;
        int h=target/graph.width;
        int w=target%graph.width;
        Set<Integer>open = new HashSet<>();
        open.add(start);
        Integer[] previous=new Integer[graph.height* graph.width];
        Integer[] cost=new Integer[graph.height* graph.width];
        Integer[] costb=new Integer[graph.height* graph.width];
        for(int i=0; i<cost.length; i++){
            costb[i]=abs(h-i/graph.width)+abs(w-i%graph.width);
            //System.out.println(i/graph.width+" "+i%graph.width+" "+costb[i]);
        }
        cost[start]=costb[start];
        Comparator<Integer> minComparator = new Comparator<Integer>() {

            @Override
            public int compare(Integer i, Integer j) {
                if(cost[i].compareTo(cost[j])==0) return costb[i].compareTo(costb[j]); return cost[i].compareTo(cost[j]);
            }
        };
        while(!open.isEmpty()){
            //open.stream().forEach(i->System.out.println(cost[i]+" "+costb[i]));

            current=open.stream().min(minComparator).get();
            graph.graph.get(current).field.type=Type.HIGHLIGHTED;
            open.remove(current);

            //System.out.println("***"+cost[current]+" "+costb[current]);
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
            for(int x : graph.graph.get(current).neighbors){
                //System.out.println(x);
                if(graph.graph.get(x).field.type==Type.FREE||graph.graph.get(x).field.type==Type.TELEPORT||graph.graph.get(x).field.type==Type.TARGET) {
                    if(open.contains(x)&&cost[x]>costb[x]+1+cost[current]-costb[current]){
                        cost[x]=costb[x]+cost[current]-costb[current]+1;
                        previous[x] = current;
                    }
                    if(!open.contains(x)){
                        previous[x] = current;
                        open.add(x);
                        cost[x]=costb[x]+cost[current]-costb[current]+1;
                    }
                    //System.out.println(cost[x]);
                    //System.out.println(x/graph.width+" "+x%graph.height+" "+costb[x]);
                }
            }
        }
        return false;
    }
}
