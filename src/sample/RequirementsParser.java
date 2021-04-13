/*
  Class RequirementsParser parse all require arguments
  @author Ladislav Dokoupil (xdokou14)
  @author Adrián Bobola (xbobol00)
 */
package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.FileReader;
import java.util.*;

public class RequirementsParser {
    public Map<String, Integer> requirements;

    /**
     * Parses requirements json file into local structures requirements
     * @param filename file path
     * @throws Exception no such file found or bad JSON structure
     */
    public RequirementsParser(String filename) throws Exception {
        requirements = new Hashtable<>();

        JSONArray tmp = (JSONArray) new JSONParser().parse(new FileReader(filename));
        Iterator itr2 = tmp.iterator();

        while (itr2.hasNext()) {
            Map itr1 = ((Map) itr2.next());
            String name = itr1.get("name").toString();
            int amount = Integer.parseInt(itr1.get("amount").toString());
            requirements.put(name, amount);
        }
    }
}
