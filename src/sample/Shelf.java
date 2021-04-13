/*
  Class Shelf
  @author Ladislav Dokoupil (xdokou14)
  @author Adrián Bobola (xbobol00)
 */
package sample;

public class Shelf extends Storeable{

    public Shelf(int x, int y){
        super(x,y);
    }
    public Shelf(Coordinates cord){
        super(cord.x, cord.y);
    }
}
