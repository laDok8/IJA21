package sample;

import java.util.HashMap;
import java.util.Map;

public abstract class Storeable extends Cordinates {

    Map<String,Integer> stored = new HashMap<>();

    public Storeable(int x, int y) {
        super(x, y);
    }

    public void add_item(String name, int count) {
        if(count > 0) {
            int storedCount = stored.getOrDefault(name, 0);
            stored.put(name, storedCount + count);
        }
    }


    public int delete_item(String name, int count) {
        int storedCount = stored.getOrDefault(name,0);
        //some items remain in object
        int recived = 0;
        if(storedCount > count){
            stored.put(name,storedCount-count);
            return count;
        } else {
            stored.remove(name);
            return count-storedCount;
        }
    }

}
