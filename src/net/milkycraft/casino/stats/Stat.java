package net.milkycraft.casino.stats;

import net.milkycraft.casino.GameResult;

public class Stat {

	private GameResult result;
	private long date;
	private String player;
	private String casino;
	private int cost;

	public Stat(GameResult result, String player, String casino, int cost,
			long date) {
		this.result = result;
		this.player = player;
		this.cost = cost;
		this.date = date;
		this.casino = casino;
	}

	public GameResult getResult() {
		return result;
	}

	public String getCasino() {
		return casino;
	}

	public long getDate() {
		return date;
	}

	public String getPlayer() {
		return player;
	}

	public int getCost() {
		return cost;
	}

	@Override
	public String toString() {
		return casino + "|" + player + "|" + result + "|" + cost + "|" + date;
	}
}
