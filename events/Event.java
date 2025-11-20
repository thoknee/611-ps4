/*
 * 
 *  Abstract class - Event
 * 
 *  Right now only events are battle and market. This is to allow for other events
 *  
 * 
 *  Written by Tony Ponomarev
 * 
 * 
 */
package events;

import entities.Party;
import world.WorldMap;

public abstract class Event {
    
    public abstract void trigger(Party party, WorldMap map);
}
