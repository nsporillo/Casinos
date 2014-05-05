package net.milkycraft.casino.cmds;

import static org.bukkit.ChatColor.RED;

import java.util.List;

import net.milkycraft.casino.Casinos;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class ReloadCommand extends BaseCommand {

	public ReloadCommand(Casinos plugin) {
		super(plugin);
		super.setName("reload");
		super.addUsage(null, null, "Reloads the plugin");
		super.setPermission("casinos.reload");
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
		plugin.onDisable();
		plugin.onEnable();
		sender.sendMessage(ChatColor.GREEN + "[MilkyCasino] Reloaded Casinos!");
	}

}
