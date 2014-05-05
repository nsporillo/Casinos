package net.milkycraft.casino.cmds;

import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.RED;

import java.util.List;

import net.milkycraft.casino.Casinos;
import net.milkycraft.casino.Count;
import net.milkycraft.casino.GameResult;
import net.milkycraft.casino.Outcome;

import org.bukkit.command.CommandSender;

public class BenchCommand extends BaseCommand {

	public BenchCommand(Casinos plugin) {
		super(plugin);
		super.setName("bench");
		super.addUsage("[number]", null, "Benchmarks the odds");
		super.setPermission("casinos.addsign");
	}

	@Override
	public void runCommand(CommandSender sender, List<String> args) {
		if (!this.checkPermission(sender)) {
			this.noPermission(sender);
			return;
		}

		if (args.size() == 1) {
			String num = args.get(0);
			Integer x = Integer.parseInt(num);
			int lost = 0;
			int two = 0;
			int three = 0;
			for (int i = 0; i < x; i++) {
				Outcome out = new Outcome(plugin);
				GameResult gr = out.getResult();
				if (gr.getCount() == Count.LOST) {
					lost++;
				} else if (gr.getCount() == Count.TWO) {
					two++;
				} else if (gr.getCount() == Count.THREE) {
					three++;
				}
			}
			sender.sendMessage(RED + "Lost: " + lost + " of " + x);
			sender.sendMessage(RED + "Two: " + two + " of " + x);
			sender.sendMessage(RED + "Three: " + three + " of " + x);
			sender.sendMessage(GOLD + "Won: " + (two + three) + " of " + x);
		}
	}
}
