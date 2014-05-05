package net.milkycraft.casino.cmds;

import static org.bukkit.ChatColor.RED;

import java.util.List;

import net.milkycraft.casino.Casino;
import net.milkycraft.casino.Casinos;
import net.milkycraft.casino.storage.Serializer;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class DeleteCommand extends BaseCommand {

	public DeleteCommand(Casinos plugin) {
		super(plugin);
		super.setName("delete");
		super.addUsage("[name]", null, "Deletes a casino");
		super.setPermission("casinos.delete");
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
		if (args.size() == 1) {
			String cc = args.get(0);
			Casino c2 = null;
			for (Casino c : plugin.getCasinos()) {
				if (c.getName().equalsIgnoreCase(cc)) {
					Serializer.delete(c);
					c2 = c;
					sender.sendMessage(RED + "Removed casino " + cc);
				}
			}
			plugin.getCasinos().remove(c2);
		}
	}

}
