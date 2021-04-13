/*
  @author Ladislav Dokoupil (xdokou14)
  @author Adrián Bobola (xbobol00)
 */

package sample;

import java.util.*;

public class Main {


    /**
     * Print map into output
     */
    public static void printMap(String[][] map) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                System.out.print(map[row][col]);
            }
            System.out.println();
        }
    }

    /**
     * Main function
     * @param args
     */
    public static void main(String[] args) {
        //load shelfs
        JsonParser jsonParser = null;
        try {
            jsonParser = new JsonParser("data/sample.json");
        } catch (Exception e) {
            System.err.println("chyba parser: sample.json");
            System.exit(1);
        }
        final int maxX = jsonParser.getMaxX();
        final int maxY = jsonParser.getMaxY();

        //load required items list
        RequirementsParser reqParser = null;
        try {
            reqParser = new RequirementsParser("data/requirements.json");
        } catch (Exception e) {
            System.err.println("chyba parser: requirements.json");
            System.exit(2);
        }

        //print all shelfs into output
        System.out.println("-------------------- REGAL NA SKLADE -----------------------");
        jsonParser.getAllShelfs().forEach((cordinates, tmp) -> {
            System.out.println("Regal [" + cordinates.x + ":" + cordinates.y + "], zbozi:" + tmp.stored);
        });
        System.out.println("-------------------- POZIADAVKY -----------------------");
        //print required items into output
        reqParser.requirements.forEach((name, count) -> {
            System.out.println("Pozadovane zbozi: " + name + ", " + count + " ks");
        });
        System.out.println("\n**************************************************************************");
        System.out.println("*************************  MAPA ******************************************");
        System.out.println("**************************************************************************\n");

        //initalize pathifinding structure
        FindPath findPath = new FindPath(maxX, maxY);
        //update every time shelfs are changed
        findPath.updatePaths(jsonParser.getAllShelfs());

        //create empty map
        String[][] map = new String[maxX][maxY];
        for (String[] row : map) {
            Arrays.fill(row, " ");
        }

        //put all shelf into map
        jsonParser.getAllShelfs().forEach((cordinates, tmp) -> {
            map[cordinates.y][cordinates.x] = "X";
        });

        Boolean running = true;
        while (running) {
            //set refreshing speed
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //iterate trolleys
            for (Trolley trolley : jsonParser.getTrolleys()) {

                //set task to empty trolleys
                if (trolley.task == null && reqParser.requirements.size() > 0) {
                    Map.Entry<String, Integer> tmp;
                    tmp = reqParser.requirements.entrySet().iterator().next();
                    trolley.task = new AbstractMap.SimpleEntry<String, Integer>(tmp.getKey(), tmp.getValue());
                    reqParser.requirements.remove(tmp.getKey());
                }
                //ending condition
                if (reqParser.requirements.size() == 0 && jsonParser.getTrolleys().stream().allMatch(trolley1 -> trolley1.task == null)) {
                    running = false;
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
                    System.out.println("-------------------- DALSIA POZIADAVKA --------------------------");
                    //TODO zmenit
                    trolley.task = null;
                    continue;
                }
                //min distance Cordinates value
                Coordinates lowestDisanceCord = sortedByDistances.firstEntry().getValue();
                Coordinates curentCoordinates = new Coordinates(trolley.x, trolley.y);
                PathNode vysl2 = findPath.aStar(curentCoordinates, lowestDisanceCord);

                //no path to this required item
                if (vysl2 == null) {
                    System.out.println("ERROR - Vozik se nemuze dostat k regalu s pozadovanym zbozim!");
                    System.out.println("-------------------- DALSIA POZIADAVKA --------------------------");
                    //TODO zmenit
                    trolley.task = null;
                    continue;
                }
                //vysl2.parent != null &&
                PathNode tmpCord;
                tmpCord = vysl2;
                while (!tmpCord.self.equals(curentCoordinates)) {
                    vysl2 = tmpCord;
                    tmpCord = vysl2.parent;
                }

                //add new trolley coordinates into map
                map[trolley.y][trolley.x] = "T";
                printMap(map);
                //delete trolley previous coordinates
                map[trolley.y][trolley.x] = " ";
                System.out.println("ZMENA POZICE VOZIKU: [" + trolley.x + ":" + trolley.y + "]");
                System.out.println("------------- MAP UPDATE ------------------");
                if (curentCoordinates.getDistance(lowestDisanceCord) != 1) {
                    trolley.x = vysl2.self.x;
                    trolley.y = vysl2.self.y;
                } else {
                    //Success - remove items from shelf TODO: nastav pocet polozek ve voziku + pocet deleted
                    Shelf itemShelf = jsonParser.getAllShelfs().get(lowestDisanceCord);
                    int removedItem = itemShelf.delete_item(trolley.task.getKey(), trolley.task.getValue());
                    trolley.task.setValue(trolley.task.getValue() - removedItem);
                    //Deleted OK - set task = nul;
                    if (trolley.task.getValue() == 0) {
                        trolley.task = null;
                    }

                    System.out.println("-------------------- DALSIA POZIADAVKA --------------------------");

                    //Set map to default position - loading next require item
                    for (String[] row : map) {
                        Arrays.fill(row, " ");
                    }

                    //put all shelf into map
                    jsonParser.getAllShelfs().forEach((cordinates, tmp) -> {
                        map[cordinates.y][cordinates.x] = "X";
                    });
                }
            }
        }
        System.out.println("VSECHNY POZADAVKY USPESNE ZPRACOVANY.");
    }
}
