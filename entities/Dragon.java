/*
 * 
 *  Dragon Monster, extends Monster and Character
 * 
 *  Written by Tony Ponomarev 
 * 
 */


package entities;

public class Dragon extends Monster {
    public Dragon(String name, int level, int baseDamage, int defense, double dodgeChance) {
        super(name, level, level * 100, baseDamage, defense, dodgeChance);
    }
}
