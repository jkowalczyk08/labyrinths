package labyrinths.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Graph {
    Map<Node, LinkedList<Node>> graph;
    Graph(int height, int width,  Type[][] tab){
        graph=new HashMap<>();
        for(int i=1; i < height-1; i++) {
            for (int j = 1; j < width-1; j++) {
                if(tab[i][j]!=Type.WALL)
                addVertex(new Node(i, j));
            }
        }
        for(int i=1; i<height-1; i++) {
            for (int j = 1; j < width-1; j++) {
                if(i-1>=0&&graph.containsKey(new Node(i-1, j))){
                    this.addEdge(new Node(i, j), new Node(i-1, j));
                }
                if(i+1<height&&graph.containsKey(new Node(i+1, j))){
                    this.addEdge(new Node(i, j), new Node(i+1, j));
                }
                if(j-1>=0&&graph.containsKey(new Node(i, j-1))){
                    this.addEdge(new Node(i, j), new Node(i, j-1));
                }
                if(j+1<width&&graph.containsKey(new Node(i, j+1))) {
                    this.addEdge(new Node(i, j), new Node(i, j + 1));
                }
            }
        }
    }
    void addVertex(Node x){
        graph.put(x, new LinkedList<>());
        //graph.get(x).add(x);System.out.println(graph.get(x));
    }
    void addEdge(Node one, Node two){
       // System.out.println(one);
        if(!graph.get(one).contains(two)){
            graph.get(one).add(two);
        }
        if(!graph.get(two).contains(one)){
            graph.get(two).add(one);
        }
        //System.out.println(graph.get(one));
    }
    void removeEdge(Node one, Node two){
        //graph.get(one).remove(two);
        graph.get(two).remove(one);
    }
    void removeVertex(Node vertex){
        if(!graph.containsKey(vertex)) return;
        //System.out.println(vertex.toString() + graph.get(vertex) );
        for(Node x : graph.get(vertex)){
            this.removeEdge(vertex, x);
        }
        graph.remove(vertex, graph.get(vertex));
    }
    @Override
    public String toString(){
        return graph.toString();
    }
}
