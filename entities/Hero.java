/*
 *  Hero method. Extends the character class and will be extended by 
 *  paladins, sorcerers, and warriors.
 * 
 *  Written by Tony Ponomarev
 * 
 */


package entities;

import java.util.List;

import items.Armor;
import items.Inventory;
import items.Weapon;
import items.Potion;
import items.StatType;

public abstract class Hero extends Character{

    protected int mana;
    protected int strength;
    protected int agility;
    protected int dexterity;
    protected int gold;
    protected int exp;

    protected int maxHp;
    protected int maxMana;

    protected Inventory inventory;
    protected Weapon equippedWeapon;
    protected Armor equippedArmor;


    public Hero(String name, int level, int hp, int mana, int strength,
                int dexterity, int agility,int gold,int exp) {
        super(name, level, hp);
        this.mana = mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.agility = agility;
        this.gold = gold;
        this.exp = exp;

        this.maxHp = hp;
        this.maxMana = mana;


        this.inventory = new Inventory();
        this.equippedWeapon = null;
        this.equippedArmor = null;
    }

    // Getters

    public Inventory getInventory() {
        return inventory;
    }
    public int getMana(){ 
        return mana; 
    }
    public int getStrength(){ 
        return strength; 
    }
    public int getDexterity(){ 
        return dexterity; 
    }
    public int getAgility(){
         return agility; 
        }
    public int getGold(){
         return gold; 
        }
    public int getMaxHp() {
        return maxHp;
    }

    public int getMaxMana() {
        return maxMana;
    }
    public int getExperience(){ 
        return exp; 
    }

    public void setHp(int amt){
        this.hp = amt;
    }

    public void setMana(int amt){ 
        this.mana = amt;
    }
    public void setStrength(int amt){ 
        this.strength = amt;
    }
    public void setDexterity(int amt){ 
        this.dexterity = amt;
    }
    public void setAgility(int amt){
         this.agility = amt;
        }



    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public Armor getEquippedArmor() {
        return equippedArmor;
    }



    public void equipArmor(Armor armor) {
        this.equippedArmor = armor;
    }


    public void addGold(int amount) {
        gold += amount;
    }

    public void spendGold(int amount) {
        gold -= amount;
    }

    public void gainExperience(int amount) {
        exp += amount;
        // Automatically level up if enough XP
        while (exp >= xpToLevelUp()) {
            exp -= xpToLevelUp();
            

            int oldLevel = level;
            int oldHp = maxHp;
            int oldMana = maxMana;
            int oldStr = strength;
            int oldDex = dexterity;
            int oldAgi = agility;


            // Different level up for each type of hero.

            levelUp();
            maxHp = level * 100;
            maxMana = (int) Math.round(maxMana * 1.1);
            printLevelUp(oldLevel, oldHp, oldMana, oldStr, oldDex, oldAgi);


            this.hp = maxHp;
            this.mana = maxMana;
            

        }
    }

    public void equipWeapon(Weapon weapon) {
        if (getLevel() < weapon.getLevelRequirement()) {
            System.out.println(getName() + " is not high enough level to equip " + weapon.getName());
            return;
        }
        this.equippedWeapon = weapon;
    }

    public boolean hasTwoHandedWeaponEquipped() {
        return equippedWeapon != null && equippedWeapon.getHandsRequired() == 2;
    }
    
    public int xpToLevelUp(){
        return level * 10;
    }

    public abstract void levelUp();





    public void printLevelUp(int oldLevel, int oldHp, int oldMana,
                                int oldStrength, int oldDexterity, int oldAgility) {

    System.out.println("Congratulations! " + name + " has leveled up! 🎉");
    System.out.println("Now Level " + oldLevel + "!");

    System.out.printf(" HP       %5d  ->  %5d%n", oldHp, maxHp);
    System.out.printf(" Mana     %5d  ->  %5d%n", oldMana, maxMana);
    System.out.printf(" Strength %5d  ->  %5d%n", oldStrength, strength);
    System.out.printf(" Dexterity%5d  ->  %5d%n", oldDexterity, dexterity);
    System.out.printf(" Agility  %5d  ->  %5d%n", oldAgility, agility);
    
}

    
    
    // Damage helpers
    public double computeAttackDamage(int weaponDamage) {
        return (strength + weaponDamage) * 0.1;
    }

    public double computeSpellDamage(int spellBaseDamage) {
        return spellBaseDamage + (dexterity / 10000.0) * spellBaseDamage;
    }

    public double getDodgeChance() {
        return agility * 0.002;
    }

    public boolean hasEnoughMana(int cost) {
        return mana >= cost;
    }

    public void useMana(int amount) {
        mana = Math.max(0, mana - amount);
    }


    public void regenAfterBattle() {
        this.hp = Math.min((int) Math.ceil(this.hp * 1.1), maxHp);
        this.mana = Math.min((int) Math.ceil(this.mana * 1.1), maxMana);
    }

    public void reviveAfterBattle(){
        this.hp = (int) Math.ceil(maxHp/2);
        this.mana = (int) Math.ceil(maxMana/2);
    }

     public void applyPotion(Potion potion) {

        List<StatType> stats = potion.getAffectedStats();
        int amount = potion.getEffectAmount();

        System.out.println(name + " has used a potion!");

        for (StatType st : stats) {
            switch (st) {
                case HEALTH: {
                    System.out.println("It heals them " + amount +  "HP.");
                    setHp(hp + amount);
                }
                case MANA: {
                    System.out.println("Regenerates " + amount + "mana.");
                    setMana(mana + amount);
                }
                case STRENGTH:{
                    System.out.println("Increases their strength by" + amount);
                    setStrength(strength + amount);
                }
                case DEXTERITY: {
                    System.out.println("Increases their dexterity by" + amount);
                    setDexterity(dexterity + amount);
                }
                case AGILITY: {
                    System.out.println("Increases their agility by" + amount);
                    setAgility(agility+ amount);
                }
            }
        }
}



    public String toString() {
        return name
                + " (Lv " + level + ", HP " + hp + ", MP " + mana + ", STR " + strength
                + ", DEX " + dexterity + ", AGI " + agility + ", XP " + exp + ", Gold " + gold + ")";
    }


}
