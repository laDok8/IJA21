package ija21;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.*;

public class MainLoop extends TimerTask {

    JsonParser jsonParser;
    Map<String, Integer> requirements;
    FindPath findPath;

    public MainLoop(JsonParser jsonParser, Map<String, Integer> requirements, FindPath findPath) {
        this.jsonParser = jsonParser;
        this.requirements = requirements;
        this.findPath = findPath;
    }

    @Override
    public void run() {
        //iterate trolleys
        for (Trolley trolley : jsonParser.getTrolleys()) {
            //unload goods
            if (trolley.getRemainCapacity() == 0 || (trolley.task == null && trolley.getItemInCount() > 0)) {
                trolley.unload = true;
            }

            //set task to empty trolleys
            if (trolley.task == null && requirements.size() > 0) {
                Map.Entry<String, Integer> tmp;
                tmp = requirements.entrySet().iterator().next();
                trolley.task = new AbstractMap.SimpleEntry<>(tmp.getKey(), tmp.getValue());
                requirements.remove(tmp.getKey());
            }
            //ending condition
            if (requirements.size() == 0 && jsonParser.getTrolleys().stream().allMatch(trolley1 -> trolley1.task == null)
                    && jsonParser.getTrolleys().stream().allMatch(trolley1 -> trolley1.getItemInCount() == 0)) {
                //all trolleys finished
                this.cancel();
                return;
            } else if (requirements.size() == 0 && trolley.task == null && !trolley.unload) {
                //wait for other to finish
                return;
            }

                //sort shelfs by distance
                TreeMap<Double, Coordinates> sortedByDistances = new TreeMap<>();

                //path to unload
                if (trolley.unload) {
                    // shelves with stages sorted by distance
                    sortedByDistances.clear();
                    for (Coordinates entry : jsonParser.getStages()) {
                        double distance = entry.getDistance(trolley);
                        sortedByDistances.put(distance, entry);
                    }
                } else { // path to shelves
                    Map<Coordinates, Shelf> found = jsonParser.findGoods(trolley.task.getKey());

                    for (Map.Entry<Coordinates, Shelf> entry : found.entrySet()) {
                        double distance = entry.getKey().getDistance(trolley);
                        sortedByDistances.put(distance, entry.getKey());
                    }
                }


                System.out.println("Pozadovane zbozi: " + trolley.task + " ks");
                //compute path to shortest (if exists)
                if (sortedByDistances.size() == 0) {
                    System.err.println("!!!!!!! ERROR: POZADOVANE MNOZSTVI NENI SKLADEM !!!!!!!!!");
                    if (trolley.unload) {
                        System.err.println("!!!!!!! NENI STAGE !!!!!!!!!");
                        System.exit(1);
                    }
                    trolley.task = null;
                    continue;
                }
                //min distance Cordinates value
                Coordinates lowestDisanceCord = sortedByDistances.firstEntry().getValue();
                Coordinates curentCoordinates = new Coordinates((int) trolley.getX(), (int) trolley.getY());
                //if(trolley.pathList == null || trolley.unload) {
                trolley.pathList = findPath.aStar(curentCoordinates, lowestDisanceCord);
                //}

                //no path to this required item
                if (trolley.pathList == null) {
                    System.err.println("ERROR - Vozik se nemuze dostat k regalu !");
                    if (trolley.unload) {
                        System.err.println("!!!!!!! NENI STAGE !!!!!!!!!");
                        System.exit(1);
                    }
                    trolley.task = null;
                    continue;
                }

                if (trolley.pathList.size() != 0) {
                    //move trolley
                    trolley.setX(trolley.pathList.get(0).getX());
                    trolley.setY(trolley.pathList.get(0).getY());
                    trolley.pathList.remove(0);
                } else if (!trolley.unload) {
                    //next to shelf- load goods = remove items from shelf and add to trolley
                    Shelf itemShelf = jsonParser.getAllShelfs().get(lowestDisanceCord);
                    //count of items to delete - with respect to max capacity
                    int deleteCount = Math.min(trolley.getRemainCapacity(), trolley.task.getValue());
                    int removedItem = itemShelf.delete_item(trolley.task.getKey(), deleteCount);
                    trolley.task.setValue(trolley.task.getValue() - removedItem);
                    trolley.add_item(trolley.task.getKey(), removedItem);
                    //Deleted OK - set task = nul;
                    if (trolley.task.getValue() == 0)
                        trolley.task = null;
                } else {
                    //next to stage - unload
                    trolley.pathList = null;
                    trolley.unloadGoods();
                }
        }

    }
}
