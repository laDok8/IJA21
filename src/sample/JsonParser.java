package sample;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.FileReader;
import java.util.*;

public class JsonParser {

    public Map<Cordinates, Shelf> storedObjects;

    public JsonParser(String filename) throws Exception {
        storedObjects = new Hashtable<>();
        JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(filename));
        JSONArray content = (JSONArray) obj.getOrDefault("content", new JSONArray());
        // iterating phoneNumbers
        Iterator itr2 = content.iterator();

        while (itr2.hasNext()) {
            Map itr1 = ((Map) itr2.next());
            /*while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }*/
            System.out.println(itr1.get("x") + " " + itr1.get("y"));
            int x = Integer.parseInt(itr1.get("x").toString());
            int y = Integer.parseInt(itr1.get("y").toString());
            Cordinates co = new Cordinates(x, y);
            String name = itr1.get("y").toString();
            int amount = Integer.parseInt(itr1.get("amount").toString());
            //System.out.println(x+""+y+""+name);
            Shelf tmp = storedObjects.getOrDefault(co, new Shelf(co));
            tmp.add_item(name, amount);
            storedObjects.put(co, tmp);
        }

        //example of filtering saving for later
        //var xyz = storedObjects.entrySet().stream().filter(map -> map.getValue() < 10).collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
    }
}
