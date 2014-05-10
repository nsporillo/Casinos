package net.milkycraft.casino;

import static org.bukkit.Bukkit.getPluginManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkycraft.casino.cmds.CommandHandler;
import net.milkycraft.casino.stats.Metric;
import net.milkycraft.casino.storage.Config;
import net.milkycraft.casino.storage.LowLevelStat;
import net.milkycraft.casino.storage.Serializer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Casinos extends JavaPlugin {

	private CommandHandler commands;
	private ItemStack[] stacks;
	private List<Casino> casinos;
	protected static List<String> muteList;
	public Config config;
	private Random r = new Random();
	private RegisteredServiceProvider<Economy> econ;
	private static StatisticManager manager;

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		commands = new CommandHandler(this);
		casinos = new CopyOnWriteArrayList<Casino>();
		muteList = new ArrayList<String>();
		config = new Config(this, "config.yml");
		stacks = new ItemStack[config.total];	
		getPluginManager().registerEvents(new CasinoListener(this), this);
		this.loadOdds();
		casinos = Serializer.load();
		manager = new StatisticManager(this);
		manager.load();
		for (Casino c : casinos) {
			c.setHandle(this);
			c.update();
		}
		econ = Bukkit.getServicesManager().getRegistration(Economy.class);
	}

	@Override
	public void onDisable() {
		Casinos.manager.save();
		Serializer.save(casinos);
		getServer().getScheduler().cancelTasks(this);
	}

	public void reload() {
		// Disable methods
		Bukkit.getScheduler().cancelTasks(this);
		Casinos.manager.save();
		Serializer.save(casinos);
		Casinos.muteList.clear();		
		// Enable methods
		this.commands = new CommandHandler(this);
		this.config = new Config(this, "config.yml");
		this.stacks = new ItemStack[config.total];
		getPluginManager().registerEvents(new CasinoListener(this), this);
		this.loadOdds();
		this.casinos = Serializer.load();
		Casinos.manager.load();
		for (Casino c : casinos) {
			c.setHandle(this);
			c.update();
		}
		this.econ = Bukkit.getServicesManager().getRegistration(Economy.class);
	}

	public static void info(String info) {
		Bukkit.getLogger().info("[Casinos] " + info);
	}

	public EconomyResponse charge(String player, double cost) {
		return econ.getProvider().withdrawPlayer(player, cost);
	}

	public EconomyResponse pay(String player, double cost) {
		return econ.getProvider().depositPlayer(player, cost);
	}

	private void loadOdds() {
		Map<Integer, Integer> odds = config.odds;
		for (Entry<Integer, Integer> i : odds.entrySet()) {
			put(i.getKey(), i.getValue());
		}
		Collections.shuffle(Arrays.asList(stacks));
	}

	@SuppressWarnings("deprecation")
	private void put(int x, int y) {
		ItemStack is = new ItemStack(x);
		int z = getLeft();
		int zz = y + z;
		for (int i = z; i < zz; i++) {
			stacks[i] = is;
		}
	}

	private int getLeft() {
		int last = 0;
		for (int x = 0; x < stacks.length; x++) {
			if (!(stacks[x] == null)) {
				last++;
			}
		}
		return last;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		this.commands.runCommand(s, l, a);
		return true;
	}

	public static List<String> getMuted() {
		return muteList;
	}

	public List<Casino> getCasinos() {
		return casinos;
	}

	public ItemStack[] getItems() {
		return stacks;
	}

	public void random() {
		long l = r.nextLong();
		this.r = new Random(l);
	}

	public LowLevelStat getStat(Metric m) {
		switch (m) {
			case MOSTLUCKY:
				return Casinos.manager.luck;
			case MOSTPLAYS:
				return Casinos.manager.plays;
			case MOSTSPENT:
				return Casinos.manager.spent;
			case MOSTWINS:
				return Casinos.manager.wins;
			default:
		}
		return null;
	}

	public ItemStack get(int i) {
		return stacks[r.nextInt(i)];
	}

	public static StatisticManager getManager() {
		return manager;
	}
}
