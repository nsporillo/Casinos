package net.milkycraft.casino.storage;

import static org.bukkit.Material.valueOf;

import java.util.HashMap;
import java.util.Map;

import net.milkycraft.casino.Casinos;

import org.bukkit.configuration.ConfigurationSection;

public class Config extends ConfigLoader {

	public int total;
	public Map<Integer, Integer> odds = new HashMap<Integer, Integer>();

	public Config(Casinos plugin, String fileName) {
		super(plugin, fileName);
		super.saveIfNotExist();
		super.load();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void loadKeys() {
		ConfigurationSection groups = config.getConfigurationSection("odds");
		if (groups == null) {
			config.createSection("odds");
			super.saveConfig();
			return;
		}
		for (String keys : groups.getKeys(false)) {
			ConfigurationSection vars = groups.getConfigurationSection(keys);
			if (vars == null) {
				vars = groups.createSection(keys);
				super.saveConfig();
			}
			if (keys.equals("total")) {
				total = vars.getInt("total");
				continue;
			}
			odds.put(valueOf(keys.toUpperCase()).getId(), vars.getInt("count"));
		}
	}
}
