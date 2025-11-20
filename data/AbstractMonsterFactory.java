/*
 * 
 *  AbstractHeroFactory that handles the parsing
 * 
 *  Writtten by Tony Ponomarev
 */


package data;

import entities.Monster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public abstract class AbstractMonsterFactory<T extends Monster> implements MonsterFactory<T> {

    private final String filePath;

    public AbstractMonsterFactory(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<T> loadMonsters() {
        List<T> monsters = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s+");

                String name       = parts[0];
                int level         = Integer.parseInt(parts[1]);
                int baseDamage    = Integer.parseInt(parts[2]);
                int defense       = Integer.parseInt(parts[3]);
                int dodgeChance   = Integer.parseInt(parts[4]);

                T monster = createMonster(name, level, baseDamage, defense, dodgeChance/100);
                monsters.add(monster);
            }
        } catch (IOException e) {
            System.err.println("Error reading monsters file " + filePath + ": " + e.getMessage());
        }

        return monsters;
    }

    // Creates the monster
    protected abstract T createMonster(String name,
                                       int level,
                                       int baseDamage,
                                       int defense,
                                       int dodgeChance);
}
