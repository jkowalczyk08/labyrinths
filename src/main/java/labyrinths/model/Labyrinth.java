package labyrinths.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Labyrinth {
    Graph graph;
    int start;
    int target;
    int width;
    int height;
    List<Integer> teleports;
    public int getWidth() {return width-2;}
    public int getHeight() {return height-2;}
    public Labyrinth(int height, int width){
        this.width=width+2;
        this.height=height+2;
        teleports=new ArrayList<>();
        start=0;
        target=0;
        graph=new Graph(this.height, this.width);
    }
    public Result perform(String algorithm){
            if(algorithm.equals("DFS"))
                return new Result(Dfs.startAlgorithm(graph, start, target));
            if(algorithm.equals("BFS"))
                return new Result(Bfs.startAlgorithm(graph, start, target));
            if(algorithm.equals("Astar"))
                return new Result(Astar.startAlgorithm(graph, start, target));
            if(algorithm.equals("BidirectionalBFS"))
                return new Result(BidirectionalBFS.startAlgorithm(graph, start, target));
        if(algorithm.equals("GreedyBestFirstSearch"))
            return new Result(GreedyBestFirstSearch.startAlgorithm(graph, start, target));
            return new Result();
    }
    public List<String> availableAlgorithms(){
        List<String> a=new ArrayList<>();
        a.add("DFS");
        a.add("BFS");
        a.add("Astar");
        a.add("BidirectionalBFS");
        a.add("GreedyBestFirstSearch");
        return a;
    }
    Result getFromString(String s){
        Result res=new Result();
        res.add(new Field(0, 0, Type.START));
        start=width+1;
        target=width*(height-1)-2;
        res.add(new Field(height-2-1, width-2-1, Type.TARGET));
        int i=0;
        while(i<s.length()) {
            int j=s.charAt(i);
            if((char)j=='x') {
                graph.removeVertex(i / width +1, i % width+1);
                res.add(new Field(i / width , i % width , Type.WALL));
            }
            i++;
        }
        return res;
    }
    Result getDefault(){
        Result res=new Result();
        res.add(new Field(0, 0, Type.START));
        start=width+1;
        target=width*(height-1)-2;
        res.add(new Field(height-2-1, width-2-1, Type.TARGET));
        for(int i=2; i<height-1; i+=2){
            for(int j=1; j<width-1; j+=2){
                if(!(i==height-2&&j==width-2)){
                    graph.removeVertex(i, j);
                    res.add(new Field(i-1, j-1, Type.WALL));
                }
            }
        }
        return res;
    }
    Result getSnake(){
        Result res=new Result();
        res.add(new Field(0, 0, Type.START));
        start=width+1;
        target=width*(height-1)-2;
        res.add(new Field(height-2-1, width-2-1, Type.TARGET));
        for(int i=2; i<height-1; i+=2){
            for(int j=1; j<width-1; j++){
                if(!((j==1&&i%4==0)||(j==(width-2)&&(i%4==2)))){
                    graph.removeVertex(i, j);
                    res.add(new Field(i-1, j-1, Type.WALL));
                }
            }
        }
        return res;
    }
    public Result getPreset(LabyrinthPreset presetType) {
        switch (presetType) {
            case EMPTY:
                return new Result();
            case SNAKE:
                return getSnake();
            case DEFAULT:
                return getDefault();
            case PRESET_10x10:
                return getDefault("src/main/resources/textFiles/sampleLabyrinth10x10.txt");
            case PRESET_15x30:
                return getDefault("src/main/resources/textFiles/sampleLabyrinth15x30(2).txt");
            case PRESET_20x40:
                return getDefault("src/main/resources/textFiles/sampleLabyrinth20x40.txt");
        }
        return new Result();
    }
    Result getDefault(String s){
        Result res=new Result();
        res.add(new Field(0, 0, Type.START));
        start=width+1;
        target=width*(height-1)-2;
        res.add(new Field(height-2-1, width-2-1, Type.TARGET));
        FileReader fr= null;
        try {
            fr = new FileReader(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int i=0;
        int j=0;
        while(true) {
            try {
                assert fr != null;
                if ((j = fr.read()) == -1) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if((char)j=='x') {
                graph.removeVertex(i / width +1, i % width+1);
                res.add(new Field(i / width , i % width , Type.WALL));
            }
            i++;
        }
        try {
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
    public Result addWall(int h, int w){
        Result result=new Result();
        if(graph.graph.get(graph.indexOf(h+1, w+1)).field.type!=Type.WALL){
            result.add(new Field(h, w, Type.WALL));
            graph.removeVertex(h+1, w+1);
        }
        else{
            result.add(new Field(h, w, Type.FREE));
            graph.addVertex(h+1, w+1);
        }
        return result;
    }
    public Result setStart(int h, int w){
        Result result=new Result();
        if(start!=0)
            result.add(new Field(graph.graph.get(start), Type.FREE));
        start=graph.indexOf(h+1, w+1);
        result.add(new Field(h, w, Type.START));
        return result;
    }
    public Result setTarget(int h, int w){
        Result result=new Result();
        if(target!=0)
            result.add(new Field(graph.graph.get(target), Type.FREE));
        target=graph.indexOf(h+1, w+1);
        result.add(new Field(h, w, Type.TARGET));
        return result;
    }
    public Result setTeleport(int h, int w){
        int f=graph.indexOf(h+1,w+1);
        if(teleports.contains(f)){
            graph.removeVertex(h+1,w+1);
            graph.addVertex(h+1,w+1);
            teleports.remove(teleports.indexOf(f));
            Result result=new Result();
            if(f==start)
                result.add(new Field(h, w, Type.START));
            else if(f==target)
                result.add(new Field(h, w, Type.TARGET));
            else result.add(new Field(h, w, Type.FREE));
            return result;
        }
        for(Integer i : teleports){
            graph.addEdge(f, i);
        }
        teleports.add(f);
        Result result=new Result();
        result.add(new Field(h, w, Type.TELEPORT));
        return result;
    }
    public Result getClear(){
        Result res=new Result();
        for(int i=0; i<width*height; i++){
            if(graph.graph.get(i).field.type==Type.HIGHLIGHTED2||graph.graph.get(i).field.type==Type.HIGHLIGHTED||graph.graph.get(i).field.type==Type.PATH) {
                res.add(new Field(i / width - 1, i % width - 1, Type.FREE));
                graph.graph.get(i).field.type = Type.FREE;
            }
        }
        res.add(new Field(start/ width - 1, start % width - 1, Type.START));
        res.add(new Field(target/ width - 1, target % width - 1, Type.TARGET));
        return res;
    }

}