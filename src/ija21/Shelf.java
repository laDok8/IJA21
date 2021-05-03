/*
  Class Shelf
  @author Ladislav Dokoupil (xdokou14)
  @author Adrián Bobola (xbobol00)
 */
package ija21;

public class Shelf extends Storeable{

    public Shelf(int x, int y){
        super(x,y);
    }
    public Shelf(Coordinates cord){
        super(cord, 0,1,0);
    }
}
