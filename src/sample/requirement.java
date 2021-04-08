package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class requirement {
    private List<String> item = new ArrayList<String>();
    private List<Integer> count_list = new ArrayList<Integer>();

    /**
     * Vytvori novu poziadavku
     * @param name - meno polozky
     * @param count - pocet ks danej polozky
     */
    public requirement(String name, int count){
        if (!item.contains(name)) { //neni v soupisu
            item.add(name);
            count_list.add(count);
        } else { //je v soupisu - najdem a upravim kusy
            int element = count_list.get(item.indexOf(name));
            element+=count;
            count_list.set(item.indexOf(name), element);
        }
    }
 }
