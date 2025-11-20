/*
 * 
 *  Factory for parsing .txt and making all the Sorcerers
 * 
 *  Written by Tony Ponomarev
 * 
 */

package data;

import entities.Sorcerer;

public class SorcererFactory extends AbstractHeroFactory<Sorcerer> {

    public SorcererFactory(String filePath) {
        super(filePath);
    }

    @Override
    protected Sorcerer createHero(String name,
                                  int mana,
                                  int strength,
                                  int agility,
                                  int dexterity,
                                  int gold,
                                  int experience) {
        int level = 1;
        int hp    = level * 100;

        return new Sorcerer(name, level, hp, mana,
                            strength, dexterity, agility,
                            gold, experience);
    }
}
