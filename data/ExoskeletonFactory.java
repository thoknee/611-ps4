/*
 * 
 *  Factory for parsing .txt and making all the dragons 
 * 
 *  Written by Tony Ponomarev
 * 
 */

package data;

import entities.Exoskeleton;

public class ExoskeletonFactory extends AbstractMonsterFactory<Exoskeleton> {

    public ExoskeletonFactory(String filePath) {
        super(filePath);
    }

    @Override
    protected Exoskeleton createMonster(String name,
                                        int level,
                                        int baseDamage,
                                        int defense,
                                        int dodgeChance) {

        return new Exoskeleton(name, level, baseDamage, defense, dodgeChance);
    }
}
