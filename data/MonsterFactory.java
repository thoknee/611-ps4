/*
 * 
 *  Interface used to create monster factories. Will be implemented for any type of monster
 * 
 *  Written by Tony Ponomarev
 * 
 * 
 */

package data;

import entities.Monster;
import java.util.List;

public interface MonsterFactory<T extends Monster> {
    List<T> loadMonsters();
}
