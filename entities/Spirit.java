/*
 * 
 *  Spirit Monster class 
 * 
 *  Written by Tony Ponomarev
 * 
 */

package entities;

public class Spirit extends Monster {
    public Spirit(String name, int level, int baseDamage, int defense, double dodgeChance) {
        super(name, level, level * 100, baseDamage, defense, dodgeChance);
    }
}
