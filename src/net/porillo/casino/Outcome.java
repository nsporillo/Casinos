package net.porillo.casino;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class Outcome {

    private ItemStack[] o = new ItemStack[3];

    private int l;
    private Casinos cas;
    private Prize p;

    public Outcome(Casinos cas) {
        this.cas = cas;
        this.cas.random();
        l = cas.getItems().length - 1;
        o[0] = cas.get(l);
        o[1] = cas.get(l);
        o[2] = cas.get(l);
        p = new Prize(this);
    }

    public ItemStack get(int x) {
        return o[x];
    }

    public ItemStack[] getStack() {
        return o;
    }

    public ItemStack getRandom() {
        return cas.get(l);
    }

    public GameResult getResult() {
        return p.getResult();
    }

    private String a(ItemStack i) {
        return ChatColor.BLUE + i.getType().toString().toLowerCase() + ChatColor.GOLD;
    }

    @Override
    public String toString() {
        return "Outcome [" + a(o[0]) + ", " + a(o[1]) + ", " + a(o[2]) + "]";
    }
}
