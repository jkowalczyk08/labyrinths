package labyrinths.model;

public class Node {
    int w;
    int h;
    Node(int x, int y){
        w=y;
        h=x;
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof Node){
            return ((Node) o).w == w && ((Node) o).h == h ;
        }
        return false;
    }
    @Override
    public int hashCode(){
        return 31*w+h;
    }
    @Override
    public String toString(){
        return h + " " + w;
    }
}
