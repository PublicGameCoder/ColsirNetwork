package wands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WandHandler implements Listener {

	WandHandler() {
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		ItemStack item = e.getItem();
		if (item == null || item.getType() == Material.AIR || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName())return;
		if (!WandEnum.isValidWand(item,e)) {
			return;
		}
		e.setCancelled(true);
		Player player = e.getPlayer();
		WandEnum type = WandEnum.getByName(item.getItemMeta().getDisplayName());
		if (type == null) {
			if (player.isOp()) {
				player.sendMessage("Wand type was not found!");
			}
			return;
		}
		
		WandDetails wandDetails = new WandDetails(type, item);
		wandDetails.master = player;
		wandDetails.currentAction = e.getAction();
		
		switch (type) {
		case Arroxin:
			ArroxinWand.getManager(wandDetails).use();
			break;
		case Elrym:
			ElrymWand.getManager(wandDetails).use();
			break;
		case Historia:
			HistoriaWand.getManager(wandDetails).use();
			break;
		case Levitheros:
			LevitherosWand.getManager(wandDetails).use();
			break;
		case Zorcus:
			ZorcusWand.getManager(wandDetails).use();
			break;
		default:
			break;
			
		}
	}

}
