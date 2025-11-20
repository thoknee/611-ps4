/*
 * 
 * 
 *  Abstract factory for a factory of hero factories 
 * 
 * Written by Tony Ponomav
 * 
 * 
 */

package data;

import entities.Hero;
import java.util.List;

public interface HeroFactory<T extends Hero> {
    List<T> loadHeroes();
}
