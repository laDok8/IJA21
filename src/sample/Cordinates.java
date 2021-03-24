package sample;

import java.util.Objects;

public class Cordinates {
    int x, y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cordinates that = (Cordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Cordinates(int x, int y){
        this.x = x;
        this.y = y;
    }
    public double getDistance(Cordinates cord){
        return Math.sqrt(Math.pow(this.x - cord.x,2) + Math.pow(this.y - cord.y,2));
    }
}
