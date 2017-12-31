package colsirnetwork;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ChatUtil {

	private static String prefix = ConfigManager.getManager().getChatPrefix();
	
	public static void sendTo(Player p, String message) {
		message = ChatColor.translateAlternateColorCodes('&', prefix + message);
		p.sendMessage(message);
	}
}
