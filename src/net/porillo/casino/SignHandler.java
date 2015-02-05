package net.porillo.casino;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;

public class SignHandler {

	//TODO: Make sign settings configurable?
	
	public static void start(Sign s, String name) {
		setSign(s, 0, ChatColor.BLUE + name);
		setSign(s, 1, ChatColor.DARK_BLUE + "Rolling!");
	}

	public static void win(Sign s, GameResult g) {
		setSign(s, 1, ChatColor.YELLOW + "Congrats!");
		setSign(s, 2, ChatColor.GREEN + "You won");
		setSign(s, 3, ChatColor.GOLD + "" + (g.getCount().ordinal() + 1) + " "
				+ g.getItem().getType().toString().toLowerCase());
	}

	public static void lose(Sign s) {
		setSign(s, 1, ChatColor.YELLOW + "Cooling down");
		setSign(s, 2, ChatColor.RED + "You lost!");
		setSign(s, 3, ChatColor.GOLD + "Try again :)");
	}

	public static void update(Sign s, int cost, String owner) {
		setSign(s, 1, ChatColor.GREEN + "Ready");
		setSign(s, 2, ChatColor.WHITE + "Costs $" + cost);
		setSign(s, 3, ChatColor.AQUA + owner);
	}

	public static void setSign(Sign s, int x, String y) {
		s.setLine(x, y);
		s.update();
	}
}
