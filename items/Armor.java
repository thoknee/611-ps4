/*
 * 
 *  Armor - gives damage reduction
 * 
 *  Written by Tony Ponomarev
 */

package items;

public class Armor extends Item {

    private int damageReduction;

    public Armor(String name, int price, int levelRequirement,
                 int damageReduction, int usesRemaining) {
        super(name, price, levelRequirement, usesRemaining);
        this.damageReduction = damageReduction;
    }

    public int getDamageReduction() {
        return damageReduction;
    }
}