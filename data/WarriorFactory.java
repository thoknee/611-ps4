/*
 * 
 *  Uses abstract factory to make warriors
 * 
 *  Written by Tony Ponomarev
 */


package data;

import entities.Warrior;

public class WarriorFactory extends AbstractHeroFactory<Warrior> {

    public WarriorFactory(String filePath) {
        super(filePath);
    }

    @Override
    protected Warrior createHero(String name,
                                 int mana,
                                 int strength,
                                 int agility,
                                 int dexterity,
                                 int gold,
                                 int experience) {
        int level = 1;
        int hp = level * 100;

        return new Warrior(name, level, hp, mana,
                           strength, dexterity, agility,
                           gold, experience);
    }
}
