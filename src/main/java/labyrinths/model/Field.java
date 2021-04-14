package labyrinths.model;

public class Field {
    int w;
    int h;
    Type type;
     public Field(Node n, Type t){
        this.w =n.w-1;
        this.h =n.h-1;
        this.type=t;
    }
    public Field(int x, int y, Type t){
        this.w =y;
        this.h =x;
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
    @Override
    public int hashCode(){
        return 31*w+h;
    }
    @Override
    public String toString(){
        return h + " " + w + " " + type;
    }
}
