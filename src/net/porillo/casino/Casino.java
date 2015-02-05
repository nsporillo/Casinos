package net.porillo.casino;

import static net.porillo.casino.CasinoUtility.play;
import static net.porillo.casino.CasinoUtility.reward;
import static net.porillo.casino.CasinoUtility.rotate;

import java.io.Serializable;
import java.util.List;

import net.porillo.casino.stats.Stat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Rotation;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;

public class Casino implements Serializable {

	private static final long serialVersionUID = 1191579718645168727L;
	private transient Casinos plugin;
	private Button button;
	private CasinoSign sign;
	private List<Slot> slots;
	private String name;
	private String owner;
	private boolean running;
	private int cost;

	public Casino(Casinos plugin, Block b, String name, int cost) {
		this.plugin = plugin;
		this.name = name;
		this.cost = cost;
		this.running = false;
		this.owner = "";
		this.button = new Button(b);
	}

	public void update() {
		this.running = false;
		this.getClicker().setMetadata("slot", new FixedMetadataValue(plugin, "slot"));
		button.update();
		sign.update();
	}

	public void spin(final Player p) {
		if (running) {
			return;
		}
		running = true;
		SignHandler.start(getSign(), name);
		for (Slot s : slots) {
			s.update();
		}
		final Outcome out = new Outcome(plugin);
		final BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			@Override
			public void run() {
				for (Slot s : slots) {
					s.setImage(out.getRandom());
				}
				play(p, 6, 1, Sound.ORB_PICKUP, 0.4F, 0);
			}
		}, 0L, 5L);
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				task.cancel();
				for (int i = 0; i < slots.size(); i++) {
					slots.get(i).setImage(out.get(i));
				}
				finish(out.getResult(), p);
			}
		}, 60L);
	}

	void finish(final GameResult g, final Player p) {
		final Sign s = getSign();
		if (g.getCount() == Count.LOST) {
			SignHandler.lose(s);
		} else {
			SignHandler.win(s, g);
		}
		p.sendMessage(ChatColor.GREEN + "[Casino] " + ChatColor.GOLD + g.getOutcome().toString());
		reward(p, g);
		final BukkitTask task1 = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			@Override
			public void run() {
				if (!running || g.getCount() == Count.LOST) {
					return;
				}
				for (Slot s : slots) {
					if (s.getFrame().getItem().getType() == g.getItem().getType()) {
						rotate(s.getFrame());
					}
				}
			}
		}, 0L, 5L);
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				task1.cancel();
				if (!running) {
					return;
				}
				for (Slot s : slots) {
					s.getFrame().setRotation(Rotation.NONE);
				}
				SignHandler.update(s, cost, owner);
				Casinos.getManager().processStat(
						new Stat(g, p.getName(), name, cost, System.currentTimeMillis()));
				play(p, 10, 1, Sound.ANVIL_LAND, 2, 1);
				running = false;
			}
		}, 60L);
	}

	public void setSlots(List<Slot> slots) {
		this.slots = slots;
	}

	public void setHandle(Casinos cas) {
		this.plugin = cas;
	}

	public void setSign(Sign s) {
		this.sign = new CasinoSign(s);
	}

	public Sign getSign() {
		sign.update();
		return sign.getSign();
	}

	public Block getClicker() {
		button.update();
		return button.getBlock();
	}

	public String getName() {
		return name;
	}

	public String getOwner() {
		return owner;
	}

	public boolean hasOwner() {
		return !owner.equals("") && owner.length() > 2;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getCost() {
		return cost;
	}

	public List<Slot> getSlots() {
		return slots;
	}

	public boolean isRunning() {
		return running;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public Casinos getHandle() {
		return plugin;
	}
}
