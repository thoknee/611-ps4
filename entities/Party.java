/*
 * 
 *  Party class.
 *  Represents the group of heroes moving together on the map.
 * 
 *  Written by Tony Ponomarev
 * 
 */

package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Party {

    private final List<Hero> heroes = new ArrayList<>();
    private int row; 
    private int col;


    public Party(List<Hero> initialHeroes, int startRow, int startCol) {
        if (initialHeroes == null || initialHeroes.isEmpty() || initialHeroes.size() > 3) {
            throw new IllegalArgumentException("Party size must be between 1 and 3 heroes.");
        }
        heroes.addAll(initialHeroes);
        this.row = startRow;
        this.col = startCol;
    }


    public List<Hero> getHeroes() {
        return Collections.unmodifiableList(heroes);
    }

    public int size() {
        return heroes.size();
    }

    public Hero getHero(int index) {
        return heroes.get(index);
    }

    public void addHero(Hero hero) {
        if (heroes.size() >= 3) {
            throw new IllegalStateException("Party already has 3 heroes.");
        }
        heroes.add(hero);
    }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }

    // --- Battle-related helpers ---

    public boolean allFainted() {
        for (Hero h : heroes) {
            if (!h.isFainted()) {
                return false;
            }
        }
        return true;
    }

    public int getHighestLevel() {
        int max = 0;
        for (Hero h : heroes) {
            if (h.getLevel() > max) {
                max = h.getLevel();
            }
        }
        return max;
    }

    public int getAliveCount() {
        int count = 0;
        for (Hero h : heroes) {
            if (!h.isFainted()) {
                count++;
            }
        }
        return count;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Party at (" + row + "," + col + "):\n");
        for (Hero h : heroes) {
            sb.append("  ").append(h).append("\n");
        }
        return sb.toString();
    }
}
