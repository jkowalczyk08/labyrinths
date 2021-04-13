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
    public Type getType() {return type;}
    public int getX() {return x;}
    public int getY() {return y;}
    @Override
    public boolean equals(Object o){
        if(o instanceof Field){
            return ((Field) o).x==x && ((Field) o).y==y && ((Field) o).type==type;
        }
        return false;
    }
}
