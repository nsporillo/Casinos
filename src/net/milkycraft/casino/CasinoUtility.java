package net.milkycraft.casino;

import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Rotation;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CasinoUtility {

	private static String pre = GREEN + "[Casino] ";
	private static String lost = pre + RED + "Sorry but you lost, better luck next time";
	private static String two = pre + GOLD + "Congrats, you won 3 %as";
	private static String three = pre + GOLD + "Congrats, you won 5 %as";

	public static void reward(Player p, GameResult result) {
		ItemStack s = result.getItem();
		String a = s.getType().toString().toLowerCase();
		Inventory i = p.getInventory();
		switch (result.getCount()) {
			case LOST:
				p.sendMessage(lost);
				play(p, 20, 1, Sound.IRONGOLEM_HIT, 5, 1);
				break;
			case THREE:
				s.setAmount(5);
				p.sendMessage(three.replace("%a", a));
				play(p, 30, 10, Sound.LEVEL_UP, 3, 5);
				i.addItem(s);
				break;
			case TWO:
				s.setAmount(3);
				p.sendMessage(two.replace("%a", a));
				play(p, 30, 5, Sound.LEVEL_UP, 1.5F, 5);
				i.addItem(s);
				break;
		}
	}

	public static void play(Player p, int r, int i, Sound s, float x, float y) {
		List<Entity> ens = p.getNearbyEntities(r, r, r);
		List<Player> players = new ArrayList<Player>();
		if (!Casinos.getMuted().contains(p.getName())) {
			players.add(p);
		}
		for (Entity en : ens) {
			if (en instanceof Player) {
				Player pla = (Player) en;
				if (!Casinos.getMuted().contains(pla.getName())) {
					players.add((Player) en);
				}
			}
		}
		for (int b = 0; b < i; b++) {
			for (Player v : players) {
				v.playSound(p.getLocation(), s, x, y);
			}
		}
	}

	public static void rotate(ItemFrame i) {
		Rotation r = i.getRotation();
		switch (r) {
			case CLOCKWISE:
				i.setRotation(Rotation.FLIPPED);
				break;
			case COUNTER_CLOCKWISE:
				i.setRotation(Rotation.NONE);
				break;
			case FLIPPED:
				i.setRotation(Rotation.COUNTER_CLOCKWISE);
				break;
			case NONE:
				i.setRotation(Rotation.CLOCKWISE);
				break;
		}
	}
}
