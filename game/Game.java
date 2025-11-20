/*
 * 
 *  Main game loop and some setup helpers
 * 
 *  Written by Tony Ponomarev
 * 
 * 
 */

package game;

import entities.Hero;
import entities.Paladin;
import entities.Party;
import entities.Sorcerer;
import entities.Warrior;
import events.BattleEvent;
import events.MarketEvent;
import items.Armor;
import items.Potion;
import items.Weapon;
import events.Event;
import world.TileType;
import world.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import data.HeroFactory;
import data.PaladinFactory;
import data.SorcererFactory;
import data.WarriorFactory;

public class Game {

    private static double battleProb = 0.3;

    private WorldMap map;
    private Party party;
    private boolean running = true;


    private final BattleEngine battleEngine = new BattleEngine();
    private final MarketManagement marketManager = new MarketManagement(); // or MarketManager if that's your name
    private final Random rng = new Random();

    public void start() {
        Scanner sc = new Scanner(System.in);
        Display.showIntroduction();
        Display.showRules();
        
        // load heroes 
        List<Hero> available = loadAllHeroes();

        // choose heroes
        List<Hero> chosen = chooseHeroes(available);
        

        // Find correct world
        setupWorldAndParty(chosen, sc);

        // start game loop
        mainLoop();
    }

    private List<Hero> loadAllHeroes() {
        List<Hero> all = new ArrayList<>();

        HeroFactory<Warrior> warriorFactory =
                new WarriorFactory("Legends_Monsters_and_Heroes/Warriors.txt");
        HeroFactory<Sorcerer> sorcererFactory =
                new SorcererFactory("Legends_Monsters_and_Heroes/Sorcerers.txt");
        HeroFactory<Paladin> paladinFactory =
                new PaladinFactory("Legends_Monsters_and_Heroes/Paladins.txt");

        all.addAll(warriorFactory.loadHeroes());
        all.addAll(sorcererFactory.loadHeroes());
        all.addAll(paladinFactory.loadHeroes());

        return all;
    }

    private List<Hero> chooseHeroes(List<Hero> available) {
        Scanner sc = new Scanner(System.in);
        List<Hero> chosen = new ArrayList<>();

        int partySize = 0;
        while (partySize < 1 || partySize > 3) {
            System.out.print("How many heroes do you want in your party? (1-3): ");
            String input = sc.nextLine().trim();
            try {
                partySize = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                partySize = 0;
            }
            if (partySize < 1 || partySize > 3) {
                System.out.println("Please enter 1, 2, or 3.");
            }
        }

        List<Hero> pool = new ArrayList<>(available);

        for (int i = 0; i < partySize; i++) {
            System.out.println("\nChoose hero #" + (i + 1) + ":");

            Display.showHeroList(pool);
            
            System.out.print("Enter number: ");

            Hero selected = null;
            while (selected == null) {
                String input = sc.nextLine().trim();
                try {
                    int choice = Integer.parseInt(input) - 1;
                    if (choice >= 0 && choice < pool.size()) {
                        selected = pool.get(choice);
                    } else {
                        System.out.print("Invalid choice, try again: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Please enter a valid number: ");
                }
            }

            chosen.add(selected);
            pool.remove(selected);
        }

        return chosen;
    }

    private void mainLoop() {
        Scanner sc = new Scanner(System.in);

        Display.showIntroduction();
        Display.showRules();
        map.print(party); 

        while (running && !party.allFainted()) {
            
            map.print(party); 
            System.out.println("W/A/S/D to move, I to show party, Q to quit, H to see the rules");

            String input = sc.nextLine().trim();
            if (input.isEmpty()) continue;
            char cmd = Character.toUpperCase(input.charAt(0));

            int prevRow = party.getRow();
            int prevCol = party.getCol();

            switch (cmd) {
                case 'W':
                    map.moveUp(party);
                    break;
                case 'A':
                    map.moveLeft(party);
                    break;
                case 'S':
                    map.moveDown(party);
                    break;
                case 'D':
                    map.moveRight(party);
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
                                            openHeroInventoryMenu(h, sc);
                                        }

                                    } else {
                                        System.out.println("Invalid hero number.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Not a valid number.");
                                }
                            }
                        }

                        break;
                    }
                case 'Q':
                    running = false;
                    break;
                case 'H':
                    Display.showRules();
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }

            // Only trigger events if the party actually moved to a new tile
            if (running && (party.getRow() != prevRow || party.getCol() != prevCol)) {
                handleTileEvents();
            }
            
        }

        System.out.println("Game over.");
    }

    private void handleTileEvents() {
        TileType type = map.getTile(party.getRow(), party.getCol()).getType();
        Event event = null;

        switch (type) {
            case MARKET: {
                System.out.println("You stepped on a MARKET tile.");
                event = new MarketEvent(marketManager);
                break;
            }
            case COMMON: {
                System.out.println("You stepped on a COMMON tile.");
                if (shouldTriggerBattle()) {
                    event = new BattleEvent(battleEngine);
                } else {
                    System.out.println("It's quiet... no battle this time.");
                }
                break;
            }
            case INACCESSIBLE: {
                // Shouldn't happen
                System.out.println("You shouldn't be able to stand here!");
                break;
            }
        }

        if (event != null) {
            event.trigger(party, map);
        }
    }

    private boolean shouldTriggerBattle() {
        double chance = battleProb;
        return rng.nextDouble() < chance;
    }


    // allows players to reroll to find a map they like
    private void setupWorldAndParty(List<Hero> chosen, Scanner sc) {
        while (true) {
            
            WorldMap map = new WorldMap(8);
            int[] startPos = map.startPos();
            this.party = new Party(chosen, startPos[0], startPos[1]);

            System.out.println("\nGenerated world map:");
            map.print(party); 

            System.out.print("Do you like this map? (Y to accept, N to reroll): ");
            String input = sc.nextLine().trim().toUpperCase();

            if (input.equals("Y")) {

                this.map = map;
                startPos = map.startPos();
                this.party = new Party(chosen, startPos[0], startPos[1]);
                System.out.println("Great, let's begin your adventure!");
                break;
            } else if (input.equals("N")) {
                System.out.println("Generating New Map");
                // loop continues, generate a new map
            } else {
                System.out.println("Please enter Y or N.");
            }
        }
}
private void openHeroInventoryMenu(Hero h, Scanner sc) {

    boolean usingInventory = true;

    while (usingInventory) {
        Display.showHeroInventory(h);

        System.out.println("Inventory Options:");
        System.out.println("  W - Equip a weapon");
        System.out.println("  A - Equip armor");
        System.out.println("  P - Use a potion");
        System.out.println("  Q - Exit inventory");
        System.out.print("> ");

        String choice = sc.nextLine().trim().toUpperCase();

        switch (choice) {

            case "W": {
                List<Weapon> weapons = h.getInventory().getWeapons();
                if (weapons.isEmpty()) {
                    System.out.println("No weapons available.");
                    break;
                }

                System.out.println("Choose a weapon to equip:");
                for (int i = 0; i < weapons.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + weapons.get(i));
                }
                System.out.print("> ");

                try {
                    int idx = Integer.parseInt(sc.nextLine()) - 1;
                    if (idx >= 0 && idx < weapons.size()) {
                        Weapon w = weapons.get(idx);
                        h.equipWeapon(w);
                        System.out.println("Equipped " + w.getName() + "!");
                    } else {
                        System.out.println("Invalid selection.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number.");
                }
                break;
            }

            case "A": {
                List<Armor> armors = h.getInventory().getArmors();
                if (armors.isEmpty()) {
                    System.out.println("No armor available.");
                    break;
                }

                System.out.println("Choose armor to equip:");
                for (int i = 0; i < armors.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + armors.get(i));
                }
                System.out.print("> ");

                try {
                    int idx = Integer.parseInt(sc.nextLine()) - 1;
                    if (idx >= 0 && idx < armors.size()) {
                        Armor a = armors.get(idx);
                        h.equipArmor(a);
                        System.out.println("Equipped " + a.getName() + "!");
                    } else {
                        System.out.println("Invalid selection.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number.");
                }
                break;
            }

            case "P": {
                List<Potion> potions = h.getInventory().getPotions();
                if (potions.isEmpty()) {
                    System.out.println("No potions available.");
                    break;
                }

                System.out.println("Choose a potion to use:");
                for (int i = 0; i < potions.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + potions.get(i));
                }
                System.out.print("> ");

                try {
                    int idx = Integer.parseInt(sc.nextLine()) - 1;
                    if (idx >= 0 && idx < potions.size()) {
                        Potion p = potions.get(idx);
                        h.applyPotion(p);
                        h.getInventory().removeItem(p);

                        System.out.println("Used " + p.getName() + "!");
                    } else {
                        System.out.println("Invalid selection.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number.");
                }
                break;
            }

            case "Q":
                usingInventory = false;
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }
}



    public static void main(String[] args) {
        new Game().start();
    }
}
