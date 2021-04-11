package labyrinths.model;

public class Field {
    int x;
    int y;
    Type type;
    Field(int x, int y, Type t){
        this.x=x;
        this.y=y;
        this.type=t;
    }
}
