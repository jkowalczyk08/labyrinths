package labyrinths.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Graph {
    Map<Field, LinkedList<Field>> graph;
    Graph(int width, int height, Type[][] tab){
        graph=new HashMap<>();
        for(int i=0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(tab[i][j]!=Type.WALL)
                addVertex(new Field(i, j, tab[i][j]));
            }
        }
        for(int i=0; i<height; i++) {
            for (int j = 0; j < width; j++) {
                if(i-1>=0&&graph.containsKey(new Field(i-1, j, tab[i-1][j]))){
                    this.addEdge(new Field(i, j, tab[i][j]), new Field(i-1, j, tab[i-1][j]));
                }
                if(i+1<height&&graph.containsKey(new Field(i+1, j, tab[i+1][j]))){
                    this.addEdge(new Field(i, j, tab[i][j]), new Field(i+1, j, tab[i+1][j]));
                }
                if(j-1>=0&&graph.containsKey(new Field(i, j-1, tab[i][j-1]))){
                    this.addEdge(new Field(i, j, tab[i][j]), new Field(i, j-1, tab[i][j-1]));
                }
                if(j+1<width&&graph.containsKey(new Field(i, j+1, tab[i][j+1]))) {
                    this.addEdge(new Field(i, j, tab[i][j]), new Field(i, j + 1, tab[i][j + 1]));
                }
            }
        }
    }
    void addVertex(Field x){
        graph.put(x, new LinkedList<>());
    }
    void addEdge(Field one, Field two){
        if(!graph.get(one).contains(two)){
            graph.get(one).add(two);
        }
        if(!graph.get(two).contains(one)){
            graph.get(two).add(one);
        }
    }
    void removeEdge(Field one, Field two){
        graph.get(one).remove(two);
        graph.get(two).remove(one);
    }
    void removeVertex(Field vertex){
        if(!graph.containsKey(vertex)) return;
        for(Field x : graph.get(vertex)){
            this.removeEdge(vertex, x);
        }
        graph.remove(vertex);
    }
}
