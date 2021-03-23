package sample;

import java.util.ArrayList;
import java.util.List;

public class shelf {
    public int x;
    public int y;
    private List<String> type = new ArrayList<String>();
    private List<Integer> count_list = new ArrayList<Integer>();

    public shelf (int x, int y){
        this.x = x;
        this.y = y;
    }

    public int add_item(String name, int count) {
        if (!type.contains(name)) { //polozka nie je v zozname
            type.add(name);
            count_list.add(count);
            return 0;
        } else {
            int element = count_list.get(type.indexOf(name));
            element += count;
            count_list.set(type.indexOf(name), count);
        }
        return 0;
    }


    public int delete_item(String name, int count) {
        if (!type.contains(name)) {
            return 0;
        }
        int element = count_list.get(type.indexOf(name));
        element -= count;
        count_list.set(type.indexOf(name), element);
        return 0;
    }
}
