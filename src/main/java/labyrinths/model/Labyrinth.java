package labyrinths.model;

public class Labyrinth {
    Type[][] table;
    Graph graph;
    Node start;
    int width;
    int height;
    public Labyrinth(int height, int width){
        table= new Type[height+2][width+2];
        this.width=width+2;
        this.height=height+2;
        for(int i=0; i<height+2; i++){
            for(int j=0; j<width+2; j++){
                if(i==0||j==0||i==(this.height-1)||j==this.width-1)
                    table[i][j]= Type.WALL;
                else
                    table[i][j]=Type.FREE;
            }
        }
        graph=new Graph(this.height, this.width, table);
    }
    public Result perform(String algorithm){
        Dfs alg=new Dfs();
        Result r=new Result(Dfs.startDfs(graph, table, start));
        return r;
    }
    public Result getDefault(){
        Result res=new Result();
        table[1][1]=Type.START;
        res.add(new Field(0, 0, Type.START));
        start=new Node(1, 1);
        table[height-2][width-2]=Type.TARGET;
        res.add(new Field(height-2-1, width-2-1, Type.TARGET));
        for(int i=2; i<height-1; i+=2){
            for(int j=1; j<width-1; j++){
                if(!((j==1&&i%4==0)||(j==(width-2)&&(i%4==2)))){
                    table[i][j]=Type.WALL;
                    //System.out.println(new Node(i, j));
                    graph.removeVertex(new Node(i, j));
                    res.add(new Field(i-1, j-1, Type.WALL));
                }
            }
        }

        return res;
    }
}