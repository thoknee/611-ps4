package data;

import items.Armor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArmorFactory implements ItemFactory<Armor> {

    private final String filePath;

    public ArmorFactory(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Armor> loadItems() {
        List<Armor> armors = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\s+");
                if (parts.length < 4) {
                    System.err.println("Skipping malformed armor line: " + line);
                    continue;
                }

                String name   = parts[0];
                int cost      = Integer.parseInt(parts[1]);
                int levelReq  = Integer.parseInt(parts[2]);
                int reduction = Integer.parseInt(parts[3]);

                Armor armor = new Armor(name, cost, levelReq, reduction, 999);
                armors.add(armor);
            }
        } catch (IOException e) {
            System.err.println("Error reading armor file: " + e.getMessage());
        }

        return armors;
    }
}
