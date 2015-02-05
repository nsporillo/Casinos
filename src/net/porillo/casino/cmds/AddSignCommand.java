package net.porillo.casino.cmds;

import static org.bukkit.ChatColor.RED;

import java.util.List;

import net.porillo.casino.Casino;
import net.porillo.casino.Casinos;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AddSignCommand extends BaseCommand {

	public AddSignCommand(Casinos plugin) {
		super(plugin);
		super.setName("addsign");
		super.addUsage("[name]", null, "Adds a sign to the machine");
		super.setPermission("casinos.addsign");
	}

	@SuppressWarnings("deprecation")
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
			String name = args.get(0);
			for (Casino c : plugin.getCasinos()) {
				if (c.getName().equalsIgnoreCase(name)) {
					Player p = (Player) sender;
					Block b = p.getLastTwoTargetBlocks(null, 5).get(1);
					if (b.getState() instanceof Sign) {
						c.setSign((Sign) b.getState());
						sender.sendMessage(RED + "Added a sign to " + name);
					} else {
						sender.sendMessage(RED + "Look at a sign");
					}
				}
			}
		}
	}
}
