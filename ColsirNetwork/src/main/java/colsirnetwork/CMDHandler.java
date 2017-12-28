package colsirnetwork;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You need to be a player to execute this command!");
			return true;
		}
		Player player = (Player) sender;
		if (args.length <= 0) {
			player.sendMessage(ChatColor.RED+command.getUsage());
			return true;
		}
		WandEnum wand = WandEnum.getByName(args[0]);
		if (wand == null) {
			player.sendMessage(ChatColor.RED+"Sorry, but there is no wand called "+args[0]+"!");
			return true;
		}
		
		if (!player.hasPermission(wand.getPermission())) {
			if (player.isOp()) {
				player.sendMessage(ChatColor.RED+"You don't have permission to use the "+wand.getName()+ChatColor.RED+" wand!");
				player.sendMessage(ChatColor.RED+"Missing permission: "+wand.getPermission());
			}else {
				player.sendMessage(ChatColor.RED+"You don't have permission to use the "+wand.getName()+ChatColor.RED+" wand!");
			}
			return true;
		}
		
		WandManager.getManager().giveWand(player,wand);
		
		return true;
	}

}
