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
        int size = 140;
        for (int x = 0; x < slotCount / 5; x++) {
            for (int y = 0; y < slotCount / 7; y++) {
                slots[index++] = new Slot(495 + x * size + x * 48, 240 + y * size + y * 17, size);
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

    public void render() {
        for (Slot slot : slots) {
            slot.render();
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
