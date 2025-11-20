/*
 * 
 *  Creates an instance of a market upon event trigger 
 *  This way, it keeps the same inventory and bought/sold items
 * 
 *  Written by Tony Ponomarev
 * 
 * 
 */

package game;

import data.ArmorFactory;
import data.ItemFactory;
import data.PotionFactory;
import data.SpellFactory;
import data.WeaponFactory;
import entities.Hero;
import entities.Party;
import items.*;

import java.util.*;

public class MarketManagement {

    // All possible items in the game (from the .txt files)
    private final List<Item> catalog = new ArrayList<>();

    // Per-tile market stock
    private final Map<String, List<Item>> markets = new HashMap<>();

    private final Random rng = new Random();

    public MarketManagement() {
        ItemFactory<Weapon> weaponFactory =
                new WeaponFactory("Legends_Monsters_and_Heroes/Weaponry.txt");
        ItemFactory<Armor> armorFactory =
                new ArmorFactory("Legends_Monsters_and_Heroes/Armory.txt");
        ItemFactory<Potion> potionFactory =
                new PotionFactory("Legends_Monsters_and_Heroes/Potions.txt");
        ItemFactory<Spell> iceSpellFactory =
                new SpellFactory("Legends_Monsters_and_Heroes/IceSpells.txt", SpellType.ICE);
        ItemFactory<Spell> fireSpellFactory =
                new SpellFactory("Legends_Monsters_and_Heroes/FireSpells.txt", SpellType.FIRE);
        ItemFactory<Spell> lightningSpellFactory =
                new SpellFactory("Legends_Monsters_and_Heroes/LightningSpells.txt", SpellType.LIGHTNING);

        catalog.addAll(weaponFactory.loadItems());
        catalog.addAll(armorFactory.loadItems());
        catalog.addAll(potionFactory.loadItems());
        catalog.addAll(iceSpellFactory.loadItems());
        catalog.addAll(fireSpellFactory.loadItems());
        catalog.addAll(lightningSpellFactory.loadItems());
    }

    public void enterMarket(Party party) {
        Scanner sc = new Scanner(System.in);

        // Key for this tile's market
        String key = party.getRow() + "," + party.getCol();

        // existing stock or create a new random market
        List<Item> stock = markets.get(key);
        if (stock == null) {
            stock = generateRandomStock();
            markets.put(key, stock);
        }

        boolean inMarket = true;
        while (inMarket) {
            System.out.println("\nMARKET at (" + key + ")");
            System.out.println("Who wants to enter the market?");
            for (int i = 0; i < party.size(); i++) {
                Hero h = party.getHero(i);
                System.out.println((i + 1) + ". " + h.getName() + " (Gold: " + h.getGold() + ")");
            }
            System.out.println("Q. Leave market");

            System.out.print("Choice: ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("Q")) {
                inMarket = false;
                break;
            }

            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx < 0 || idx >= party.size()) {
                    System.out.println("Invalid hero choice.");
                    continue;
                }
                Hero hero = party.getHero(idx);
                heroMarketMenu(hero, stock, sc);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number or Q.");
            }
        }

        System.out.println("You leave the market.");
    }

    // Gets random stock
    private List<Item> generateRandomStock() {
        if (catalog.isEmpty()) {
            return new ArrayList<>();
        }

        // Shuffle a copy of the catalog
        List<Item> shuffled = new ArrayList<>(catalog);
        Collections.shuffle(shuffled, rng);

        // Decide how many items this market sells
        int minItems = 1;
        int maxItems = 3;
        int count = minItems + rng.nextInt(Math.max(1, maxItems - minItems + 1));
        count = Math.min(count, shuffled.size());

        return new ArrayList<>(shuffled.subList(0, count));
    }


    private void heroMarketMenu(Hero hero, List<Item> stock, Scanner sc) {
        boolean done = false;

        while (!done) {
            System.out.println("\n=== MARKET: " + hero.getName() + " ===");
            System.out.println("Gold: " + hero.getGold());
            System.out.println("1. Buy items");
            System.out.println("2. Sell items");
            System.out.println("3. View inventory");
            System.out.println("4. Done with this hero");

            System.out.print("Choice: ");
            String input = sc.nextLine().trim();

            switch (input) {
                case "1":
                    buyMenu(hero, stock, sc);
                    break;
                case "2":
                    sellMenu(hero, stock, sc);
                    break;
                case "3":
                    System.out.println(hero.getInventory());
                    break;
                case "4":
                    done = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    private void buyMenu(Hero hero, List<Item> stock, Scanner sc) {
        while (true) {
            System.out.println("\nBUY: ");
            if (stock.isEmpty()) {
                System.out.println("This market has nothing to sell right now.");
                return;
            }

            for (int i = 0; i < stock.size(); i++) {
                Item item = stock.get(i);
                System.out.println((i + 1) + ". " + item
                        + " [Req Lv: " + item.getLevelRequirement() + "]");
            }
            System.out.println("Q. Back");
            System.out.println("Gold: " + hero.getGold());
            System.out.print("Select item to buy: ");

            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("Q")) {
                return;
            }

            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx < 0 || idx >= stock.size()) {
                    System.out.println("Invalid item choice.");
                    continue;
                }

                Item item = stock.get(idx);

                if (hero.getLevel() < item.getLevelRequirement()) {
                    System.out.println("You are not high enough level to buy this.");
                    continue;
                }

                if (hero.getGold() < item.getPrice()) {
                    System.out.println("You don't have enough gold.");
                    continue;
                }

                hero.spendGold(item.getPrice());
                hero.getInventory().addItem(item);
                stock.remove(idx);

                System.out.println(hero.getName() + " bought " + item.getName() + ".");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number or Q.");
            }
        }
    }


    private void sellMenu(Hero hero, List<Item> stock, Scanner sc) {
        while (true) {
            System.out.println("\nSELL: ");
            List<Item> items = hero.getInventory().getAllItems();
            if (items.isEmpty()) {
                System.out.println("You have nothing to sell.");
                return;
            }

            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                int sellPrice = item.getPrice() / 2;
                System.out.println((i + 1) + ". " + item + " [Sell for: " + sellPrice + "]");
            }
            System.out.println("Q. Back");

            System.out.print("Select item to sell: ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("Q")) {
                return;
            }

            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx < 0 || idx >= items.size()) {
                    System.out.println("Invalid item choice.");
                    continue;
                }

                Item item = items.get(idx);
                int sellPrice = item.getPrice() / 2;

                hero.addGold(sellPrice);
                hero.getInventory().removeItem(item);

                // This market can now sell the item
                stock.add(item);

                System.out.println(hero.getName() + " sold " + item.getName()
                        + " for " + sellPrice + " gold.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number or Q.");
            }
        }
    }
}
