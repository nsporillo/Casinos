package net.porillo.casino;

import java.io.Serializable;

public class SerialLocation implements Serializable {

	private static final long serialVersionUID = -645101824628385024L;
	public String world;
	public int x;
	public int y;
	public int z;

	public SerialLocation(String world, int x, int y, int z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public String getWorld() {
		return world;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	@Override
	public String toString() {
		return "SerialLocation [world=" + world + ", x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	public String getCompressed() {
		return world + "|" + x + "|" + y + "|" + z;
	}
}
