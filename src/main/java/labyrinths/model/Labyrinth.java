package labyrinths.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Labyrinth {
    Graph graph;
    int start;
    int target;
    int width;
    int height;
    int torch;
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
    public Result up(){
        if(graph.graph.get(start-width).field.type==Type.WALL){
            return new Result();
        }
        Result res=setStart(start/width-1-1, start%width-1);
        if(start-width==target){
            res.win=true;
            return res;
        }
        else{
            if(start==torch){
                torch=0;
                res.vision=true;
            }

            return res;
        }
    }
    public Result down(){
        if(graph.graph.get(start+width).field.type==Type.WALL){
            return new Result();
        }
        Result res=setStart(start/width, start%width-1);
        if(start+width==target){
            res.win=true;
            return res;
        }
        else{
            if(start==torch){
                torch=0;
                res.vision=true;
            }
            return res;
        }
    }
    public Result right(){
        if(graph.graph.get(start+1).field.type==Type.WALL){
            return new Result();
        }
        Result res=setStart(start/width-1, start%width);
        if(start+1==target){
            res.win=true;
            return res;
        }
        else{
            if(start==torch){
                torch=0;
                res.vision=true;
            }
            return res;
        }
    }
    public Result left(){
        if(graph.graph.get(start-1).field.type==Type.WALL){
            return new Result();
        }
        Result res=setStart(start/width-1, start%width-2);
        if(start-1==target){
            res.win=true;
            return res;
        }
        else{
            if(start==torch){
                torch=0;
                res.vision=true;
            }
            return res;
        }
    }
    public Result getRandomLabyrinth(){
        teleports.clear();
        Result res=new Result();
        res.add(new Field(start/width-1, start%width-1, Type.FREE));
        res.add(new Field(target/width-1, target%width-1, Type.FREE));
        start=0;
        target=0;
        for(int i=0; i<height-2; i++){
            for(int j=0; j<width-2; j++)
                res.add(new Field(i, j, Type.WALL));
        }
        graph=new Graph(this.height, this.width, 1);
        int current=5*width+5;
        List<Integer> toVisit=new ArrayList<>();
        graph.addVertex(current/width, current%width);
        res.add(new Field(current/width-1, current%width-1, Type.FREE));
        if(graph.checkIfOK(current+2))
            toVisit.add(current+2);
        if(graph.checkIfOK(current-2))
            toVisit.add(current-2);
        if(graph.checkIfOK(current+2*width))
            toVisit.add(current+2*width);
        if(graph.checkIfOK(current-2*width))
            toVisit.add(current-2*width);
        while(!toVisit.isEmpty()){
            List<Integer>myOptions=new ArrayList<>();
            Collections.shuffle(toVisit);
            current=toVisit.remove(0);
            if(graph.checkIfOK(current+2)&&graph.graph.get(current+2).field.type==Type.FREE)
                myOptions.add(current+2);
            if(graph.checkIfOK(current-2)&&graph.graph.get(current-2).field.type==Type.FREE)
                myOptions.add(current-2);
            if(graph.checkIfOK(current+2*width)&&graph.graph.get(current+2*width).field.type==Type.FREE)
                myOptions.add(current+2*width);
            if(graph.checkIfOK(current-2*width)&&graph.graph.get(current-2*width).field.type==Type.FREE)
                myOptions.add(current-2*width);
            Collections.shuffle(myOptions);
            Integer next=myOptions.remove(0);
            next=(next+current)/2;
            graph.addVertex(next/width, next%width);
            res.add(new Field(next/width-1, next%width-1, Type.FREE));
            graph.addVertex(current/width, current%width);
            res.add(new Field(current/width-1, current%width-1, Type.FREE));
            if(graph.checkIfOK(current+2)&&graph.graph.get(current+2).field.type==Type.WALL&&!toVisit.contains(current+2))
                toVisit.add(current+2);
            if(graph.checkIfOK(current-2)&&!toVisit.contains(current-2)&&graph.graph.get(current-2).field.type==Type.WALL)
                toVisit.add(current-2);
            if(graph.checkIfOK(current+2*width)&&!toVisit.contains(current+2*width)&&graph.graph.get(current+2*width).field.type==Type.WALL)
                toVisit.add(current+2*width);
            if(graph.checkIfOK(current-2*width)&&!toVisit.contains(current-2*width)&&graph.graph.get(current-2*width).field.type==Type.WALL)
                toVisit.add(current-2*width);
        }
        if(graph.graph.get(width+1).field.type==Type.FREE){
            setStart(0,0);
            res.add(new Field(0, 0, Type.START));
        }else
        if(graph.graph.get(width+2).field.type==Type.FREE){
            setStart(0,1);
            res.add(new Field(0, 1, Type.START));
        }else
        if(graph.graph.get(2*width+1).field.type==Type.FREE){
            setStart(1,0);
            res.add(new Field(1, 0, Type.START));
        }else
        if(graph.graph.get(width*2+2).field.type==Type.FREE){
            setStart(1,1);
            res.add(new Field(1, 1, Type.START));
        }

        if(width%2==0){
            for(int i=1; i<height-1; i+=2){
                graph.addVertex(i, width-2);
                res.add(new Field(i-1, width-3, Type.FREE));
            }
        }
        if(height%2==0){
            for(int i=1; i<width-1; i+=2){
                graph.addVertex(height-2, i);
                res.add(new Field(height-3, i-1, Type.FREE));
            }
        }
        if(graph.graph.get(width*2+2).field.type==Type.WALL) {
            //graph.graph.get(width*2+2).field.type=Type.FREE;
            graph.addVertex(height-2, width-2);
            res.add(new Field(height-3, width-3, Type.FREE));
        }
        setTarget(height-3,width-3);
        res.add(new Field(height-3, width-3, Type.TARGET));
        Random rand = new Random();
        int n = rand.nextInt(height/2-1);
        int m = rand.nextInt(width/2-1);
        torch = (n*2+1)*width+m*2+1;
        res.add(new Field(n*2, m*2, Type.VISION));
        return res;
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
    Result getDefault(){
        Result res=new Result(Stream.of(setStart(0,0).changes,(setTarget(width-2, height-2).changes)).flatMap(Collection::stream)
                .collect(Collectors.toList()));
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
                return new Result(Stream.of(setStart(0,0).changes,(setTarget(height-3, width-3).changes)).flatMap(Collection::stream)
                    .collect(Collectors.toList()));
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
        graph.graph.get(graph.indexOf(1,1)).field.type=Type.START;
        target=width*(height-1)-2;
        res.add(new Field(height-2-1, width-2-1, Type.TARGET));
        graph.graph.get(graph.indexOf(height-2,width-2)).field.type=Type.TARGET;
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
        if(teleports.contains(graph.indexOf(h+1, w+1))||start==graph.indexOf(h+1, w+1)||target==graph.indexOf(h+1, w+1))
            return result;
        if(graph.graph.get(graph.indexOf(h+1, w+1)).field.type==Type.FREE){
            result.add(new Field(h, w, Type.WALL));
            graph.removeVertex(h+1, w+1);
        }
        else if(graph.graph.get(graph.indexOf(h+1, w+1)).field.type==Type.WALL){
            result.add(new Field(h, w, Type.FREE));
            graph.addVertex(h+1, w+1);
        }
        return result;
    }
    public Result setStart(int h, int w){
        Result result=new Result();
        if(target==graph.indexOf(h+1, w+1)||graph.graph.get(graph.indexOf(h+1, w+1)).field.type==Type.WALL)
            return result;
        if(start!=0){
            if(teleports.contains(start)){
                result.add(new Field(graph.graph.get(start), Type.FREE));
                result.add(new Field(graph.graph.get(start), Type.TELEPORT));
                graph.graph.get(start).field.type= Type.FREE;
            }
            else{
                result.add(new Field(graph.graph.get(start), Type.FREE));
                graph.graph.get(start).field.type= Type.FREE;
            }
        }
        start=graph.indexOf(h+1, w+1);
        result.add(new Field(h, w, Type.START));
        graph.graph.get(start).field.type= Type.START;
        return result;
    }
    public Result setTarget(int h, int w){
        Result result=new Result();
        if(start==graph.indexOf(h+1, w+1)||graph.graph.get(graph.indexOf(h+1, w+1)).field.type==Type.WALL)
            return result;
        if(target!=0) {
            if (teleports.contains(target)) {
                result.add(new Field(graph.graph.get(target), Type.FREE));
                result.add(new Field(graph.graph.get(target), Type.TELEPORT));
                graph.graph.get(target).field.type = Type.FREE;
            } else {
                result.add(new Field(graph.graph.get(target), Type.FREE));
                graph.graph.get(target).field.type = Type.FREE;
            }
        }
        target=graph.indexOf(h+1, w+1);
        result.add(new Field(h, w, Type.TARGET));
        graph.graph.get(target).field.type= Type.TARGET;
        return result;
    }
    public Result setTeleport(int h, int w){
        int f=graph.indexOf(h+1,w+1);
        if(graph.graph.get(f).field.type==Type.WALL)return new Result();
        if(teleports.contains(f)){
            graph.removeVertex(h+1,w+1);
            graph.addVertex(h+1,w+1);
            teleports.remove(teleports.indexOf(f));
            Result result=new Result();
            result.add(new Field(h, w, Type.FREE));
            if(f==start)
                result.add(new Field(h, w, Type.START));
            else if(f==target)
                result.add(new Field(h, w, Type.TARGET));
            return result;
        }
        for(Integer i : teleports){
            graph.addEdge(f, i);
        }
        teleports.add(f);
        graph.graph.get(f).field.type= Type.TELEPORT;
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
        if(start!=0)
            res.add(new Field(start/ width - 1, start % width - 1, Type.START));
        if(target!=0)
            res.add(new Field(target/ width - 1, target % width - 1, Type.TARGET));
        for(Integer i : teleports){
            res.add(new Field(i/ width - 1, i % width - 1, Type.TELEPORT));
        }
        return res;
    }

    public Result getInitialResult(String s) throws FileFormatException {
        Result res=new Result();
        int i=0;
        while(i<s.length()) {
            int j=s.charAt(i);

            if((char)j=='x') {
                if(i % width == width-1 || i % width == width - 2) {
                    throw new FileFormatException();
                }

                graph.removeVertex(i / width +1, i % width+1);
                res.add(new Field(i / width , i % width , Type.WALL));
            }
            else if((char)j=='s') {
                if(i % width == width-1 || i % width == width - 2) {
                    throw new FileFormatException();
                }

                start=graph.indexOf(i / width +1, i % width +1);
                res.add(new Field(i / width , i % width , Type.START));
            }
            else if((char)j=='f') {
                if(i % width == width-1 || i % width == width - 2) {
                    throw new FileFormatException();
                }

                target = graph.indexOf(i / width +1, i % width +1);
                res.add(new Field(i / width , i % width , Type.TARGET));
            }
            else if((char)j=='t') {
                if(i % width == width-1 || i % width == width - 2) {
                    throw new FileFormatException();
                }

                int f=graph.indexOf(i / width +1, i % width +1);
                for(Integer t : teleports){
                    graph.addEdge(f, t);
                }
                teleports.add(f);
                res.add(new Field(i / width, i % width, Type.TELEPORT));
            }
            else if((char)j==':') {
                if(i % width != width-1 && i % width != width - 2) {
                    throw new FileFormatException();
                }
            }
            else if((char)j=='o') {
                if(i % width == width-1 || i % width == width - 2) {
                    throw new FileFormatException();
                }
            }
            else {
                throw new FileFormatException();
            }
            i++;
        }
        return res;
    }

    // changes labyrinth to string representation which can later be saved: height;width;fields
    public String getSaveString() {
        StringBuilder stringRepresentation = new StringBuilder();
        int h = getHeight();
        int w = getWidth();
        int index;
        Field data;
        char nextEl;
        stringRepresentation.append(h);
        stringRepresentation.append(';');
        stringRepresentation.append(w);
        stringRepresentation.append(';');

        for(int i = 1; i < h+1; i++) {
            for(int j = 1; j < w+1; j++) {
                index = graph.indexOf(i,j);
                data = graph.graph.get(index).field;
                if(data.type == Type.WALL) {
                    nextEl = 'x';
                }
                else if(index == start) {
                    nextEl = 's';
                }
                else if(index == target) {
                    nextEl = 'f';
                }
                else if(teleports.contains(index)) {
                    nextEl = 't';
                }
                else {
                    nextEl = 'o';
                }
                stringRepresentation.append(nextEl);
            }

            if(i != h) {
                // append this instead of '\n' because '\n' causes problems
                stringRepresentation.append("::");
            }
        }
        return stringRepresentation.toString();
    }

}