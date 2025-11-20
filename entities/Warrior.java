/*
 * 
 *  Class for warriors, subclass of hero
 * 
 *  written by Tony Ponomarev
 * 
 */

package entities;

public class Warrior extends Hero {

    public Warrior(String name, int level, int hp, int mp, int strength,
                   int dexterity, int agility, int gold, int experience) {
        super(name, level, hp, mp, strength, dexterity, agility, gold, experience);
    }

    @Override
    public void levelUp() {
        // increase level
        level++;
        // Favors strength and agility
        strength = (int) Math.round(strength * 1.05 * 1.05);
        dexterity = (int) Math.round(dexterity * 1.05);
        agility  = (int) Math.round(agility  * 1.05 * 1.05);

    }
}
