/*
 * 
 *   The other type of event, a market
 * 
 *  Written by Tony Ponomarev
 * 
 * 
 */



package events;

import entities.Party;
import game.MarketManagement;
import world.WorldMap;

public class MarketEvent extends Event {

    private final MarketManagement marketManager;

    public MarketEvent(MarketManagement marketManager) {
        this.marketManager = marketManager;
    }

    @Override
    public void trigger(Party party, WorldMap map) {
        marketManager.enterMarket(party);
    }
}
