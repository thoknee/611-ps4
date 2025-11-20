/*
 * 
 *  Weapon, a type of item
 * 
 * 
 *  Written by Tony Ponomarev
 * 
 */


package items;

public class Weapon extends Item {

    private int damage;
    private int handsRequired;

    public Weapon(String name, int price, int levelRequirement,
                  int damage, int handsRequired, int usesRemaining) {
        super(name, price, levelRequirement, usesRemaining);
        this.damage = damage;
        this.handsRequired = handsRequired;
    }

    public int getDamage() {
        return damage;
    }

    public int getHandsRequired() {
        return handsRequired;
    }
}
