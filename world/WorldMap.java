/*
 * 
 *  Generates and displays the world map
 * 
 *  Written by Tony Ponomarev
 * 
 */


package world;

import entities.Party;

import java.util.Random;

public class WorldMap {

    private final int size;
    private final Tile[][] grid;
    private final Random rng = new Random();

    public WorldMap(int size) {
        this.size = size;
        this.grid = new Tile[size][size];
        generateRandomMap();
    }

    public int getSize() {
        return size;
    }

    public Tile getTile(int row, int col) {
        return grid[row][col];
    }

    private void generateRandomMap() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                double p = rng.nextDouble();
                TileType type;
                if (p < 0.20) {
                    type = TileType.INACCESSIBLE;
                } else if (p < 0.4) {
                    type = TileType.MARKET;
                } else {
                    type = TileType.COMMON;
                }
                grid[r][c] = new Tile(type);
            }
        }

        // make sure starting tile is accessible (say top-left)
        grid[0][0] = new Tile(TileType.COMMON);
    }

    public boolean canMoveTo(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) return false;
        return grid[row][col].isAccessible();
    }

    public void moveUp(Party party) {
        int r = party.getRow();
        int c = party.getCol();
        if (canMoveTo(r - 1, c)) {
            party.setPosition(r - 1, c);
        } else {
            System.out.println("Cannot move up!");
        }
    }

    public void moveDown(Party party) {
        int r = party.getRow();
        int c = party.getCol();
        if (canMoveTo(r + 1, c)) {
            party.setPosition(r + 1, c);
        } else {
            System.out.println("Cannot move down!");
        }
    }

    public void moveLeft(Party party) {
        int r = party.getRow();
        int c = party.getCol();
        if (canMoveTo(r, c - 1)) {
            party.setPosition(r, c - 1);
        } else {
            System.out.println("Cannot move left!");
        }
    }

    public void moveRight(Party party) {
        int r = party.getRow();
        int c = party.getCol();
        if (canMoveTo(r, c + 1)) {
            party.setPosition(r, c + 1);
        } else {
            System.out.println("Cannot move right!");
        }
    }

    public int[] startPos() {
        while (true) {
            int r = rng.nextInt(size);
            int c = rng.nextInt(size);
            if (grid[r][c].isAccessible()) {
                return new int[]{r, c};
            }
        }
    }

    public void print(Party party) {
        int size = this.size;

        // horizontal separator line for one row of tiles
        StringBuilder sepBuilder = new StringBuilder();
        for (int c = 0; c < size; c++) {
            sepBuilder.append("+---");
        }
        sepBuilder.append("+");
        String separator = sepBuilder.toString();

        for (int r = 0; r < size; r++) {
            // top separator for this row of tiles
            System.out.println(separator);

            // each tile row is 3 text rows (subRow 0,1,2)
            for (int subRow = 0; subRow < 3; subRow++) {
                StringBuilder line = new StringBuilder();

                for (int c = 0; c < size; c++) {
                    boolean hasParty = (r == party.getRow() && c == party.getCol());
                    Tile tile = grid[r][c];
                    TileType type = tile.getType();

                    String patternRow = getTilePatternRow(type, subRow, hasParty);

                    // prepend vertical border and content
                    line.append("|").append(patternRow);
                }
                line.append("|"); // closing border at end of row

                System.out.println(line.toString());
            }
        }

        // bottom border after last row
        System.out.println(separator);
    }


    private String getTilePatternRow(TileType type, int subRow, boolean hasParty) {
        String row;

        switch (type) {
            case INACCESSIBLE:
                // mountain-ish
                if (subRow == 0) row = " ^ ";
                else if (subRow == 1) row = "/ \\";
                else row = "^^^";
                break;

            case MARKET:
                if (subRow == 0) row = "  M";
                else if (subRow == 1) row = "   ";
                else row = "   ";
                break;

            case COMMON:
            default:
                if (subRow == 0) row = "   ";
                else if (subRow == 1) row = " . ";
                else row = "   ";
                break;
        }

        // If the party is on this tile, override the center char with 'P'
        if (hasParty && subRow == 1) {
            // middle row: replace char at index 1
            char[] chars = row.toCharArray();
            chars[1] = 'P';
            row = new String(chars);
        }

        return row;
    }

}
