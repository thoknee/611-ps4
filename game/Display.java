/*
 * 
 *  Simple UI display helper that can make everything look better
 * 
 * 
 *  Written by Tony Ponomarev
 * 
 */


package game;

import entities.Hero;
import entities.Monster;
import entities.Party;
import items.Inventory;
import items.Item;

import java.util.List;

public class Display {

    
    public static void printSeparator() {
        System.out.println("------------------------------------------------------");
    }

    private static String makeBar(int current, int max, int width) {
        if (max <= 0) {
            max = 1;
        }
        double ratio = Math.max(0.0, Math.min(1.0, current / (double) max));
        int filled = (int) Math.round(ratio * width);

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < width; i++) {
            sb.append(i < filled ? '#' : ' ');
        }
        sb.append("] ");
        sb.append(current).append("/").append(max);
        return sb.toString();
    }


    public static void heroDisplay(Hero h) {
        System.out.println("HERO DETAILS: " + h.getName());
        printSeparator();

        System.out.println("Class : " + h.getName());
        System.out.println("Level : " + h.getLevel());
        System.out.println("XP    : " + h.getExperience());
        System.out.println();

        System.out.println("STATS");
        printSeparator();

        String hpBar = makeBar(h.getHp(), h.getMaxHp(), 20);
        String manaBar = makeBar(h.getMana(), h.getMaxMana(), 20);

        System.out.println("HP     : " + hpBar);
        System.out.println("MP     : " + manaBar);
        System.out.printf("STR    : %d%n", h.getStrength());
        System.out.printf("DEX    : %d%n", h.getDexterity());
        System.out.printf("AGI    : %d%n", h.getAgility());
        System.out.printf("Gold   : %d%n", h.getGold());
        System.out.println();

        System.out.println("EQUIPMENT");
        printSeparator();
        if (h.getEquippedWeapon() != null) {
            System.out.println("Weapon : " + h.getEquippedWeapon());
        } else {
            System.out.println("Weapon : (none)");
        }

        if (h.getEquippedArmor() != null) {
            System.out.println("Armor  : " + h.getEquippedArmor());
        } else {
            System.out.println("Armor  : (none)");
        }
        System.out.println();


        
    }

    public static void printEnemyInfo(List<Monster> enemies) {

        System.out.println("\n=== MONSTERS ===");
        for (Monster m : enemies) {
            System.out.println(m.getName()
                    + "  Lv:" + m.getLevel()
                    + "  HP:" + m.getHp()
                    + "  DMG:" + m.getBaseDamage()
                    + "  DEF:" + m.getDefense()
                    + "  DODGE:" + m.getDodgeChance());
        }
        System.out.println();
    }


    public static void showHeroList(List<Hero> heroes) {
        System.out.printf(
                "%-3s %-20s %-10s %-4s %-6s %-6s %-6s %-6s %-6s%n",
                "#", "Name", "Class", "Lv",  "HP" , "STR", "DEX", "AGI", "Gold"
        );
        printSeparator();

        for (int i = 0; i < heroes.size(); i++) {
            Hero h = heroes.get(i);
            System.out.printf(
                    "%-3d %-20s %-10s %-4d %-6d %-6d %-6d %-6d %-6d%n",
                    (i + 1),
                    h.getName(),
                    h.getClass().getSimpleName(),
                    h.getLevel(),
                    h.getHp(),
                    h.getStrength(),
                    h.getDexterity(),
                    h.getAgility(),
                    h.getGold()
            );
        }

        printSeparator();
        System.out.println();
    }

    public static void showMonsters(List<Monster> monsters) {
        System.out.println("ENEMY STATUS");
        System.out.printf("%-3s %-18s %-4s %-8s %-8s %-8s %-10s%n",
                "#", "Name", "Lv", "HP", "DMG", "DEF", "DODGE");
        printSeparator();
        for (int i = 0; i < monsters.size(); i++) {
            Monster m = monsters.get(i);
            System.out.printf(
                    "%-3d %-18s %-4d %-8d %-8d %-8d %-10.2f%n",
                    (i + 1),
                    m.getName(),
                    m.getLevel(),
                    m.getHp(),
                    m.getBaseDamage(),
                    m.getDefense(),
                    m.getDodgeChance()
            );
        }

    }

    public static void showItemsList(List<? extends Item> items) {
        System.out.printf("%-3s %-20s %-6s %-6s %-15s%n",
                "#", "Name", "Cost", "ReqLv", "Details");
        printSeparator();
        for (int i = 0; i < items.size(); i++) {
            Item it = items.get(i);
            System.out.printf(
                    "%-3d %-20s %-6d %-6d %-15s%n",
                    (i + 1),
                    it.getName(),
                    it.getPrice(),
                    it.getLevelRequirement(),
                    it.toString()
            );
        }
        printSeparator();
    }


    public static void showHeroInventory(Hero h) {
        
        if (h.getInventory() == null) {
            System.out.println(h.getName() + " has no inventory.");

            System.out.println();
            return;
        }

        Inventory inv = h.getInventory();

        System.out.println();
       System.out.println("INVENTORY: " + h.getName());
        printSeparator();
        // Weapons
        System.out.println("WEAPONS");
        if (inv.getWeapons().isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (int i = 0; i < inv.getWeapons().size(); i++) {
                System.out.println("  " + (i + 1) + ". " + inv.getWeapons().get(i));
            }
        }
        System.out.println();

        // Armor
        System.out.println("ARMOR");
        if (inv.getArmors().isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (int i = 0; i < inv.getArmors().size(); i++) {
                System.out.println("  " + (i + 1) + ". " + inv.getArmors().get(i));
            }
        }
        System.out.println();

        // Potions
        System.out.println("POTIONS");
        if (inv.getPotions().isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (int i = 0; i < inv.getPotions().size(); i++) {
                System.out.println("  " + (i + 1) + ". " + inv.getPotions().get(i));
            }
        }
        System.out.println();

        // Spells
        System.out.println("SPELLS");
        if (inv.getSpells().isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (int i = 0; i < inv.getSpells().size(); i++) {
                System.out.println("  " + (i + 1) + ". " + inv.getSpells().get(i));
            }
        }

        System.out.println();
    }



    public static void showIntroduction() {

        System.out.println("Welcome to Legends, Monsters and heroes");

        System.out.println(
                "A world full of magic, monsters, and danger.\n" +
                "You lead a small party of heroes through a dangerous land of markets,\n" +
                "mystic spells, and random ambushes.\n"
        );
        System.out.println(
                "You only have one goal\n" +
                "  - Survive\n" + 
                "To do so: \n" + 
                "  - Defeat monsters\n" +
                "  - Grow stronger\n" +
                "  - Stack gold\n" +
                "  - Buy better gear\n" 

    
        );
        System.out.println("Move carefully, shop wisely, keep your friends close, and your enemies closer");

        System.out.println();
    }

    public static void showRules() {
        System.out.println("How to Play");

        System.out.println("World");
        printSeparator();
        System.out.println("  - The world is a grid of tiles:");
        System.out.println("      ^ : Inaccessible (you cannot enter)");
        System.out.println("      M : Market (buy/sell items)");
        System.out.println("      . : Common tile. Beware, there is a chance of battle");
        System.out.println("      P : Your party's current position");
        System.out.println("  - Move with W (up), A (left), S (down), D (right).");
        System.out.println("  - You can quit at any time with Q.");
        System.out.println();

        System.out.println("MARKETS");
        printSeparator();
        System.out.println("  - Step on an M tile, then press M to enter the market.");
        System.out.println("  - Each hero has their own gold and inventory.");
        System.out.println("  - You can:");
        System.out.println("      * Buy items");
        System.out.println("      * Sell items");
        System.out.println();

        System.out.println("In Battle");
        printSeparator();
        System.out.println("  - On common tiles, you may randomly encounter monsters.");
        System.out.println("  - In battle, you act first");
        System.out.println("  - On a hero's turn you can:");
        System.out.println("      A : Attack with weapon");
        System.out.println("      S : Cast a spell (must need both hands)");
        System.out.println("      P : Use a potion (must need both hands)");
        System.out.println("      E : Equip a weapon or armor");
        System.out.println("      I : Show detailed info on all heroes");
        System.out.println("      R : Show detailed info on all monsters");
        System.out.println("  - Heroes and monsters can dodge attacks based on agility");
        System.out.println("  - If a hero's HP drops to 0, they faint for the rest of the battle.");
        System.out.println("  - If all heroes faint, the game is over.");
        System.out.println("  - If all monsters are defeated, heroes gain gold and experience.");
        System.out.println();

        System.out.println("Grow Stronger");
       printSeparator();
        System.out.println("  - Heroes gain experience after winning battles.");
        System.out.println("  - When a hero levels up, their HP/MP and stats improve.");
        System.out.println("  - Some hero types (Warrior, Sorcerer, Paladin) favor certain stats.");
        System.out.println("  - Use gold in markets to buy better weapons, armor, potions, and spells.");
        System.out.println();

        System.out.println("Other useful keys:");
        printSeparator();
        System.out.println("  I : Show party status");
        System.out.println("  H : Show the rules again");
        System.out.println("  Q : Quit the game");

        System.out.println();
    }

}
