package labyrinths.model;

public class Labyrinth {
    Graph graph;
    int start;
    int target;
    int width;
    int height;
    public int getWidth() {return width-2;}
    public int getHeight() {return height-2;}
    public Labyrinth(int height, int width){
        this.width=width+2;
        this.height=height+2;
        graph=new Graph(this.height, this.width);
    }
    public Result perform(String algorithm){
        return new Result(Dfs.startDfs(graph, start, target));
    }
    public Result getDefault(){
        Result res=new Result();
        res.add(new Field(0, 0, Type.START));
        start=width+1;
        target=width*(height-1)-2;
        res.add(new Field(height-2-1, width-2-1, Type.TARGET));
        for(int i=2; i<height-1; i+=2){
            for(int j=1; j<width-1; j++){
                if(!((j==1&&i%4==0)||(j==(width-2)&&(i%4==2)))){
                    //System.out.println(new Node(i, j))
                    graph.removeVertex(i, j);
                    res.add(new Field(i-1, j-1, Type.WALL));
                }
            }
        }
        return res;
    }
}