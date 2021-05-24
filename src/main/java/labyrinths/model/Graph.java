package labyrinths.model;

import java.util.*;

public class Graph {
    int width;
    int height;
    List<Node> graph;
    Graph(int height, int width){
        this.height=height;
        this.width=width;
        graph=new ArrayList<>();
        for(int i=0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(i!=0&&j!=0&&i!=height-1&&j!=width-1)
                    graph.add(new Node(i, j, Type.FREE));
                else
                    graph.add(new Node(i, j, Type.WALL));
            }
        }
        for(int i=1; i < height-1; i++) {
            for (int j = 1; j < width-1; j++) {
                if(i-1>0){
                    graph.get(indexOf(i, j)).neighbors.add(indexOf(i-1, j));
                }
                if(i+1<height-1){
                    graph.get(indexOf(i, j)).neighbors.add(indexOf(i+1, j));
                }
                if(j-1>0){
                    graph.get(indexOf(i, j)).neighbors.add(indexOf(i, j-1));
                }
                if(j+1<width-1){
                    graph.get(indexOf(i, j)).neighbors.add(indexOf(i, j+1));
                }
            }
        }
    }
    void addVertex(int h, int w){
        if(graph.get(indexOf(h, w)).field.type!=Type.WALL) return;
        if(h-1>0&&graph.get(indexOf(h-1, w)).field.type!=Type.WALL){
            graph.get(indexOf(h, w)).neighbors.add(indexOf(h-1, w));
            graph.get(indexOf(h-1, w)).neighbors.add(indexOf(h, w));
        }
        if(h+1<height-1&&graph.get(indexOf(h+1, w)).field.type!=Type.WALL){
            graph.get(indexOf(h, w)).neighbors.add(indexOf(h+1, w));
            graph.get(indexOf(h+1, w)).neighbors.add(indexOf(h, w));
        }
        if(w-1>0&&graph.get(indexOf(h, w-1)).field.type!=Type.WALL){
            graph.get(indexOf(h, w)).neighbors.add(indexOf(h, w-1));
            graph.get(indexOf(h, w-1)).neighbors.add(indexOf(h, w));
        }
        if(w+1<width-1&&graph.get(indexOf(h, w+1)).field.type!=Type.WALL){
            graph.get(indexOf(h, w)).neighbors.add(indexOf(h, w+1));
            graph.get(indexOf(h, w+1)).neighbors.add(indexOf(h, w));
        }
    }
    void addEdge(Node one, Node two){
        if(!one.neighbors.contains(indexOf(two)))
        one.neighbors.add(indexOf(two));
        if(!two.neighbors.contains(indexOf(one)))
            two.neighbors.add(indexOf(one));
    }
    void removeEdge(Node one, Node two){
            one.neighbors.remove(indexOf(two));
            two.neighbors.remove(indexOf(one));
    }
    void removeVertex(int h, int w){
        for(int nei : graph.get(indexOf(h, w)).neighbors){
            graph.get(nei).neighbors.remove(indexOf(h, w));
        }
        graph.get(indexOf(h, w)).neighbors.clear();
    }
    @Override
    public String toString(){
        return graph.toString();
    }
    Integer indexOf(Integer h, Integer w){
        return h*width+w;
    }
    Integer indexOf(Node n){
        return indexOf(n.field.h, n.field.w);
    }
}
