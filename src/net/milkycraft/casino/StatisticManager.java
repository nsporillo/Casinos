package net.milkycraft.casino;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import net.milkycraft.casino.stats.Stat;
import net.milkycraft.casino.storage.LowLevelStat;

public class StatisticManager {

	private Casinos main;
	private List<Stat> statcache = new CopyOnWriteArrayList<Stat>();
	public LowLevelStat plays;
	public LowLevelStat wins;
	public LowLevelStat spent;
	public LowLevelStat luck;

	public StatisticManager(Casinos plugin) {
		this.main = plugin;
	}

	public void load() {
		plays = new LowLevelStat(main, "stats", "plays.yml");
		wins = new LowLevelStat(main, "stats", "wins.yml");
		spent = new LowLevelStat(main, "stats", "spent.yml");
		luck = new LowLevelStat(main, "stats", "luck.yml");
		this.sort();
		this._save();
	}

	public void clearCache() {
		for (Stat st : statcache) {
			this.p(st);
		}
		statcache.clear();
	}

	public void processStat(Stat s) {
		statcache.add(s);
		if (statcache.size() <= 25) {
			return;
		} else {
			this.clearCache();
			this.sort();
		}
	}

	public void report() {
		Logger l = main.getLogger();
		l.info("Plays: " + plays.getVal().size());
		l.info("Wins: " + wins.getVal().size());
		l.info("Spent: " + spent.getVal().size());
		l.info("Luck: " + luck.getVal().size());
	}

	public void save() {
		try {
			this.clearCache();
			this.sort();
		} catch (Exception ex) {
			ex.printStackTrace();
			main.getLogger().severe("Sorting stats before save failed!");
		}
		_save();
	}

	private void _save() {
		plays.save();
		wins.save();
		spent.save();
		luck.save();
	}

	private void p(Stat s) {
		LinkedHashMap<String, Integer> total_plays = plays.getVal();
		if (total_plays.containsKey(s.getPlayer())) {
			int plays = total_plays.get(s.getPlayer());
			total_plays.remove(s.getPlayer());
			total_plays.put(s.getPlayer(), plays + 1);
		} else {
			total_plays.put(s.getPlayer(), 1);
		}
		LinkedHashMap<String, Integer> total_spent = spent.getVal();
		if (total_spent.containsKey(s.getPlayer())) {
			int total = total_spent.get(s.getPlayer());
			total_spent.remove(s.getPlayer());
			total_spent.put(s.getPlayer(), total + s.getCost());
		} else {
			total_spent.put(s.getPlayer(), s.getCost());
		}
		LinkedHashMap<String, Integer> total_wins = wins.getVal();
		boolean winner = s.getResult().isWinner();
		if (winner) {
			if (total_wins.containsKey(s.getPlayer())) {
				int wins = total_wins.get(s.getPlayer());
				total_wins.remove(s.getPlayer());
				total_wins.put(s.getPlayer(), wins + 1);
			} else {
				total_wins.put(s.getPlayer(), 1);
			}
		} else {
			if (!total_wins.containsKey(s.getPlayer())) {
				total_wins.put(s.getPlayer(), 0);
			}
		}
		LinkedHashMap<String, Integer> l = luck.getVal();
		for (String st : total_plays.keySet()) {
			if (l.containsKey(st)) {
				l.remove(st);
			}
			int wins = 0;
			if (total_wins.containsKey(st)) {
				wins = total_wins.get(st);
			}
			int plays = 0;
			if (total_plays.containsKey(st)) {
				plays = total_plays.get(st);
			}
			if (wins == 0 || plays == 0) {
				l.put(st, 0);
				continue;
			}
			double pwin = (double) wins / (double) plays;
			int ll = (int) (pwin * 100);
			l.put(st, ll);
		}
	}

	public void sort() {
		LinkedList<Entry<String, Integer>> e = doSort(plays.getVal());
		LinkedList<Entry<String, Integer>> f = doSort(wins.getVal());
		LinkedList<Entry<String, Integer>> g = doSort(spent.getVal());
		LinkedList<Entry<String, Integer>> h = doSort(luck.getVal());
		plays.getVal().clear();
		for (Map.Entry<String, Integer> en : e) {
			plays.getVal().put(en.getKey(), en.getValue());
		}
		wins.getVal().clear();
		for (Map.Entry<String, Integer> en : f) {
			wins.getVal().put(en.getKey(), en.getValue());
		}
		spent.getVal().clear();
		for (Map.Entry<String, Integer> en : g) {
			spent.getVal().put(en.getKey(), en.getValue());
		}
		luck.getVal().clear();
		for (Map.Entry<String, Integer> en : h) {
			luck.getVal().put(en.getKey(), en.getValue());
		}
	}

	public LinkedList<Map.Entry<String, Integer>> doSort(LinkedHashMap<String, Integer> base) {
		LinkedList<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(
				base.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		return list;
	}

	public LinkedHashMap<String, Integer> getLeaders(LinkedHashMap<String, Integer> base, int page) {
		int search = page * 10 + 10;
		if (base.size() / 10 < page) {
			page = 0;
		}
		if (search > base.size()) {
			search = base.size();
		}
		LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		String[] array = base.keySet().toArray(new String[base.size()]);

		for (int i = page * 10; i < search; i++) {
			map.put(array[i], i);
		}
		return map;
	}
}
