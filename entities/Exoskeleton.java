/*
 * 
 *  Exoskeleton Monster class
 * 
 *  Written by tony Ponomarev
 */

package entities;

public class Exoskeleton extends Monster {
    public Exoskeleton(String name, int level, int baseDamage, int defense, double dodgeChance) {
        super(name, level, level * 100, baseDamage, defense, dodgeChance);
    }
}
