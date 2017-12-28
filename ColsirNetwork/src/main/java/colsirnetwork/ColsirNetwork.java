package colsirnetwork;

import org.bukkit.plugin.java.JavaPlugin;

public class ColsirNetwork extends JavaPlugin {
	
	private static ColsirNetwork instance;
	
	@Override
	public void onEnable() {
		instance = this;
		getCommand("Colsirwand").setExecutor(new CMDHandler());
		WandManager.getManager();
	}
	
	public static ColsirNetwork getInstance() {
		return instance;
	}
}
