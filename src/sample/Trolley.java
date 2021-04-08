package sample;

import java.util.HashMap;
import java.util.Map;

public class Trolley extends Storeable {
    private int id, capacity, items_in;
    Integer[][][] newPosition = new Integer[100][100][2]; //TODO: Mala by stacit velkost [5][100][2] kedze 5 poloziek/vozik max
    public Map.Entry<String, Integer> task;
    Map<String, Integer> trolleyItems = new HashMap<>();

    /**
     * @param id_in - identifikacia vozika
     */
    public Trolley(int id_in, Cordinates cord) {
        super(cord.x, cord.y);
        this.id = id_in;
        this.capacity = 5;
        this.items_in = 0;

    }

    /**
     * Vrati ID vozika
     *
     * @return ID vozika
     */
    public int getId() {
        return id;
    }

    public int getItemInCount() {
        return stored.size();
    }

    public void moveX(int direction) {
        //if (Math.abs(direction) == 1){
        if (direction == 1) {
            this.x += direction;
        }
        if (direction == -1) {
            this.x += direction;
        }
    }

    public void moveY(int direction) {
        if (direction == 1) {
            this.y += direction;
        }
        if (direction == -1) {
            this.y += direction;
        }
    }

    public void addNewPosition(int itemNumber, int moveCount, int x, int y) {
        this.newPosition[itemNumber][moveCount][0] = x;
        this.newPosition[itemNumber][moveCount][1] = y;
    }

    public int itemsAmount() {
        return this.trolleyItems.size();
    }

    public void addItem(String name, int amount) {
        this.trolleyItems.put(name, amount);
    }

    public void deleteItem(String name) {
        this.trolleyItems.remove(name);
    }
}
