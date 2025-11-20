/*
 * 
 *  Inventory, stored as a list - can individually get every single item 
 * 
 *  Written by Tony Ponomarev
 *  
 */



package items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory {

    private final List<Item> items = new ArrayList<>();



    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
        }
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getAllItems() {
        return Collections.unmodifiableList(items);
    }



    public List<Weapon> getWeapons() {
        List<Weapon> result = new ArrayList<>();
        for (Item i : items) {
            if (i instanceof Weapon) {
                result.add((Weapon) i);
            }
        }
        return result;
    }

    public List<Armor> getArmors() {
        List<Armor> result = new ArrayList<>();
        for (Item i : items) {
            if (i instanceof Armor) {
                result.add((Armor) i);
            }
        }
        return result;
    }

    public List<Potion> getPotions() {
        List<Potion> result = new ArrayList<>();
        for (Item i : items) {
            if (i instanceof Potion) {
                result.add((Potion) i);
            }
        }
        return result;
    }

    public List<Spell> getSpells() {
        List<Spell> result = new ArrayList<>();
        for (Item i : items) {
            if (i instanceof Spell) {
                result.add((Spell) i);
            }
        }
        return result;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("Inventory:\n");
        for (Item i : items) {
            sb.append("  ").append(i).append("\n");
        }
        return sb.toString();
    }
}
