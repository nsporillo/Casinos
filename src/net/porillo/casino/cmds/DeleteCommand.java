package net.porillo.casino.cmds;

import static org.bukkit.ChatColor.RED;

import java.util.List;

import net.porillo.casino.Casino;
import net.porillo.casino.Casinos;
import net.porillo.casino.storage.Serializer;

import org.bukkit.command.CommandSender;

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
