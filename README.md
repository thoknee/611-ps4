### Legends: Monsters & Heroes

This is an RPG inspired game built in java through the terminal. In the game you act as a group of heroes traversing a dangerous landscape full of monsters ready to attack. As you travel and battle monsters your heroe's level up, earn gold, and become stronger simultaenously coming across stronger and stronger monsters. The goal seems simple but achieving it isn't as easy: Survive.

## Game Overview

# General
You can choose your team of up to three heroes to take on this dangerous landscape as they embark on a randomly generated grid like map. On this map your party of heroes can run into 3 different kinds of tiles.

 - Common tiles: The normal tile distinguished by a ".", these are the tiles you have to worry about. Upon entering one there is a random chance that monsters attack. Be ready and stay vigilent.
 - Market tiles: You can tell it is a market with an "M" in the corner and here you can buy and sell powerful items that will greatly assist you in your survival.
 - Inaccessible tiles: Distinguished by a rough "mountain" like design

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

   

