package ija21;

public class blockWidth {
    public static int SCALE = 15;

    public static void setZOOM(Double zoom){
        SCALE = (int) (zoom * 15);
    }
}