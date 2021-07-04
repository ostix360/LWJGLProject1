package fr.ostix.game.items;

public class ItemStack {
    private final Item item;
    private int count;

    public ItemStack(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public void increaseItemCount() {
        count++;
    }

    public void decreaseItemCount() {
        count--;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }
}
