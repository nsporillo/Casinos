package net.porillo.casino.cmds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.porillo.casino.Casinos;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandHandler {

	private final Map<String, Command> commands = new HashMap<String, Command>();

	public CommandHandler(final Casinos plugin) {
		commands.put("create", new CreateCommand(plugin));
		commands.put("mute", new MuteCommand(plugin));
		commands.put("addsign", new AddSignCommand(plugin));
		commands.put("delete", new DeleteCommand(plugin));
		commands.put("reload", new ReloadCommand(plugin));
		commands.put("setowner", new OwnerCommand(plugin));
		commands.put("setcost", new SetCostCommand(plugin));
		commands.put("bench", new BenchCommand(plugin));
	}

	public void runCommand(CommandSender s, String label, String[] args) {
		if (args.length == 0 || this.commands.get(args[0].toLowerCase()) == null) {
			s.sendMessage(ChatColor.GREEN + "===" + ChatColor.GOLD + " Casinos Help "
					+ ChatColor.GREEN + "===");
			for (Command cmd : this.commands.values()) {
				if (cmd.checkPermission(s)) {
					cmd.showHelp(s, label);
				}
			}
			return;
		}
		List<String> arguments = new ArrayList<String>(Arrays.asList(args));
		final Command cmd = this.commands.get(arguments.remove(0).toLowerCase());
		if (arguments.size() < cmd.getRequiredArgs()) {
			cmd.showHelp(s, label);
			return;
		}
		cmd.runCommand(s, arguments);
	}

}