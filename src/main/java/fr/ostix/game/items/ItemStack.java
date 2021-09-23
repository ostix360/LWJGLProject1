package fr.ostix.game.items;

import fr.ostix.game.toolBox.Logger;

public class ItemStack {
    private Item item;
    private int count;

    public ItemStack(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public void addItems(Item item, int number){
        if (this.item == null){
            this.item = item;
            this.count = number;
        }else if(this.item == item){
            this.count = number;
        }else{
            Logger.warn("you couldn't had item to an itemStack if there is not the same...");
        }
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
