/*
 * 
 *  Factory for parsing .txt and making all the dragons 
 * 
 *  Written by Tony Ponomarev
 * 
 */

package data;

import entities.Dragon;

public class DragonFactory extends AbstractMonsterFactory<Dragon> {

    public DragonFactory(String filePath) {
        super(filePath);
    }

    @Override
    protected Dragon createMonster(String name,
                                   int level,
                                   int baseDamage,
                                   int defense,
                                   int dodgeChance) {
        
        return new Dragon(name, level, baseDamage, defense, dodgeChance);
    }
}
