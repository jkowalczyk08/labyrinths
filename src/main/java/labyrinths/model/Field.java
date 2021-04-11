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
    void setState(Type t){
        type=t;
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof Field){
            return ((Field) o).x==x && ((Field) o).y==y && ((Field) o).type==type;
        }
        return false;
    }
}
