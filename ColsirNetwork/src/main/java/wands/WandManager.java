package wands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import colsirnetwork.ChatUtil;
import colsirnetwork.ColsirNetwork;

public class WandManager {
	
	private static WandManager instance;
	private WandHandler handler;
	private Map<WandEnum,ItemStack> wands = new HashMap<WandEnum,ItemStack>();

	public static WandManager getManager() {
		if (instance == null) {
			instance = new WandManager();
		}
		return instance;
	}
	
	private WandManager() {
		wands = new HashMap<WandEnum,ItemStack>();
		Material material = null;
		int amount = 1;
		String name = null;
		short damage = 0;
		byte data = 0;
		for (WandEnum wand : WandEnum.values()) {
			material = wand.getMaterial();
			amount = wand.getAmount();
			damage = wand.getDamage();
			data = wand.getData();
			name = wand.getName();
			@SuppressWarnings("deprecation")
			ItemStack w = new ItemStack(material,amount, (short)damage, (byte)data);
			ItemMeta meta = w.getItemMeta();
			meta.setDisplayName(name);
			meta.setUnbreakable(true);
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			w.setItemMeta(meta);
			wands.put(wand, w);
		}
		handler = new WandHandler();
		ColsirNetwork.getInstance().getServer().getPluginManager().registerEvents(handler, ColsirNetwork.getInstance());
	}

	public void giveWand(Player player, WandEnum type) {
		ItemStack wand = wands.get(type);
		player.getInventory().addItem(wand);
		ChatUtil.sendTo(player, "You have received a "+type.getName()+"&7!");
	}

}
