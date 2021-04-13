/*
  Class Cordinates represent x and y coordinates of object
  @author Ladislav Dokoupil (xdokou14)
  @author Adrián Bobola (xbobol00)
 */

package sample;

import java.util.Objects;

public class Coordinates {
    int x, y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }
    public double getDistance(Coordinates cord){
        return Math.sqrt(Math.pow(this.x - cord.x,2) + Math.pow(this.y - cord.y,2));
    }
}
