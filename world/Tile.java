/* 
 * 
 *  A tile on the grid- holds a typeType
 * 
 *  Written by Tony Ponomarev
 * 
 * 
 */



package world;

public class Tile {

    private final TileType type;

    public Tile(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return type;
    }

    public boolean isAccessible() {
        return type != TileType.INACCESSIBLE;
    }

    public boolean isMarket() {
        return type == TileType.MARKET;
    }

    public boolean isCommon() {
        return type == TileType.COMMON;
    }
}
