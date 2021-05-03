/*
  Class Cordinates represent x and y coordinates of object
  @author Ladislav Dokoupil (xdokou14)
  @author Adrián Bobola (xbobol00)
 */

package ija21;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.swing.*;
import java.util.Objects;

public class Coordinates extends Rectangle {


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return this.getX() == that.getX() && getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    /**
     * cordinate position
     * @param x position on horizontal axis
     * @param y position on vertical axis
     */

    int scale = 1;

    public Coordinates(int x, int y){
        super(10,10, Color.BLACK);
        this.setX(x*scale);
        this.setY(y*scale);
    }

    public Coordinates(Coordinates cord, double R, double G, double B){
        super(10,10, Color.color(R, G, B));
        this.setX((int)cord.getX()*scale);
        this.setY((int)cord.getY()*scale);
    }

    /**
     * get straight line distance between two cordinates
     * @param cord 2.nd cordinate
     * @return distance of cordinates
     */
    public double getDistance(Coordinates cord){
        return Math.sqrt(Math.pow(this.getX() - cord.getX(),2) + Math.pow(this.getY() - cord.getY(),2));
    }
}
