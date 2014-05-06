package net.milkycraft.casino;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

public class Button implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1098128556439871640L;
	private transient Block b;
	private SerialLocation loc;

	public Button(Block b) {
		this.b = b;
		loc = new SerialLocation(b.getWorld().getName(), b.getX(), b.getY(), b.getZ());
	}

	public void update() {
		b = Bukkit.getWorld(loc.world).getBlockAt(loc.x, loc.y, loc.z);
	}

	public Block getBlock() {
		return b;
	}
}
