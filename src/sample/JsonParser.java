package sample;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.stream.Collectors;
import java.io.FileReader;
import java.util.*;

public class JsonParser {

    public Map<Cordinates, Shelf> storedObjects;
    public Map<Integer, Cordinates> trolleyObjects;

    public JsonParser(String filename) throws Exception {
        storedObjects = new Hashtable<>();
        trolleyObjects = new Hashtable<>();
        JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(filename));
        JSONArray content = (JSONArray) obj.getOrDefault("content", new JSONArray());
        JSONArray trolley = (JSONArray) obj.getOrDefault("trolley", new JSONArray());

        // iterating phoneNumbers
        Iterator itr2 = content.iterator();

        while (itr2.hasNext()) {
            Map itr1 = ((Map) itr2.next());
            /*while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }*/
            //System.out.println(itr1.get("x") + " " + itr1.get("y"));
            int x = Integer.parseInt(itr1.get("x").toString());
            int y = Integer.parseInt(itr1.get("y").toString());
            Cordinates co = new Cordinates(x, y);
            String name = itr1.get("name").toString();
            int amount = Integer.parseInt(itr1.get("amount").toString());
            //System.out.println(x+""+y+""+name);
            Shelf tmp = storedObjects.getOrDefault(co, new Shelf(co));
            tmp.add_item(name, amount);
            storedObjects.put(co, tmp);
        }

        // iterating trolley array
        Iterator itr3 = trolley.iterator();
        while (itr3.hasNext()) {
            Map itr4 = ((Map) itr3.next());

            int id = Integer.parseInt(itr4.get("id").toString());
            int x = Integer.parseInt(itr4.get("x").toString());
            int y = Integer.parseInt(itr4.get("y").toString());
            Cordinates co = new Cordinates(x, y);
            trolleyObjects.put(id, co);
            //System.out.println("Trolley id: "+id+" position: "+x+" : "+y);

        }
    }


    Map<Cordinates, Shelf> findGoods(String name){
        var result = storedObjects.entrySet()
                .stream()
                .filter(map-> map.getValue().stored.containsKey(name))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return result;
    }
}
