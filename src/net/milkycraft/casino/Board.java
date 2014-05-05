package net.milkycraft.casino;

import static org.bukkit.ChatColor.BLACK;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.WHITE;
import static org.bukkit.ChatColor.YELLOW;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import net.milkycraft.casino.stats.Metric;

public class Board {

	public static String[] getLines(Metric m,
			LinkedHashMap<String, Integer> base) {
		List<String> e = new ArrayList<String>();
		e.add(WHITE + "MilkyCraft Casino");
		switch (m) {
		case MOSTLUCKY:
			e.add(YELLOW + "Most lucky");
			break;
		case MOSTPLAYS:
			e.add(YELLOW + "Most plays");
			break;
		case MOSTSPENT:
			e.add(YELLOW + "Most spent");
			break;
		case MOSTWINS:
			e.add(YELLOW + "Most wins");
			break;
		}
		int i = 0;
		for (Entry<String, Integer> b : base.entrySet()) {
			i++;
			if (i > 10) {
				break;
			}
			e.add(BLACK + "[" + WHITE + i + BLACK + "] " + GREEN + b.getKey()
					+ YELLOW + "--" + GOLD + b.getValue());
		}
		return (String[]) e.toArray(new String[e.size()]);
	}
}
