/*
 * 
 *  Handles the spell, extending item
 * 
 *  Written by Tony Ponomarev
 * 
 */


package items;

public class Spell extends Item {

    private int baseDamage;
    private int manaCost;
    private SpellType type;

    public Spell(String name,
                 int price,
                 int levelRequirement,
                 int baseDamage,
                 int manaCost,
                 SpellType type,
                 int usesRemaining) {

        // Spells are consumable; you can pass 1 for single-use
        super(name, price, levelRequirement, usesRemaining);
        this.baseDamage = baseDamage;
        this.manaCost = manaCost;
        this.type = type;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getManaCost() {
        return manaCost;
    }

    public SpellType getType() {
        return type;
    }

    @Override
    public String toString() {
        return name
                + " (Lv " + levelRequirement
                + ", " + price + "g"
                + ", dmg " + baseDamage
                + ", mana " + manaCost
                + ", type " + type
                + ", uses " + usesRemaining + ")";
    }
}