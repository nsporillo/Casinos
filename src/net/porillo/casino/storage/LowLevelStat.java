package net.porillo.casino.storage;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import net.porillo.casino.Casinos;

public class LowLevelStat extends ConfigLoader {

	private LinkedHashMap<String, Integer> val = new LinkedHashMap<String, Integer>();

	public LowLevelStat(Casinos plugin, String fileName) {
		super(plugin, fileName);
		super.saveIfNotExist();
		super.load();
	}

	public LowLevelStat(Casinos plugin, String dir, String fileName) {
		super(plugin, dir, fileName);
		super.saveIfNotExist();
		super.load();
	}

	@Override
	protected void loadKeys() {
		for (String str : config.getKeys(true)) {
			int p = config.getInt(str);
			val.put(str, p);
		}
	}

	public void save() {
		for (Entry<String, Integer> store : val.entrySet()) {
			config.set(store.getKey(), store.getValue());
		}
		super.saveConfig();
	}

	public void set(String key, Object value) {
		config.set(key, value);
		super.saveConfig();
	}

	public void reload() {
		super.rereadFromDisk();
		super.load();
	}

	public LinkedHashMap<String, Integer> getVal() {
		return val;
	}

	public void setVal(LinkedHashMap<String, Integer> val) {
		this.val = val;
	}

}