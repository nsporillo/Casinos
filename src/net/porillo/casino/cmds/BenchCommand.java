package net.porillo.casino.cmds;

import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;

import java.util.List;

import net.porillo.casino.Casinos;
import net.porillo.casino.Count;
import net.porillo.casino.GameResult;
import net.porillo.casino.Outcome;

import org.bukkit.command.CommandSender;

public class BenchCommand extends BaseCommand {

	public BenchCommand(Casinos plugin) {
		super(plugin);
		super.setName("bench");
		super.addUsage("[number]", null, "Benchmarks the odds");
		super.setPermission("casinos.addsign");
	}

	@Override
	public void runCommand(CommandSender s, List<String> args) {
		if (!this.checkPermission(s)) {
			this.noPermission(s);
			return;
		}
		if (args.size() == 1) {
			String num = args.get(0);
			Integer x = Integer.parseInt(num);
			int[] outcome = new int[3];
			for (int i = 0; i < x; i++) {
				Outcome out = new Outcome(plugin);
				GameResult gr = out.getResult();
				if (gr.getCount() == Count.LOST) {
					outcome[0]++;
				} else if (gr.getCount() == Count.TWO) {
					outcome[1]++;
				} else if (gr.getCount() == Count.THREE) {
					outcome[2]++;
				}
			}
			s.sendMessage(GREEN + "Based on " + x + " outcomes...");
			s.sendMessage(RED + "Lost: " + outcome[0] + " of " + x);
			s.sendMessage(RED + "Two: " + outcome[1] + " of " + x);
			s.sendMessage(RED + "Three: " + outcome[2] + " of " + x);
			s.sendMessage(GOLD + "Won: " + (outcome[1] + outcome[2]) + " of " + x);
		}
	}
}
