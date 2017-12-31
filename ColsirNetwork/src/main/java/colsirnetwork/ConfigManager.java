package colsirnetwork;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

	private File configFile;
	
	private static ConfigManager instance;
	public static ConfigManager getManager() {
		if (instance == null) {
			instance = new ConfigManager();
		}
		return instance;
	}
	
	private ConfigManager() {
		if (!ColsirNetwork.getInstance().getDataFolder().exists()) {
			ColsirNetwork.getInstance().getDataFolder().mkdirs();
		}
		configFile = new File(ColsirNetwork.getInstance().getDataFolder(), "configuration.yml");
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setDefaults();
		}
	}
	
	private void setDefaults() {
		FileConfiguration config = getConfig();
		
		ConfigurationSection options = config.createSection("Options");
		options.set("ChatPrefix", "&9[&6&lColsirNetwork&r&9] &7");
		
		saveConfig(config);
	}

	private FileConfiguration getConfig() {
		return YamlConfiguration.loadConfiguration(configFile);
	}
	
	private void saveConfig(FileConfiguration config) {
		try {
			config.save(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getString(String path) {
		return getConfig().getString(path);
	}
	
	public String getChatPrefix() {
		return getString("Options.ChatPrefix");
	}
}
