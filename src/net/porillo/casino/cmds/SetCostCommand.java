package net.porillo.casino.cmds;

import net.porillo.casino.Casino;
import net.porillo.casino.Casinos;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

import static org.bukkit.ChatColor.RED;

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
                String cas = args.get(0);
                if (c.getName().equalsIgnoreCase(cas)) {
                    if (c.getOwner().equals(sender.getName()) || sender.isOp()) {
                        //TODO: Define configurable range of pricing
                        //Old: {p|p >= 100 && p <= 500}
                        int a = Integer.valueOf(args.get(1));
                        c.setCost(a);
                        sender.sendMessage(RED + "Set the cost of" + cas + " to " + a);
                    } else {
                        sender.sendMessage(RED + "You don't own that machine");
                    }
                }
            }
        }
    }

}
