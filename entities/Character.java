/*
 * 
 *  Abstract Character class. This will be extended by monsters and heroes 
 * 
 *  Written by Tony Ponomarev
 * 
 */


package entities;


public abstract class Character{

    protected String name;
    protected int level;
    protected int hp;


    public Character(String name, int level, int hp){
        this.name = name;
        this.level = level;
        this.hp = hp;

    }
    
    // Getters
    public String getName() { 
        return name; 
    }
    public int getLevel() { 
        return level; 
    }
    public int getHp() {
         return hp; 
    }


    // other helpful methods
    public boolean isFainted(){
        return hp <= 0;
    }
    public void takeDamage(int damage){
        hp = Math.max(0, hp - damage);
    }
    public void heal(int amount){
        this.hp += amount;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public String toString() {
        return name + " (Lv " + level + ", HP " + hp + ")";
    }

}