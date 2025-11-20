/*
 * 
 *  Abstract class item - extended by potion, spell, weapon, and armor
 * 
 *  written by Tony Ponomarev
 * 
 */


package items;

public abstract class Item {
    protected String name;
    protected int price;
    protected int levelRequirement;
    protected int usesRemaining;

     public Item(String name, int price, int levelRequirement, int usesRemaining) {
        this.name = name;
        this.price = price;
        this.levelRequirement = levelRequirement;
        this.usesRemaining = usesRemaining;
    }

    public String getName(){
         return name; 
        }
    public int getPrice(){
         return price; 
        }
    public int getLevelRequirement(){
         return levelRequirement; 
        }
    public int getUsesRemaining(){
         return usesRemaining; 
        }

    public boolean canUse() {
        return usesRemaining > 0;
    }

    public void consumeUse() {
        if (usesRemaining > 0) {
            usesRemaining--;
        }
    }

    public String toString() {
        return name + " (Lv " + levelRequirement + ", " + price + "g, uses: " + usesRemaining + ")";
    }
}
