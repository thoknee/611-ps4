/*
 * 
 *  Handles the battle event - starts the battle upon trigger
 * 
 *  Written by Tony Ponomarev
 * 
 */

package events;

import entities.Party;
import game.BattleEngine;
import world.WorldMap;

public class BattleEvent extends Event {

    private final BattleEngine battleEngine;

    public BattleEvent(BattleEngine battleEngine) {
        this.battleEngine = battleEngine;
    }

    @Override
    public void trigger(Party party, WorldMap map) {
        battleEngine.startBattle(party);
    }
}
