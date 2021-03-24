package sample;

import java.util.ArrayList;
import java.util.List;

public class Shelf extends Storeable{

    public Shelf(int x, int y){
        super(x,y);
    }
    public Shelf(Cordinates cord){
        super(cord.x, cord.y);
    }
}
