/*
 * 
 *  Monster class, extends character extended by exoskeletons, dragons, and spirits
 * 
 *  Written by Tony Ponomarev
 */

package entities;

public abstract class Monster extends Character {

    protected int baseDamage;
    protected int defense;
    protected double dodgeChance;

    public Monster(String name, int level, int hp,
                   int baseDamage, int defense, double dodgeChance) {
        super(name, level, hp);
        this.baseDamage = baseDamage;
        this.defense = defense;
        this.dodgeChance = dodgeChance;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getDefense() {
        return defense;
    }

    public double getDodgeChance() {
        return this.dodgeChance;
    }

    public void reduceDamageByFactor(double factor) {
        // used by ice spells
        baseDamage = (int) Math.round(baseDamage * (1.0 - factor));
    }

    public void reduceDefenseByFactor(double factor) {
        // used by fire spells
        defense = (int) Math.round(defense * (1.0 - factor));
    }

    public void reduceDodgeByFactor(double factor) {
        // used by lightning spells
        dodgeChance = Math.max(0, dodgeChance * (1.0 - factor));
    }

    @Override
    public String toString() {
        return name + " (Lv " + level
                + ", HP " + hp
                + ", DMG " + baseDamage
                + ", DEF " + defense
                + ", DODGE " + dodgeChance + ")";
    }
}
