package net.milkycraft.casino;

import org.bukkit.inventory.ItemStack;

public class Prize {

	private Outcome out;

	public Prize(Outcome out) {
		this.out = out;
	}

	public ItemStack g(int x) {
		ItemStack[] o = out.getStack();
		return o[x];
	}

	public GameResult getResult() {
		if (g(0).equals(g(1)) || g(1).equals(g(2)) || g(0).equals(g(2))) {
			if (g(0).equals(g(1)) && g(1).equals(g(2))) {
				return new GameResult(Count.THREE, g(0), out);
			}
			if (g(0).equals(g(1))) {
				return new GameResult(Count.TWO, g(0), out);
			} else if (g(1).equals(g(2))) {
				return new GameResult(Count.TWO, g(1), out);
			} else if (g(0).equals(g(2))) {
				return new GameResult(Count.TWO, g(0), out);
			}
		}
		return new GameResult(Count.LOST, new ItemStack(0), out);
	}
}
