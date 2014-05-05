package net.milkycraft.casino;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class CasinoSign implements Serializable {

	private static final long serialVersionUID = 5784292428146367539L;
	private transient Sign s;
	private final SerialLocation loc;

	public CasinoSign(Sign s) {
		this.s = s;
		this.loc = new SerialLocation(s.getWorld().getName(), s.getX(),
				s.getY(), s.getZ());
	}

	public void write(String line, int num) {
		this.s.setLine(num, line);
		s.update();
	}

	public Sign getSign() {
		return s;
	}

	public void update() {
		try {
			World w = Bukkit.getWorld(loc.world);
			Block b = w.getBlockAt(loc.x, loc.y, loc.z);
			s = (Sign) b.getState();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			s = null;
		}
	}
}
