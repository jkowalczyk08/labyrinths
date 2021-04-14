package labyrinths.model;

public class Testing {
    public static void main(String[] Args){
        Labyrinth l=new Labyrinth(6,4);
        //System.out.println();
        l.getDefault();
        System.out.println(l.graph);
        //l.table[5][5]=Type.TARGET;
        System.out.println(l.perform("dfs"));
    }
}
