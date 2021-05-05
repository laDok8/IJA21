/*
  Class Trolley represent a trolley in the map.
  @author Ladislav Dokoupil (xdokou14)
  @author Adrián Bobola (xbobol00)
 */

package ija21;

import java.util.List;
import java.util.Map;

public class Trolley extends Storeable {
    private final int id;
    private static final int capacity = 5;
    public boolean unload;

    public Map.Entry<String, Integer> task;
    public List<Coordinates> pathList;

    /**
     * Save trolley data into local structures task
     * @param id_in trolley ID
     * @param cord  trolley coordinates (contains X and Y)
     */
    public Trolley(int id_in, Coordinates cord) {
        super(cord, 0,0,1);
        this.id = id_in;
        unload = false;
    }

    /**
     * get trolley ID
     * @return trolley ID
     */
    public int getid() {
        return id;
    }

    /**
     * get amount of items in trolley
     * @return amount of items
     */
    public int getItemInCount() {
        int sum = 0;
        for (int count : this.getStored().values() ) {
            sum+=count;
        }
        return sum;
    }

    public void unloadGoods(){
        for (Map.Entry<String, Integer> item: this.getStored().entrySet()) {
            this.delete_item(item.getKey(),item.getValue());
        }
        this.unload = false;
    }


    /**
     * get trolley capacity
     * @return trolley capacity
     */
    public static int getCapacity() {
        return capacity;
    }

}
