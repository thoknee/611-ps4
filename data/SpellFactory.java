/*
 * 
 *  Read the spell .txt
 * 
 *  Written by Tony Ponomarev
 * 
 */



package data;

import items.Spell;
import items.SpellType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpellFactory implements ItemFactory<Spell> {

    private final String filePath;
    private final SpellType type;

    public SpellFactory(String filePath, SpellType type) {
        this.filePath = filePath;
        this.type = type;
    }

    @Override
    public List<Spell> loadItems() {
        List<Spell> spells = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); 

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() ) continue;

                String[] parts = line.split("\\s+");

                String name      = parts[0];
                int cost         = Integer.parseInt(parts[1]);
                int levelReq     = Integer.parseInt(parts[2]);
                int baseDamage   = Integer.parseInt(parts[3]);
                int manaCost     = Integer.parseInt(parts[4]);

                Spell spell = new Spell(name, cost, levelReq, baseDamage, manaCost, type, 1);
                spells.add(spell);
            }
        } catch (IOException e) {
            System.err.println("Error reading spells file (" + type + "): " + e.getMessage());
        }

        return spells;
    }
}
