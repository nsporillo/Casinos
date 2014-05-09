package net.milkycraft.casino;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static net.milkycraft.casino.CasinoUtility.matches;

public class CasinoListener implements Listener {

	private Casinos cas;

	public CasinoListener(Casinos cas) {
		this.cas = cas;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (b.getType() == Material.STONE_BUTTON) {
				if (b.hasMetadata("slot")) {
					for (Casino c : cas.getCasinos()) {
						if (isClicker(b, c.getClicker())) {
							play(c, p);
							return;
						}
					}
				}
			}
		}
	}

	private boolean isClicker(Block a, Block b) {
		Location x = a.getLocation();
		Location y = b.getLocation();
		if (x.getWorld().getName().equals(y.getWorld().getName())) {
			if (x.getBlockX() == y.getBlockX()) {
				if (x.getBlockY() == y.getBlockY()) {
					if (x.getBlockZ() == y.getBlockZ()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void play(Casino c, Player p) {
		if (!c.isRunning()) {
			if (cas.charge(p.getName(), c.getCost()).transactionSuccess()) {
				c.spin(p);
				if (c.hasOwner()) {
					double cost = Math.ceil((c.getCost() * (2.5 / 7)));
					cas.pay(c.getOwner(), cost);
				}
			} else {
				p.sendMessage(ChatColor.RED + "Your broke fool! Get some money!");
			}
		} else {
			p.playSound(p.getLocation(), Sound.IRONGOLEM_DEATH, 1F, 0);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onClick(PlayerInteractEntityEvent e) {
		if (e.getRightClicked().getType() == EntityType.ITEM_FRAME) {
			for (Casino c : cas.getCasinos()) {
				for (Slot s : c.getSlots()) {
					if (s.getFrame() == null) {
						continue;
					}
					if (matches(e.getRightClicked().getLocation(), s.getLocation())) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onHangingingBreak(HangingBreakByEntityEvent event) {
		Hanging h = event.getEntity();
		if (h instanceof ItemFrame) {
			for (Casino c : cas.getCasinos()) {
				for (Slot s : c.getSlots()) {
					if (matches(h.getLocation(), s.getLocation())) {
						if (event.getRemover() instanceof Player) {
							Player p = (Player) event.getRemover();
							if (!p.isOp()) {
								event.setCancelled(true);
							}
						} else {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onHangingIntereact(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Hanging) {
			Hanging h = (Hanging) e.getEntity();
			if (h instanceof ItemFrame) {
				for (Casino c : cas.getCasinos()) {
					for (Slot s : c.getSlots()) {
						if (matches(h.getLocation(), s.getLocation())) {
							if (e.getDamager() instanceof Player) {
								Player p = (Player) e.getDamager();
								if (!p.isOp()) {
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}
}
