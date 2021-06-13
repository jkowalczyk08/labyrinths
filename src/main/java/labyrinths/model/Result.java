package labyrinths.model;

import java.util.ArrayList;
import java.util.List;

public class Result {
    List<Field> changes;
    boolean win = false;
    boolean vision = false;

    public Result(){
        changes= new ArrayList<>();
    }
    public Result(List<Field> x){
        changes=x;
    }
    void add(Field f){
        changes.add(f);
    }
    public List<Field> getChanges() {return changes;}
    public boolean getWin() {return win;}
    public boolean getVision() {return vision;}
    @Override
    public String toString(){
        return changes.toString();
    }
}
