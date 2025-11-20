/*
 * 
 *  Factory for parsing .txt and making all the paladins
 * 
 *  Written by Tony Ponomarev
 * 
 */


package data;

import entities.Paladin;

public class PaladinFactory extends AbstractHeroFactory<Paladin> {

    public PaladinFactory(String filePath) {
        super(filePath);
    }

    @Override
    protected Paladin createHero(String name,
                                 int mana,
                                 int strength,
                                 int agility,
                                 int dexterity,
                                 int gold,
                                 int experience) {
        int level = 1;
        int hp    = level * 100;

        return new Paladin(name, level, hp, mana,
                           strength, dexterity, agility,
                           gold, experience);
    }
}
