package net.milkycraft.casino.cmds;

import static org.bukkit.ChatColor.RED;

import java.util.ArrayList;
import java.util.List;

import net.milkycraft.casino.Casino;
import net.milkycraft.casino.Casinos;
import net.milkycraft.casino.Slot;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class CreateCommand extends BaseCommand {

	private FixedMetadataValue fmv;

	public CreateCommand(Casinos plugin) {
		super(plugin);
		super.setName("create");
		super.addUsage("[name]", "[price]", "Creates casino ");
		super.setPermission("casinos.create");
		fmv = new FixedMetadataValue(plugin, "slot");
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
			sender.sendMessage(RED + "Specify the name of the wall");
		} else if (args.size() == 1) {
			sender.sendMessage(RED + "Specify the price of the wall");
		} else if (args.size() == 2) {
			try {
				Player p = (Player) sender;
				Block b = p.getLastTwoTargetBlocks(null, 5).get(1);
				if (b.getType().equals(Material.STONE_BUTTON)) {
					b.setMetadata("slot", fmv);
				} else {
					p.sendMessage(ChatColor.RED + "Look at a stone button");
					return;
				}
				Integer in = Integer.parseInt(args.get(1));
				String name = args.get(0);
				Casino cas = new Casino(plugin, b, name, in);
				List<Slot> slots = new ArrayList<Slot>();

				Location mid = b.getLocation().clone().add(0, 1, 0);
				Location x1 = mid.clone().subtract(1, 0, 1);
				Location x2 = mid.clone().add(1, 0, 1);
				Location[] locs = new Location[9];
				int i = 0;
				for (int x = Math.min(x1.getBlockX(), x2.getBlockX()); x <= Math.max(
						x1.getBlockX(), x2.getBlockX()); x++) {
					for (int z = Math.min(x1.getBlockZ(), x2.getBlockZ()); z <= Math.max(
							x1.getBlockZ(), x2.getBlockZ()); z++) {
						System.out.println("x: " + x + " - z: " + z);
						locs[i] = new Location(mid.getWorld(), x, mid.getBlockY(), z);
						i++;
					}
				}
				List<Entity> es = p.getNearbyEntities(10, 10, 10);
				for (Entity itf : es) {
					if (itf instanceof ItemFrame) {
						ItemFrame frame = (ItemFrame) itf;
						Location iloc = itf.getLocation();
						for (Location l : locs) {
							if (l.getBlockX() == iloc.getBlockX()) {
								if (l.getBlockY() == iloc.getBlockY()) {
									if (l.getBlockZ() == iloc.getBlockZ()) {
										Slot s = new Slot(cas, frame);
										slots.add(s);
									}
								}
							}
						}
					}
				}
				if (slots.size() > 3) {
					sender.sendMessage(RED + "Error: Slots size greater than 5");
				}
				cas.setSlots(slots);
				cas.setSign(getSign(b));
				plugin.getCasinos().add(cas);
			} catch (Exception ex) {
				ex.printStackTrace();
				sender.sendMessage(RED + "Error: " + ex.getMessage());
			}
			sender.sendMessage(RED + "Created a slot machine");
		}
	}

	private static Sign getSign(Block b) {
		for (BlockFace face : BlockFace.values()) {
			Block s = b.getRelative(face);
			if (s.getState() instanceof Sign) {
				return (Sign) s.getState();
			}
		}
		return null;
	}

}
