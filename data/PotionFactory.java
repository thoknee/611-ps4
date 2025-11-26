/*
 * 
 *  Factory for parsing .txt and making all the potions
 * 
 *  Written by Tony Ponomarev
 * 
 */

package data;

import items.Potion;
import items.StatType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PotionFactory implements ItemFactory<Potion> {

    private final String filePath;

    public PotionFactory(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Potion> loadItems() {
        List<Potion> potions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                String name = parts[0];
                int cost = Integer.parseInt(parts[1]);
                int levelReq = Integer.parseInt(parts[2]);
                int effectAmount = Integer.parseInt(parts[3]);

                StringBuilder attrBuilder = new StringBuilder(parts[4]);

        
                String attrRaw = attrBuilder.toString();
                List<StatType> affected = parseStats(attrRaw);

                // System.out.println(affected.toString());

                Potion potion = new Potion(name, cost, levelReq, effectAmount, affected);
                potions.add(potion);
            }
        } catch (IOException e) {
            System.err.println("Error reading potions file: " + e.getMessage());
        }

        return potions;
    }

    private List<StatType> parseStats(String raw) {
        raw = raw.trim();

        if (raw.toLowerCase().startsWith("all ")) {
            raw = raw.substring(4);
        }

        String[] tokens = raw.split("/");

        List<StatType> stats = new ArrayList<>();
        for (String token : tokens) {
            String t = token.trim().toLowerCase();
            switch (t) {
                case "health":
                    stats.add(StatType.HEALTH);
                    break;
                case "mana":
                    stats.add(StatType.MANA);
                    break;
                case "strength":
                    stats.add(StatType.STRENGTH);
                    break;
                case "dexterity":
                    stats.add(StatType.DEXTERITY);
                    break;
                case "agility":
                    stats.add(StatType.AGILITY);
                    break;
                case "degense":
                    stats.add(StatType.AGILITY);
                    break;
                default:
                    System.out.println("Stat wasn't found");
            }
        }
        return stats;
    }
}
