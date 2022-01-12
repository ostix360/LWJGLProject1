package fr.ostix.game.inventory;

import fr.ostix.game.items.*;

import java.util.*;

public class ShopInventory extends Inventory {
    private final HashMap<Integer, ItemStack> items = new HashMap<>();
    private final ItemTab shopTab;

    public ShopInventory() {
        super("Shop");
        shopTab = ItemTab.newEmptyTab("Shop", 10);
        setItems();
    }

    private void setItems() {
        items.put(10, new ItemStack(Items.potion, 1));
        int i = 0;
        for (ItemStack is : items.values()) {
            shopTab.getSlots()[i].setStack(is);
            i++;
        }
    }

    @Override
    public void open() {
        super.open();
        shopTab.startRendering();
    }

    @Override
    public void update() {
        super.update();
        shopTab.update();
    }

    @Override
    public void render() {
        shopTab.render();
    }

    @Override
    public void close() {
        super.close();
        shopTab.stopRendering();
    }
}
