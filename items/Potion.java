/*
 * 
 *  Potion - a list of stat types that it buffs, extends item
 *  
 *  Written by tony Ponomarev
 * 
 * 
 */


package items;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Potion extends Item {

    private int effectAmount;
    private List<StatType> affectedStats;

    public Potion(String name,
                  int price,
                  int levelRequirement,
                  int effectAmount,
                  List<StatType> affectedStats) {

        // Potions are single use
        super(name, price, levelRequirement, 1);
        this.effectAmount = effectAmount;
        this.affectedStats = new ArrayList<>(affectedStats);
    }

    public int getEffectAmount() {
        return effectAmount;
    }

    public List<StatType> getAffectedStats() {
        return Collections.unmodifiableList(affectedStats);
    }

    @Override
    public String toString() {
        return name + " (Lv " + levelRequirement
                + ", " + price + "g, +" + effectAmount
                + " to " + affectedStats + ")";
    }
}
