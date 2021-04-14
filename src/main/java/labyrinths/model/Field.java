package labyrinths.model;

public class Field {
    int w;
    int h;
    Type type;
    Field(int x, int y, Type t){
        this.w =x;
        this.h =y;
        this.type=t;
    }
    void setState(Type t){
        type=t;
    }
    public Type getType() {return type;}
    public int getW() {return w;}
    public int getH() {return h;}
    @Override
    public boolean equals(Object o){
        if(o instanceof Field){
            return ((Field) o).w == w && ((Field) o).h == h && ((Field) o).type==type;
        }
        return false;
    }
}
