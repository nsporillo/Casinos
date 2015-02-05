package net.porillo.casino.cmds;

import java.util.List;

import net.porillo.casino.Casinos;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

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
		this.plugin.reload();
		sender.sendMessage(ChatColor.GREEN + "[MilkyCasino] Reloaded Casinos!");
	}

}
