/*
  Class RequirementsParser parse all require arguments
  @author Ladislav Dokoupil (xdokou14)
  @author Adrián Bobola (xbobol00)
 */
package ija21;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;


import java.io.File;
import java.io.FileReader;
import java.util.*;

public class RequirementsParser {
    private final Map<String, Integer> requirements = new Hashtable<>();

    /**
     * Parses requirements json file into local structures requirements
     * @param filename file path
     * @throws Exception no such file found or bad JSON structure
     */
    public RequirementsParser(String filename) throws Exception {
        File requirementsFile = new File(filename); // file for chceck if exists
        RequirementsParser reqParser = null;
        JSONArray tmp;
        if (!requirementsFile.exists()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("--- Nacitavanie poziadaviek od uzivatela ---");
            System.out.println("Pre zastavenie nacitavania zadajte znak \"x\" do nazvu/poctu");
            System.out.println("Pre potvrdenie stlacte enter");
            System.out.println("-----------------------------------------");
            StringBuilder stdinRequirements = new StringBuilder("[");
            int iterator = 1;
            while (true) {
                System.out.println("Zadajte nazov polozky " + iterator + ":");
                String name = scanner.nextLine();
                //stop if empty line
                if (name == null || name.equals("")) {
                    break;
                }
                System.out.println("Zadajte mnozstvo polozky " + iterator + ":");
                String amount = scanner.nextLine();
                //stop if empty line
                if (amount == null || amount.equals("")) {
                    break;
                }
                stdinRequirements.append("{");
                stdinRequirements.append("\"name\": \"");
                stdinRequirements.append(name);
                stdinRequirements.append("\", \"amount\": ");
                stdinRequirements.append(amount);
                stdinRequirements.append("}");
                iterator++;
            }
            scanner.close();
            stdinRequirements.append("]");

            tmp = (JSONArray) new JSONParser().parse(String.valueOf(stdinRequirements));
        }
        else {
            tmp = (JSONArray) new JSONParser().parse(new FileReader(filename));
        }

        Iterator itr2 = tmp.iterator();

        while (itr2.hasNext()) {
            Map itr1 = ((Map) itr2.next());
            String name = itr1.get("name").toString();
            int amount = Integer.parseInt(itr1.get("amount").toString());
            requirements.put(name, amount);
        }
    }

    public Map<String, Integer> getRequirements() {
        return requirements;
    }
}