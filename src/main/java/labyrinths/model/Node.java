package labyrinths.model;

import java.util.ArrayList;
import java.util.List;

public class Node {
    Field field;
    List<Integer> neighbors;
    Node(int x, int y, Type t){
        field=new Field(x, y, t);
        neighbors=new ArrayList<>();
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof Node){
            return ((Node) o).field.equals(field);
        }
        return false;
    }
    @Override
    public int hashCode(){
        return 31;
    }
    @Override
    public String toString(){
        return field.toString()+neighbors.toString();
    }
}
