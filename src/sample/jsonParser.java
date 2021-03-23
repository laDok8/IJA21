package sample;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.FileReader;
import java.util.*;

public class jsonParser {

    public ArrayList<shelf> storedObjects;

    public jsonParser(String filename) throws Exception {
        storedObjects = new ArrayList<>();
        JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(filename));
        JSONArray content = (JSONArray) obj.getOrDefault("content",new JSONArray());
        // iterating phoneNumbers
        Iterator itr2 = content.iterator();

        while (itr2.hasNext())
        {
            Map itr1 = ((Map) itr2.next());
            /*while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }*/
            System.out.println(itr1.get("x")+" "+itr1.get("y"));
            int x = Integer.parseInt(itr1.get("x").toString());
            int y = Integer.parseInt(itr1.get("y").toString());
            String name =  itr1.get("y").toString();
            int amount = Integer.parseInt(itr1.get("amount").toString());
            //System.out.println(x+""+y+""+name);
            shelf temp = new shelf(x,y);
            temp.add_item(name,amount);
            storedObjects.add(temp);
        }


    }
}
