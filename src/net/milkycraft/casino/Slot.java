package net.milkycraft.casino;

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
		return parent;
	}

	public ItemFrame getFrame() {
		return frame;
	}

	public void setImage(ItemStack g) {
		if (frame != null) {
			frame.setItem(g);
		}
	}

	public SerialLocation getLocation() {
		return loc;
	}

	public void update() {
		if (frame != null) {
			return;
		}
		World w = Bukkit.getWorld(loc.getWorld());
		for (Entity e : w.getEntities()) {
			if (e instanceof ItemFrame) {
				Location l = e.getLocation();
				if (l.getBlockX() == loc.x) {
					if (l.getBlockY() == loc.y) {
						if (l.getBlockZ() == loc.z) {
							frame = (ItemFrame) e;
						}
					}
				}
			}
		}
	}
}
