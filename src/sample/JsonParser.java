package sample;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.stream.Collectors;
import java.io.FileReader;
import java.util.*;

public class JsonParser {




    private Map<Cordinates, Shelf> storedObjects = new Hashtable<>();
    private List<Trolley> trolleyObjects = new ArrayList<>();
    private List<Cordinates> stagingObjects = new ArrayList<>();
    private int maxX;
    private int maxY;


    public JsonParser(String filename) throws Exception {
        JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(filename));
        JSONArray content = (JSONArray) obj.getOrDefault("content", new JSONArray());
        JSONArray trolley = (JSONArray) obj.getOrDefault("trolley", new JSONArray());
        JSONArray staging = (JSONArray) obj.getOrDefault("staging", new JSONArray());

        maxX = ((Long) obj.get("maxX")).intValue();
        maxY = ((Long) obj.get("maxY")).intValue();

        // iterating gooods
        Iterator iterator = content.iterator();
        while (iterator.hasNext()) {
            Map entry = ((Map) iterator.next());
            int x = ((Long) entry.get("x")).intValue();
            int y = ((Long) entry.get("y")).intValue();
            Cordinates co = new Cordinates(x, y);
            String name = entry.get("name").toString();
            int amount = ((Long) entry.get("amount")).intValue();
            Shelf tmp = storedObjects.getOrDefault(co, new Shelf(co));
            tmp.add_item(name, amount);
            storedObjects.put(co, tmp);
        }


        //iterating trolleys
        iterator = trolley.iterator();
        while (iterator.hasNext()) {
            Map entry = ((Map) iterator.next());
            int id = ((Long) entry.get("id")).intValue();
            int x = ((Long) entry.get("x")).intValue();
            int y = ((Long) entry.get("y")).intValue();
            Cordinates co = new Cordinates(x, y);
            Trolley ntrolley = new Trolley(id,co);
            trolleyObjects.add(ntrolley);
        }

        //iterating staging arreas
        iterator = staging.iterator();
        while (iterator.hasNext()) {
            Map entry = ((Map) iterator.next());
            int x = ((Long) entry.get("x")).intValue();
            int y = ((Long) entry.get("y")).intValue();
            Cordinates co = new Cordinates(x, y);
            stagingObjects.add(co);
        }
    }

    Map<Cordinates, Shelf> findGoods(String name){
        Map<Cordinates, Shelf> result;
        result = storedObjects.entrySet()
                .stream()
                .filter(map-> map.getValue().stored.containsKey(name))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return result;
    }

    Map<Cordinates, Shelf> getAllGoods(){
        return storedObjects;
    }
    List<Trolley> getTrolleys(){
        return trolleyObjects;
    }
    List<Cordinates> getStages() {
        return stagingObjects;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }
}
