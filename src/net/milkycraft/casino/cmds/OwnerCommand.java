package net.milkycraft.casino.cmds;

import static org.bukkit.ChatColor.RED;

import java.util.List;

import net.milkycraft.casino.Casino;
import net.milkycraft.casino.Casinos;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class OwnerCommand extends BaseCommand {

	public OwnerCommand(Casinos plugin) {
		super(plugin);
		super.setName("setowner");
		super.setPermission("casinos.setowner");
		super.addUsage("[casino]", "[owner]", "Sets the owner of the casino");
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
			sender.sendMessage(RED + "You must specify an owner");
		} else if (args.size() == 2) {
			for (Casino c : plugin.getCasinos()) {
				if (c.getName().equalsIgnoreCase(args.get(0))) {
					c.setOwner(args.get(1));
				}
			}
			sender.sendMessage(RED + "Set the owner of" + args.get(0) + " to " + args.get(1));
		}
	}
}
