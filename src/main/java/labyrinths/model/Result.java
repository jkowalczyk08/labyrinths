package labyrinths.model;

import java.util.ArrayList;
import java.util.List;

public class Result {
    List<Field> changes;
    Result(){
        changes= new ArrayList<>();
    }
    Result(List<Field> x){
        changes=x;
    }
    void add(Field f){
        changes.add(f);
    }
    public List<Field> getChanges() {return changes;}
    @Override
    public String toString(){
        return changes.toString();
    }
}
