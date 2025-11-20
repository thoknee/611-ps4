package data;

import items.Item;
import java.util.List;

public interface ItemFactory<T extends Item> {
    List<T> loadItems();
}