package net.milkycraft.casino.cmds;

import static org.bukkit.ChatColor.RED;

import java.util.List;

import net.milkycraft.casino.Casino;
import net.milkycraft.casino.Casinos;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class SetCostCommand extends BaseCommand {

	public SetCostCommand(Casinos plugin) {
		super(plugin);
		super.setName("setcost");
		super.setPermission("casinos.setcost");
		super.addUsage("[casino]", "[cost]", "Sets the cost of the casino");
	}

	@Override
	public void runCommand(CommandSender sender, List<String> args) {
		if (!this.checkPermission(sender)) {
			this.noPermission(sender);
			return;
		}
		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage(RED + "Console cannot use this command");
			return;
		}
		if (args.size() == 0) {
			sender.sendMessage(RED + "You must specify an casino");
		} else if (args.size() == 1) {
			sender.sendMessage(RED + "You must specify a cost");
		} else if (args.size() == 2) {
			for (Casino c : plugin.getCasinos()) {
				if (c.getName().equalsIgnoreCase(args.get(0))) {
					if (c.getOwner().equals(sender.getName()) || sender.isOp()) {
						int a = Integer.valueOf(args.get(1));
						if (a > 99 && a <= 500) {
							c.setCost(Integer.valueOf(args.get(1)));
							sender.sendMessage(RED + "Set the cost of" + args.get(0) + " to "
									+ args.get(1));
						} else {
							sender.sendMessage(RED + "Cost must be between 100 and 500");
						}
					} else {
						sender.sendMessage(RED + "You don't own that machine");
					}
				}
			}
		}
	}

}
