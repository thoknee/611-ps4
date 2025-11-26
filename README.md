### Legends: Monsters & Heroes

This is an RPG inspired game built in java through the terminal. In the game you act as a group of heroes traversing a dangerous landscape full of monsters ready to attack. As you travel and battle monsters your heroe's level up, earn gold, and become stronger simultaenously coming across stronger and stronger monsters. The goal seems simple but achieving it isn't as easy: Survive.

## Game Overview

# General
You can choose your team of up to three heroes to take on this dangerous landscape as they embark on a randomly generated grid like map. On this map your party of heroes can run into 3 different kinds of tiles.

 - Common tiles: The normal tile distinguished by a ".", these are the tiles you have to worry about. Upon entering one there is a random chance that monsters attack. Be ready and stay vigilent.
 - Market tiles: You can tell it is a market with an "M" in the corner and here you can buy and sell powerful items that will greatly assist you in your survival.
 - Inaccessible tiles: Distinguished by a rough "mountain" like design
   ^
  / \
  ^^^

   Movement between tiles is simple:
   - W/A/S/D to move in up, left, down, and right accordingly
   - I: access party information
   - Q: Can be used to quit the game

# Combat

Upon entering a common tile monsters can ambush you at any second. Upon ambush, you are forced to battle them. Here each hero will get a turn to attack their choice of monster. This can be done with a weapon or a spell purchasable in a market. In battle the following commands are useful: 

- A: normal attack
- S: use a spell(beware, you must not have a two handed weapon)
- P: use a potion(you cannot be using a two handed weapon)
- E: Allow your hero to equip weapon or armor
- I: Show hero stats
- R: Show enemy stats

The combat is ultimately turn based as you alternate attacks with the monsters. Be cautious of scaling enemies, as your heroes level up, so will the monsters so don't get too cocky.

## General outline of code and system design

- Main.java: creates the instance of the Game
- Game.java: Creates everything needed for the game(map, heroes, enemies) as well as handles the actual game loop.
- Abstract "Event":
   - Battle: an extension of the Event and handles battles
   - Market: An extension of the Event and handles the buying and selling of gear within a market
- Entities(Abstract character -> hero, monster)
   - Extended by each specific type namely paladin, sorcerer, warrior and dragon, exoskeleton, spirit.
- Items(Abstract extended by each)
   - armor, potion, spell, and weapons
   - Handles the inventory class as well
- Abstract(Hero, monster, potion, item, spell) factory:
   - As an interface for creating multiple different factories. Each one is in charge of reading the .txt file inputted and creating the associated monsters, items, heroes, etc.)
- World generation
   - Tile: an array of tiles
   - worldMap: generates the array of tiles
 

## How to play

This game is based in the terminal so in your terminal you need to cd into the correct directory. From there:

``` javac Main.java```

upon compiling you can then run the game with:

``` java Main ```

Upon running it, here is the introduction and beginning of the game:

```
Welcome to Legends, Monsters and heroes
A world full of magic, monsters, and danger.
You lead a small party of heroes through a dangerous land of markets,
mystic spells, and random ambushes.

You only have one goal
  - Survive
To do so: 
  - Defeat monsters
  - Grow stronger
  - Stack gold
  - Buy better gear

Move carefully, shop wisely, keep your friends close, and your enemies closer

How to Play
World
------------------------------------------------------
  - The world is a grid of tiles:
      ^ : Inaccessible (you cannot enter)
      M : Market (buy/sell items)
      . : Common tile. Beware, there is a chance of battle
      P : Your party's current position
  - Move with W (up), A (left), S (down), D (right).
  - You can quit at any time with Q.

MARKETS
------------------------------------------------------
  - Step on an M tile, then press M to enter the market.
  - Each hero has their own gold and inventory.
  - You can:
      * Buy items
      * Sell items

In Battle
------------------------------------------------------
  - On common tiles, you may randomly encounter monsters.
  - In battle, you act first
  - On a hero's turn you can:
      A : Attack with weapon
      S : Cast a spell (must need both hands)
      P : Use a potion (must need both hands)
      E : Equip a weapon or armor
      I : Show detailed info on all heroes
      R : Show detailed info on all monsters
  - Heroes and monsters can dodge attacks based on agility
  - If a hero's HP drops to 0, they faint for the rest of the battle.
  - If all heroes faint, the game is over.
  - If all monsters are defeated, heroes gain gold and experience.

Grow Stronger
------------------------------------------------------
  - Heroes gain experience after winning battles.
  - When a hero levels up, their HP/MP and stats improve.
  - Some hero types (Warrior, Sorcerer, Paladin) favor certain stats.
  - Use gold in markets to buy better weapons, armor, potions, and spells.

Other useful keys:
------------------------------------------------------
  I : Show party status
  H : Show the rules again
  Q : Quit the game

How many heroes do you want in your party? (1-3): 1

Choose hero #1:
#   Name                 Class      Lv   HP     STR    DEX    AGI    Gold  
------------------------------------------------------
1   Gaerdal_Ironhand     Warrior    1    100    700    600    500    1354  
2   Sehanine_Monnbow     Warrior    1    100    700    500    800    2500  
3   Muamman_Duathall     Warrior    1    100    900    750    500    2546  
4   Flandal_Steelskin    Warrior    1    100    750    700    650    2500  
5   Undefeated_Yoj       Warrior    1    100    800    700    400    2500  
6   Eunoia_Cyn           Warrior    1    100    700    600    800    2500  
7   Rillifane_Rallathil  Sorcerer   1    100    750    500    450    2500  
8   Segojan_Earthcaller  Sorcerer   1    100    800    650    500    2500  
9   Reign_Havoc          Sorcerer   1    100    800    800    800    2500  
10  Reverie_Ashels       Sorcerer   1    100    800    400    700    2500  
11  Kalabar              Sorcerer   1    100    850    600    400    2500  
12  Skye_Soar            Sorcerer   1    100    700    500    400    2500  
13  Parzival             Paladin    1    100    750    700    650    2500  
14  Sehanine_Moonbow     Paladin    1    100    750    700    700    2500  
15  Skoraeus_Stonebones  Paladin    1    100    650    350    600    2500  
16  Garl_Glittergold     Paladin    1    100    600    400    500    2500  
17  Amaryllis_Astra      Paladin    1    100    500    500    500    2500  
18  Caliber_Heist        Paladin    1    100    400    400    400    2500  
------------------------------------------------------

Enter number: 17

Generated world map:
+---+---+---+---+---+---+---+---+
|   |  M|   |   |  M|   | ^ |   |
| . |   | . | . |   | . |/ \| . |
|   |   |   |   |   |   |^^^|   |
+---+---+---+---+---+---+---+---+
|   |   |   |   |   |   |  M|   |
| . | . | . | . | . | . |   | P |
|   |   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+---+
| ^ |   |  M|   |   |   |  M|   |
|/ \| . |   | . | . | . |   | . |
|^^^|   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+---+
|  M|  M|   |   |   |   | ^ |   |
|   |   | . | . | . | . |/ \| . |
|   |   |   |   |   |   |^^^|   |
+---+---+---+---+---+---+---+---+
|   |   |   |   |   |   | ^ |   |
| . | . | . | . | . | . |/ \| . |
|   |   |   |   |   |   |^^^|   |
+---+---+---+---+---+---+---+---+
|   |   |   |   | ^ |   |  M|   |
| . | . | . | . |/ \| . |   | . |
|   |   |   |   |^^^|   |   |   |
+---+---+---+---+---+---+---+---+
| ^ |   |   |   |   |  M|   | ^ |
|/ \| . | . | . | . |   | . |/ \|
|^^^|   |   |   |   |   |   |^^^|
+---+---+---+---+---+---+---+---+
|   |  M|   |  M|   |   |  M|   |
| . |   | . |   | . | . |   | . |
|   |   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+---+
Do you like this map? (Y to accept, N to reroll): y
Great, let's begin your adventure!
Welcome to Legends, Monsters and heroes
A world full of magic, monsters, and danger.
You lead a small party of heroes through a dangerous land of markets,
mystic spells, and random ambushes.

You only have one goal
  - Survive
To do so: 
  - Defeat monsters
  - Grow stronger
  - Stack gold
  - Buy better gear

Move carefully, shop wisely, keep your friends close, and your enemies closer

How to Play
World
------------------------------------------------------
  - The world is a grid of tiles:
      ^ : Inaccessible (you cannot enter)
      M : Market (buy/sell items)
      . : Common tile. Beware, there is a chance of battle
      P : Your party's current position
  - Move with W (up), A (left), S (down), D (right).
  - You can quit at any time with Q.

MARKETS
------------------------------------------------------
  - Step on an M tile, then press M to enter the market.
  - Each hero has their own gold and inventory.
  - You can:
      * Buy items
      * Sell items

In Battle
------------------------------------------------------
  - On common tiles, you may randomly encounter monsters.
  - In battle, you act first
  - On a hero's turn you can:
      A : Attack with weapon
      S : Cast a spell (must need both hands)
      P : Use a potion (must need both hands)
      E : Equip a weapon or armor
      I : Show detailed info on all heroes
      R : Show detailed info on all monsters
  - Heroes and monsters can dodge attacks based on agility
  - If a hero's HP drops to 0, they faint for the rest of the battle.
  - If all heroes faint, the game is over.
  - If all monsters are defeated, heroes gain gold and experience.

Grow Stronger
------------------------------------------------------
  - Heroes gain experience after winning battles.
  - When a hero levels up, their HP/MP and stats improve.
  - Some hero types (Warrior, Sorcerer, Paladin) favor certain stats.
  - Use gold in markets to buy better weapons, armor, potions, and spells.

Other useful keys:
------------------------------------------------------
  I : Show party status
  H : Show the rules again
  Q : Quit the game

+---+---+---+---+---+---+---+---+
|   |  M|   |   |  M|   | ^ |   |
| . |   | . | . |   | . |/ \| . |
|   |   |   |   |   |   |^^^|   |
+---+---+---+---+---+---+---+---+
|   |   |   |   |   |   |  M|   |
| . | . | . | . | . | . |   | P |
|   |   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+---+
| ^ |   |  M|   |   |   |  M|   |
|/ \| . |   | . | . | . |   | . |
|^^^|   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+---+
|  M|  M|   |   |   |   | ^ |   |
|   |   | . | . | . | . |/ \| . |
|   |   |   |   |   |   |^^^|   |
+---+---+---+---+---+---+---+---+
|   |   |   |   |   |   | ^ |   |
| . | . | . | . | . | . |/ \| . |
|   |   |   |   |   |   |^^^|   |
+---+---+---+---+---+---+---+---+
|   |   |   |   | ^ |   |  M|   |
| . | . | . | . |/ \| . |   | . |
|   |   |   |   |^^^|   |   |   |
+---+---+---+---+---+---+---+---+
| ^ |   |   |   |   |  M|   | ^ |
|/ \| . | . | . | . |   | . |/ \|
|^^^|   |   |   |   |   |   |^^^|
+---+---+---+---+---+---+---+---+
|   |  M|   |  M|   |   |  M|   |
| . |   | . |   | . | . |   | . |
|   |   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+---+
+---+---+---+---+---+---+---+---+
|   |  M|   |   |  M|   | ^ |   |
| . |   | . | . |   | . |/ \| . |
|   |   |   |   |   |   |^^^|   |
+---+---+---+---+---+---+---+---+
|   |   |   |   |   |   |  M|   |
| . | . | . | . | . | . |   | P |
|   |   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+---+
| ^ |   |  M|   |   |   |  M|   |
|/ \| . |   | . | . | . |   | . |
|^^^|   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+---+
|  M|  M|   |   |   |   | ^ |   |
|   |   | . | . | . | . |/ \| . |
|   |   |   |   |   |   |^^^|   |
+---+---+---+---+---+---+---+---+
|   |   |   |   |   |   | ^ |   |
| . | . | . | . | . | . |/ \| . |
|   |   |   |   |   |   |^^^|   |
+---+---+---+---+---+---+---+---+
|   |   |   |   | ^ |   |  M|   |
| . | . | . | . |/ \| . |   | . |
|   |   |   |   |^^^|   |   |   |
+---+---+---+---+---+---+---+---+
| ^ |   |   |   |   |  M|   | ^ |
|/ \| . | . | . | . |   | . |/ \|
|^^^|   |   |   |   |   |   |^^^|
+---+---+---+---+---+---+---+---+
|   |  M|   |  M|   |   |  M|   |
| . |   | . |   | . | . |   | . |
|   |   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+---+
W/A/S/D to move, I to show party, Q to quit, H to see the rules
```


   

