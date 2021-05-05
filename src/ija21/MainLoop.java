package ija21;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.AbstractMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.TreeMap;

public class MainLoop extends TimerTask {

    JsonParser jsonParser;
    Map<String, Integer> requirements;
    FindPath findPath;
    int scale;

    public MainLoop(JsonParser jsonParser, Map<String, Integer> requirements, FindPath findPath, int scale) {
        this.jsonParser = jsonParser;
        this.requirements = requirements;
        this.findPath = findPath;
        this.scale = scale;
    }

    @Override
    public void run() {
        //TODO game loop
        System.out.println("fromTimer\n");
        //System.out.println("MapSpeed: "+ mapSpeed);

        //iterate trolleys
        for (Trolley trolley : jsonParser.getTrolleys()) {

            //set task to empty trolleys
            if (trolley.task == null && requirements.size() > 0) {
                Map.Entry<String, Integer> tmp;
                tmp = requirements.entrySet().iterator().next();
                trolley.task = new AbstractMap.SimpleEntry<>(tmp.getKey(), tmp.getValue());
                requirements.remove(tmp.getKey());
            }
            //ending condition
            if (requirements.size() == 0 && jsonParser.getTrolleys().stream().allMatch(trolley1 -> trolley1.task == null)) {
                this.cancel();
                break;
            }

            //sort shelfs by distance
            TreeMap<Double, Coordinates> sortedByDistances = new TreeMap<>();
            Map<Coordinates, Shelf> found = jsonParser.findGoods(trolley.task.getKey());

            for (Map.Entry<Coordinates, Shelf> entry : found.entrySet()) {
                double distance = entry.getKey().getDistance(trolley);
                sortedByDistances.put(distance, entry.getKey());
            }

            System.out.println("Pozadovane zbozi: " + trolley.task + " ks");
            //compute path to shortest (if exists)
            if (sortedByDistances.size() == 0) {
                System.out.println("!!!!!!! ERROR: POZADOVANE MNOZSTVI NENI SKLADEM !!!!!!!!!");
                trolley.task = null;
                continue;
            }
            //min distance Cordinates value
            Coordinates lowestDisanceCord = sortedByDistances.firstEntry().getValue();
            Coordinates curentCoordinates = new Coordinates((int) trolley.getX(), (int) trolley.getY());
            PathNode vysl2 = findPath.aStar(curentCoordinates, lowestDisanceCord);

            //no path to this required item
            if (vysl2 == null) {
                System.out.println("ERROR - Vozik se nemuze dostat k regalu s pozadovanym zbozim!");
                trolley.task = null;
                continue;
            }
            //vysl2.parent != null &&
            PathNode tmpCord;
            tmpCord = vysl2;
            while (!tmpCord.getSelf().equals(curentCoordinates)) {
                vysl2 = tmpCord;
                tmpCord = vysl2.getParent();
            }

            System.out.println("ZMENA POZICE VOZIKU: [" + trolley.getX() + ":" + trolley.getY() + "]");
            System.out.println("------------- MAP UPDATE ------------------");
            if (curentCoordinates.getDistance(lowestDisanceCord) != scale) {
                trolley.setX(vysl2.getSelf().getX());
                trolley.setY(vysl2.getSelf().getY());
            } else {
                //Success - remove items from shelf
                Shelf itemShelf = jsonParser.getAllShelfs().get(lowestDisanceCord);
                int removedItem = itemShelf.delete_item(trolley.task.getKey(), trolley.task.getValue());
                trolley.task.setValue(trolley.task.getValue() - removedItem);
                trolley.add_item(trolley.task.getKey(), removedItem);
                //Deleted OK - set task = nul;
                if (trolley.task.getValue() == 0)
                    trolley.task = null;

                //put all shelf into map
                //jsonParser.getAllShelfs().forEach((cordinates, tmp) -> putShelvesToMap(cordinates.x, cordinates.y));
            }
        }

    }
}
