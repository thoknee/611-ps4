/*
 * 
 *  Loads the weapons from the .txx
 * 
 *  Written by Tony Ponomarev
 */

package data;

import items.Weapon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeaponFactory implements ItemFactory<Weapon> {

    private final String filePath;

    public WeaponFactory(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Weapon> loadItems() {
        List<Weapon> weapons = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            line = br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\s+");
                if (parts.length < 5) {
                    System.err.println("Skipping malformed weapon line: " + line);
                    continue;
                }

                String name = parts[0];
                int cost = Integer.parseInt(parts[1]);
                int levelReq = Integer.parseInt(parts[2]);
                int damage = Integer.parseInt(parts[3]);
                int handsReq = Integer.parseInt(parts[4]);

                Weapon weapon = new Weapon(name, cost, levelReq, damage, handsReq, 999);
                weapons.add(weapon);
            }
        } catch (IOException e) {
            System.err.println("Error reading weapons file: " + e.getMessage());
        }

        return weapons;
    }
}
