/*
 * 
 *  AbstractHeroFactory that handles the parsing
 * 
 *  Writtten by Tony Ponomarev
 */

package data;

import entities.Hero;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Template-method base for hero factories.
 * Handles reading the text file and parsing the common columns.
 * Subclasses only implement createHero(...).
 */
public abstract class AbstractHeroFactory<T extends Hero> implements HeroFactory<T> {

    private final String filePath;

    public AbstractHeroFactory(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<T> loadHeroes() {
        List<T> heroes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s+");

                String name     = parts[0];
                int mana        = Integer.parseInt(parts[1]);
                int strength    = Integer.parseInt(parts[2]);
                int agility     = Integer.parseInt(parts[3]);
                int dexterity   = Integer.parseInt(parts[4]);
                int gold        = Integer.parseInt(parts[5]);
                int experience  = Integer.parseInt(parts[6]);

                T hero = createHero(name, mana, strength, agility, dexterity, gold, experience);
                heroes.add(hero);
            }
        } catch (IOException e) {
            System.err.println("Error reading heroes file " + filePath + ": " + e.getMessage());
        }

        return heroes;
    }

    // Creates actual hero
    protected abstract T createHero(String name,
                                    int mana,
                                    int strength,
                                    int agility,
                                    int dexterity,
                                    int gold,
                                    int experience);
}
