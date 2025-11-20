/*
 * 
 * Load the spirits
 * 
 * 
 * Written by Tony Ponomarev
 * 
 */

package data;

import entities.Spirit;

public class SpiritFactory extends AbstractMonsterFactory<Spirit> {

    public SpiritFactory(String filePath) {
        super(filePath);
    }

    @Override
    protected Spirit createMonster(String name,
                                   int level,
                                   int baseDamage,
                                   int defense,
                                   int dodgeChance) {
        return new Spirit(name, level, baseDamage, defense, dodgeChance);
    }
}
