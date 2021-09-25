package fr.ostix.game.inventory;

import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.items.Items;

public class ItemTab {
    private final String name;
    public Slot[] slots;

    private ItemTab(String name, Slot[] slots) {
        this.name = name;
        this.slots = slots;
    }

    public static ItemTab newEmptyTab(String name, int slotCount) {

        return new ItemTab(name, generate(slotCount));
    }

    private static Slot[] generate(int slotCount) {
        Slot[] slots = new Slot[slotCount];
        int index = 0;
        for (int x = 0; x < slotCount / 4; x++) {
            for (int y = 0; y < slotCount / 5; y++) {
                slots[index] = new Slot(270 + x * 140 + x * 8, 107 + y * 140 + y * 5, 140);
                index++;
            }
        }
        return slots;
    }

    public void update() {
        slots[0].getStack().addItems(Items.potion, 2);
        for (Slot slot : slots) {
            slot.update();
        }
    }

    public void startRendering(MasterGui master) {
        for (Slot slot : slots) {
            slot.startRendering(master);
        }
    }

    public void stopRendering(MasterGui master) {
        for (Slot slot : slots) {
            slot.stopRendering(master);
        }
    }

    public String getName() {
        return name;
    }
}
