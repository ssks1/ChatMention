package me.chatMention;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;

public class PlayerSettings {
    private JavaPlugin plugin;
    private File settingsFile;
    private FileConfiguration config;

    public PlayerSettings(JavaPlugin plugin) {
        this.plugin = plugin;
        this.settingsFile = new File(plugin.getDataFolder(), "player-settings.yml");
        loadConfig();
    }

    private void loadConfig() {
        if (!settingsFile.exists()) {
            plugin.getDataFolder().mkdirs();
            try {
                settingsFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(settingsFile);
    }

    public void setSetting(UUID playerUUID, String key, Object value) {
        config.set("players." + playerUUID + "." + key, value);
        saveConfig();
    }

    public Object getSetting(UUID playerUUID, String key) {
        return config.get("players." + playerUUID + "." + key);
    }

    private void saveConfig() {
        try {
            config.save(settingsFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}