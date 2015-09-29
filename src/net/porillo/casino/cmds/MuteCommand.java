package net.porillo.casino.cmds;

import net.porillo.casino.Casinos;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

import static org.bukkit.ChatColor.RED;

public class MuteCommand extends BaseCommand {

    public MuteCommand(Casinos plugin) {
        super(plugin);
        super.setName("mute");
        super.addUsage(null, null, "Toggles sound effects from casinos");
        super.setPermission("casinos.mute");
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
        if (Casinos.getMuted().contains(sender.getName())) {
            Casinos.getMuted().remove(sender.getName());
            sender.sendMessage(ChatColor.GREEN + "Casino sounds are enabled");
        } else {
            Casinos.getMuted().add(sender.getName());
            sender.sendMessage(ChatColor.GREEN + "Casino sounds are disabled");
        }
    }

}
