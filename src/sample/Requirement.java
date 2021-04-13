/*
  Class requirement
  @author Ladislav Dokoupil (xdokou14)
  @author Adrián Bobola (xbobol00)
 */
package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Requirement {
    List<String> requiredObjects = new ArrayList<>();
    List<Integer> requiredAmountList = new ArrayList<>();

    /**
     * Save requirement object and required amount into local structures requiredObjects, requiredAmountList
     * @param name   requirement name
     * @param amount required amount
     */
    public Requirement(String name, int amount) {
        if (!requiredObjects.contains(name)) { //neni v soupisu
            requiredObjects.add(name);
            requiredAmountList.add(amount);
        } else { //je v soupisu - najdem a upravim kusy
            int element = requiredAmountList.get(requiredObjects.indexOf(name));
            element += amount;
            requiredAmountList.set(requiredObjects.indexOf(name), element);
        }
    }
}
