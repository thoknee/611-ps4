/*
 * 
 *  Class for paladins, subclass of hero
 * 
 *  written by Tony Ponomarev
 * 
 */



package entities;

public class Paladin extends Hero{
    

    public Paladin(String name, int level, int hp, 
                    int mana, int strength, int dexterity, 
                    int agility,int gold,int exp){
                    
                    
            super(name, level, hp, mana, strength, 
                    dexterity, agility, gold, exp);
    }

    public void levelUp(){
        super.level++;

        // Strengh and dexterity are favored
        strength = (int) Math.round(strength * 1.05 * 1.05);
        dexterity = (int) Math.round(dexterity * 1.05 * 1.05);
        agility  = (int) Math.round(agility  * 1.05);



        

    }

}
