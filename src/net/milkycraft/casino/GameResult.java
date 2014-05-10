package net.milkycraft.casino;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GameResult {

	private ItemStack is;
	private Count c;
	private Outcome out;

	public GameResult(Count c, ItemStack is, Outcome out) {
		this.c = c;
		this.is = is;
		this.out = out;
	}

	public GameResult(Count c) {
		this.c = c;
		this.is = new ItemStack(Material.AIR);
		this.out = null;
	}

	public boolean isWinner() {
		return c == Count.TWO || c == Count.THREE;
	}

	public ItemStack getItem() {
		return is;
	}

	public Outcome getOutcome() {
		return out;
	}

	public Count getCount() {
		return c;
	}

	@Override
	public String toString() {
		return c.toString() + " - " + is.getType().toString();
	}
}
