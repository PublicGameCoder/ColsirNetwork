package wands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import colsirnetwork.Actionbar;
import colsirnetwork.ChatUtil;
import net.md_5.bungee.api.ChatColor;

public class WandHandler implements Listener {

	WandHandler() {
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getItem();
		if (item == null || item.getType() == Material.AIR || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName())return;
		if (!WandEnum.isValidWand(item,e)) {
			return;
		}
		e.setCancelled(true);
		WandEnum type = WandEnum.getByName(item.getItemMeta().getDisplayName());
		if (type == null) {
			if (player.isOp()) {
				ChatUtil.sendTo(player,"Wand type was not found!");
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
	
	List<Player> timeOut = new ArrayList<Player>();
	@EventHandler
	public void onScroll(PlayerItemHeldEvent e) {
		Player player = e.getPlayer();
		if (timeOut.contains(player)) {
			timeOut.remove(player);
			return;
		}
		if (!player.isSneaking())return;
		ItemStack item = player.getInventory().getItem(e.getPreviousSlot());
		if (item == null || item.getType() == Material.AIR)return;
		if (!WandEnum.isValidWand(item))return;
		WandEnum type = WandEnum.getByName(item.getItemMeta().getDisplayName());
		if (type == null) {
			if (player.isOp()) {
				ChatUtil.sendTo(player,"Wand type was not found!");
			}
			return;
		}
		
		WandDetails wandDetails = new WandDetails(type, item);
		wandDetails.master = player;
		
		String spellName = "&cunknown";
		int dynamicIndex = (e.getNewSlot() > e.getPreviousSlot())? 1 : -1;
		switch (type) {
		case Arroxin:
			spellName = ArroxinWand.getManager(wandDetails).onSpellChange(dynamicIndex);
			break;
		case Elrym:
			spellName = ElrymWand.getManager(wandDetails).onSpellChange(dynamicIndex);
			break;
		case Historia:
			spellName = HistoriaWand.getManager(wandDetails).onSpellChange(dynamicIndex);
			break;
		case Levitheros:
			spellName = LevitherosWand.getManager(wandDetails).onSpellChange(dynamicIndex);
			break;
		case Zorcus:
			spellName = ZorcusWand.getManager(wandDetails).onSpellChange(dynamicIndex);
			break;
		default:
			break;
			
		}
		
		List<String> lores = new ArrayList<String>();
		
		lores.add(ChatColor.translateAlternateColorCodes('&', "&6Current Spell: &l"+spellName));
		
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lores);
		item.setItemMeta(meta);
		
		Actionbar bar = new Actionbar(spellName);
		bar.sendToPlayer(player);
		e.setCancelled(true);
		timeOut.add(player);
	}

}
