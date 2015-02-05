package net.porillo.casino;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;

public class Slot implements Serializable {

	private static final long serialVersionUID = -7932992488491607073L;
	private Casino parent;
	private transient ItemFrame frame;
	private final SerialLocation loc;

	public Slot(Casino p, ItemFrame frame) {
		this.parent = p;
		this.frame = frame;
		Location l = frame.getLocation();
		this.loc = new SerialLocation(l.getWorld().getName(), l.getBlockX(), l.getBlockY(),
				l.getBlockZ());
	}

	public Casino getParent() {
		return this.parent;
	}

	public ItemFrame getFrame() {
		return this.frame;
	}

	public void setImage(ItemStack g) {
		if (this.frame != null) {
			this.frame.setItem(g);
		}
	}

	public SerialLocation getLocation() {
		return this.loc;
	}

	public void update() {
		if (this.frame != null) {
			return;
		}
		World w = Bukkit.getWorld(loc.getWorld());
		for (Entity e : w.getEntities()) {
			if (e instanceof ItemFrame) {
				if(CasinoUtility.matches(e.getLocation(), loc)) {
					this.frame = (ItemFrame) e;
				}
			}
		}
	}
}
