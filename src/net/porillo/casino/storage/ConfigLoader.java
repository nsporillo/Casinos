package net.porillo.casino.storage;

import net.porillo.casino.Casinos;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;

public abstract class ConfigLoader {

    protected String fileName;
    protected File configFile;
    protected File dataFolder;
    protected Casinos plugin;
    protected FileConfiguration config;

    protected ConfigLoader(Casinos plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.dataFolder = plugin.getDataFolder();
        this.configFile = new File(this.dataFolder, File.separator + fileName);
        config = loadConfiguration(this.configFile);
    }

    protected ConfigLoader(Casinos plugin, String directory, String name) {
        this.plugin = plugin;
        this.fileName = name;
        this.dataFolder = new File(plugin.getDataFolder(), File.separator + directory);
        this.configFile = new File(this.dataFolder, File.separator + fileName);
        config = loadConfiguration(this.configFile);
    }

    public void load() {
        if (!this.configFile.exists()) {
            this.dataFolder.mkdir();
            saveConfig();
        }
        addDefaults();
        loadKeys();
        this.saveIfNotExist();
    }

    protected void saveConfig() {
        try {
            config.save(this.configFile);
        } catch (IOException ex) {
        }
    }

    public void saveIfNotExist() {
        if (!this.configFile.exists()) {
            if (this.plugin.getResource(this.fileName) != null) {
                this.plugin.saveResource(this.fileName, false);
            }
        }
        rereadFromDisk();
    }

    protected void rereadFromDisk() {
        config = loadConfiguration(this.configFile);
    }

    protected FileConfiguration getYaml() {
        return this.config;
    }

    protected void addDefaults() {
        config.options().copyDefaults(true);
        saveConfig();
    }

    protected abstract void loadKeys();

}
