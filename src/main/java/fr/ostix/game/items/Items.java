package fr.ostix.game.items;

import java.util.*;

public class Items {

    //TODO add bootstrap to register all Entities and Items
    private static final HashMap<Integer, Item> items = new HashMap<>();

    public static Item potion = registerItem(new ItemPotion(0, "Potion", "Ce-ci est une potion j'aime les patates et les concombres"));


    private static Item registerItem(Item i) {
        items.put(i.getId(), i);
        return i;
    }


    public static Item getItem(int id) {
        return items.get(id);
    }
}
