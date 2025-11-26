/*
 * 
 *  This is the main battle loop. Handles everything to do with the battle
 * 
 *  Written by Tony Ponomarev
 * 
 * 
 */

package game;

import data.DragonFactory;
import data.ExoskeletonFactory;
import data.MonsterFactory;
import data.SpiritFactory;
import entities.*;
import items.*;
import java.util.*;



public class BattleEngine {
    // Initializes the monsters and the rng
    private final List<Monster> monsterPool = new ArrayList<>();
    private final Random rng = new Random();


    // parses all the data
    public BattleEngine() {
        MonsterFactory<Dragon> dragonFactory =
                new DragonFactory("Legends_Monsters_and_Heroes/Dragons.txt");
        MonsterFactory<Exoskeleton> exosFactory =
                new ExoskeletonFactory("Legends_Monsters_and_Heroes/Exoskeletons.txt");
        MonsterFactory<Spirit> spiritFactory =
                new SpiritFactory("Legends_Monsters_and_Heroes/Spirits.txt");
        // uses factories to make the monsters
        monsterPool.addAll(dragonFactory.loadMonsters());
        monsterPool.addAll(exosFactory.loadMonsters());
        monsterPool.addAll(spiritFactory.loadMonsters());
    }
    
    // Main battle loop
    public void startBattle(Party party) {
        System.out.println("=== BATTLE START ===");


        List<Monster> enemies = createMonstersForBattle(party);

        System.out.println("Enemies appear!");
        for (Monster m : enemies) {
            System.out.println("  " + m);
        }

        Scanner sc = new Scanner(System.in);
        int round = 1;

        // Main battle loop
        while (!allHeroesFainted(party) && !allMonstersFainted(enemies)) {
            System.out.println("\nROUND: " + round);

            // Hero turn
            heroesTurn(party, enemies, sc);

            if (allMonstersFainted(enemies)) {
                break;
            }

            // Monster turn
            monstersTurn(party, enemies);

            round++;
        }

        // Check to see who wins and give heroes rewards
        if (allMonstersFainted(enemies)) {
            System.out.println("\nHeroes Win!");

            // Add display for after battle - maybe a continue.

            rewardHeroes(party, enemies);
        } else {
            System.out.println("\nAll heroes have fallen. game over");
        }
    }

    // Creates monsters
    private List<Monster> createMonstersForBattle(Party party) {
        int highestLevel = party.getHighestLevel();
        int count = party.size();

        // Filter pool for monsters of the right level
        List<Monster> candidates = new ArrayList<>();
        
        // Randomizes mosnters
        Collections.shuffle(monsterPool, rng);
        // Makes sure theyre the right level
        for(Monster m : monsterPool){
            if (m.getLevel() <= highestLevel + 1){
                candidates.add(m);
            }
            }
                
        return new ArrayList<>(candidates.subList(0, count));
    }

    // Handles the heros turn. 
    private void heroesTurn(Party party, List<Monster> enemies, Scanner sc) {
        for (Hero hero : party.getHeroes()) {
            if (hero.isFainted()) continue;

            boolean actionTaken = false;

            while (!actionTaken && !allMonstersFainted(enemies)) {
                System.out.println("\nIt's " + hero.getName() + "'s turn.");
                System.out.println("HP: " + hero.getHp() + "   MP: " + hero.getMana());
                System.out.println("Choose action:");
                System.out.println("  A - Attack");
                System.out.println("  S - Cast Spell");
                System.out.println("  P - Use Potion");
                System.out.println("  E - Equip Weapon/Armor");
                System.out.println("  R - See Enemies Information");
                System.out.println("  I - Show Hero Information");
                System.out.print("> ");

                String input = sc.nextLine().trim().toUpperCase();
                if (input.isEmpty()) continue;
                char cmd = input.charAt(0);

                switch (cmd) {
                    case 'A': {
                        performHeroAttack(hero, enemies, sc);
                        actionTaken = true;
                    }
                    break;
                    case 'S': {
                        if (hero.hasTwoHandedWeaponEquipped()) {
                            // Doesn't allow you to use a spell if you're using a two-handed weapon
                            System.out.println("You are wielding a two-handed weapon and cannot cast spells right now.");
                            break;
                        }
                        if (performHeroSpell(hero, enemies, sc)) {
                            actionTaken = true;
                        }
                    }
                    break;
                    case 'P':{
                       if (hero.hasTwoHandedWeaponEquipped()) {
                            // Doesn't allow you to use a potion if you're using a two-handed weapon
                            System.out.println("You are wielding a two-handed weapon and cannot use potions right now.");
                            break;
                        }
                        if (performHeroPotion(hero, sc)) {
                            actionTaken = true;
                        }
                    }
                    break;
                    case 'E':{
                        performHeroEquip(hero, sc);
                        actionTaken = true;
                    }
                    break;
                    case 'I': {
                        boolean inspecting = true;

                        while (inspecting) {
                            Display.showHeroList(party.getHeroes());
                            System.out.print("Enter hero number to inspect, or Q to return: ");

                            String inspect = sc.nextLine().trim();

                            if (inspect.equalsIgnoreCase("Q")) {
                                inspecting = false;
                                break;
                            }

                            if (!inspect.isEmpty()) {
                                try {
                                    int idx = Integer.parseInt(inspect) - 1;
                                    if (idx >= 0 && idx < party.size()) {
                                        Hero h = party.getHero(idx);

                                        Display.heroDisplay(h);

                                        // Inventory
                                        System.out.print("View this hero's inventory? (Y/N): ");
                                        String ans = sc.nextLine().trim();
                                        if (ans.equalsIgnoreCase("Y")) {
                                            Display.showHeroInventory(h);
                                        }

                                        Display.showHeroList(party.getHeroes());
                                    } else {
                                        System.out.println("Invalid hero number.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Not a valid number.");
                                }}}
                        break;
                    }
                    case 'R':
                        Display.printEnemyInfo(enemies);
                        break;
                
                    default: 
                        System.out.println("Unknown command.");
                }
            }

            if (allMonstersFainted(enemies)) break;
        }
    }

    private void performHeroAttack(Hero hero, List<Monster> enemies, Scanner sc) {
        Monster target = chooseMonsterTarget(enemies, sc);
        if (target == null) return;

        if (monsterDodged(target)) {
            System.out.println(target.getName() + " dodged the attack!");
            return;
        }

        int damage = calculateHeroAttackDamage(hero, target);
        target.takeDamage(damage);
        System.out.println(hero.getName() + " attacked " + target.getName()
                + " for " + damage + " damage!");

        if (target.isFainted()) {
            System.out.println(target.getName() + " was defeated!");
        }
    }

    private boolean performHeroSpell(Hero hero, List<Monster> enemies, Scanner sc) {
        List<Spell> spells = hero.getInventory().getSpells();
        if (spells.isEmpty()) {
            System.out.println("You have no spells.");
            return false;
        }

        System.out.println("\nChoose a spell:");
        for (int i = 0; i < spells.size(); i++) {
            Spell sp = spells.get(i);
            System.out.println("  " + (i + 1) + ". " + sp + " (MP cost: " + sp.getManaCost() + ")");
        }
        System.out.println("  Q. Cancel");

        System.out.print("> ");
        String input = sc.nextLine().trim();
        if (input.equalsIgnoreCase("Q")) return false;

        int idx;
        try {
            idx = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid selection.");
            return false;
        }

        if (idx < 0 || idx >= spells.size()) {
            System.out.println("Invalid selection.");
            return false;
        }

        Spell spell = spells.get(idx);
        if (hero.getMana() < spell.getManaCost()) {
            System.out.println("Not enough mana.");
            return false;
        }

        Monster target = chooseMonsterTarget(enemies, sc);
        if (target == null) return false;

        // spend mana
        hero.useMana(spell.getManaCost());

        if (monsterDodged(target)) {
            System.out.println(target.getName() + " dodged the spell!");
            return true; 
        }

        int damage = calculateHeroSpellDamage(hero, spell, target);
        target.takeDamage(damage);

        System.out.println(hero.getName() + " cast " + spell.getName()
                + " on " + target.getName() + " for " + damage + " damage!");

        // extra spell effects
        applySpellEffect(spell, target);

        // consume use & remove from inventory
        spell.consumeUse();
        if (!spell.canUse()) {
            hero.getInventory().removeItem(spell);
        }

        if (target.isFainted()) {
            System.out.println(target.getName() + " was defeated!");
        }

        return true;
    }

    private boolean performHeroPotion(Hero hero, Scanner sc) {
        List<Potion> potions = hero.getInventory().getPotions();
        if (potions.isEmpty()) {
            System.out.println("You have no potions.");
            return false;
        }

        System.out.println("\nChoose a potion to use:");
        for (int i = 0; i < potions.size(); i++) {
            Potion p = potions.get(i);
            System.out.println("  " + (i + 1) + ". " + p);
        }
        System.out.println("  Q. Cancel");
        System.out.print("> ");

        String input = sc.nextLine().trim();
        if (input.equalsIgnoreCase("Q")) return false;

        int idx;
        try {
            idx = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid selection.");
            return false;
        }

        if (idx < 0 || idx >= potions.size()) {
            System.out.println("Invalid selection.");
            return false;
        }

        Potion potion = potions.get(idx);
        hero.applyPotion(potion); 
        hero.getInventory().removeItem(potion);

        System.out.println(hero.getName() + " used " + potion.getName() + ".");
        return true;
    }

    private void performHeroEquip(Hero hero, Scanner sc) {
        System.out.println("\nEquip what?");
        System.out.println("  1. Weapon");
        System.out.println("  2. Armor");
        System.out.println("  Q. Cancel");
        System.out.print("> ");

        String input = sc.nextLine().trim();
        if (input.equalsIgnoreCase("Q")) return;

        switch (input) {
            case "1":
                equipWeapon(hero, sc);
            case "2":
                equipArmor(hero, sc);
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void equipWeapon(Hero hero, Scanner sc) {
        List<Weapon> weapons = hero.getInventory().getWeapons();
        if (weapons.isEmpty()) {
            System.out.println("You have no weapons to equip.");
            return;
        }

        System.out.println("\nChoose a weapon to equip:");
        for (int i = 0; i < weapons.size(); i++) {
            Weapon w = weapons.get(i);
            System.out.println("  " + (i + 1) + ". " + w);
        }
        System.out.println("  Q. Cancel");
        System.out.print("> ");

        String input = sc.nextLine().trim();
        if (input.equalsIgnoreCase("Q")) return;

        int idx;
        try {
            idx = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid selection.");
            return;
        }

        if (idx < 0 || idx >= weapons.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Weapon w = weapons.get(idx);
        hero.equipWeapon(w);
        System.out.println(hero.getName() + " equipped " + w.getName() + ".");
    }

    private void equipArmor(Hero hero, Scanner sc) {
        List<Armor> armors = hero.getInventory().getArmors();
        if (armors.isEmpty()) {
            System.out.println("You have no armor to equip.");
            return;
        }

        System.out.println("\nChoose armor to equip:");
        for (int i = 0; i < armors.size(); i++) {
            Armor a = armors.get(i);
            System.out.println("  " + (i + 1) + ". " + a);
        }
        System.out.println("  Q. Cancel");
        System.out.print("> ");

        String input = sc.nextLine().trim();
        if (input.equalsIgnoreCase("Q")) return;

        int idx;
        try {
            idx = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid selection.");
            return;
        }

        if (idx < 0 || idx >= armors.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Armor a = armors.get(idx);
        hero.equipArmor(a);
        System.out.println(hero.getName() + " equipped " + a.getName() + ".");
    }

    // Monster helpers

    private void monstersTurn(Party party, List<Monster> enemies) {
        System.out.println("\n-- Monsters' turn --");

        List<Hero> heroes = party.getHeroes();
        List<Hero> aliveHeroes;

        for (Monster m : enemies) {
            if (m.isFainted()) continue;

            aliveHeroes = new ArrayList<>();
            for (Hero h : heroes) {
                if (!h.isFainted()) {
                    aliveHeroes.add(h);
                }
            }
            if (aliveHeroes.isEmpty()) break;

            Hero target = aliveHeroes.get(rng.nextInt(aliveHeroes.size()));

            // hero dodge check
            double heroDodgeChance = target.getAgility() * 0.0002;
            if (rng.nextDouble() < heroDodgeChance) {
                System.out.println(m.getName() + " attacked " + target.getName()
                        + " but " + target.getName() + " dodged!");
                continue;
            }

            int damage = calculateMonsterAttackDamage(m, target);
            target.takeDamage(damage);

            System.out.println(m.getName() + " attacked " + target.getName()
                    + " for " + damage + " damage!");

            if (target.isFainted()) {
                System.out.println(target.getName() + " has fainted!");
            }
        }
    }


    private boolean monsterDodged(Monster target) {
        return rng.nextDouble() < target.getDodgeChance();
    }

    private int calculateHeroAttackDamage(Hero hero, Monster target) {
        int weaponDamage = 0;
        Weapon w = hero.getEquippedWeapon();
        if (w != null) {
            weaponDamage = w.getDamage();
        }


        double raw = hero.computeAttackDamage(weaponDamage);
        double reduced = raw - target.getDefense() * 0.03;
        
        return (int) Math.round(reduced);
    }

    private int calculateHeroSpellDamage(Hero hero, Spell spell, Monster target) {
        double base = spell.getBaseDamage();
        double bonus = (hero.getDexterity() / 10000.0) * base;
        double raw = base + bonus;
        double reduced = raw - target.getDefense();
        return Math.max(0, (int) Math.round(reduced));
    }

    private int calculateMonsterAttackDamage(Monster monster, Hero target) {
        double raw = monster.getBaseDamage();
        int armorReduction = 0;
        Armor a = target.getEquippedArmor();
        if (a != null) armorReduction = a.getDamageReduction();
        double reduced = (raw - armorReduction) * 0.1;
        return Math.max(0, (int) Math.round(reduced));
    }

    private void applySpellEffect(Spell spell, Monster target) {
        switch (spell.getType()) {
            case ICE:
                target.reduceDamageByFactor(0.1);
            case FIRE:
                target.reduceDefenseByFactor(0.1);
            case LIGHTNING:
                target.reduceDodgeByFactor(0.1);
        }
    }

    private void rewardHeroes(Party party, List<Monster> enemies) {
        int numMonsters = enemies.size();
        int highestMonsterLevel = 0;
        for (Monster m : enemies) {
            highestMonsterLevel = Math.max(highestMonsterLevel, m.getLevel());
        }

        int goldRewardPerHero = highestMonsterLevel * 100;
        int expReward = highestMonsterLevel * 2;

        for (Hero h : party.getHeroes()) {
            if (h.isFainted()) {
                h.reviveAfterBattle();
            } else {
                h.addGold(goldRewardPerHero);
                h.gainExperience(expReward);
                h.regenAfterBattle();
            }
        }
    }


    private boolean allHeroesFainted(Party party) {
        return party.allFainted();
    }

    private boolean allMonstersFainted(List<Monster> monsters) {
        for (Monster m : monsters) {
            if (!m.isFainted()) return false;
        }
        return true;
    }

    private Monster chooseMonsterTarget(List<Monster> enemies, Scanner sc) {
        List<Integer> aliveIndices = new ArrayList<>();
        for (int i = 0; i < enemies.size(); i++) {
            if (!enemies.get(i).isFainted()) {
                aliveIndices.add(i);
            }
        }
        if (aliveIndices.isEmpty()) return null;

        while (true) {
            System.out.println("\nChoose target:");
            for (int idx : aliveIndices) {
                Monster m = enemies.get(idx);
                System.out.println("  " + (idx + 1) + ". " + m.getName()
                        + " (HP " + m.getHp() + ")");
            }
            System.out.println("  Q. Cancel");
            System.out.print("> ");

            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("Q")) {
                return null;
            }

            try {
                int choice = Integer.parseInt(input) - 1;
                if (aliveIndices.contains(choice)) {
                    return enemies.get(choice);
                }
            } catch (NumberFormatException e) {
                // ignore, just re-prompt
            }
            System.out.println("Invalid target.");
        }
    }

    
}
