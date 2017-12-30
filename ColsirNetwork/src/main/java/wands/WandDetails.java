package wands;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

class WandDetails {

	private WandEnum type;
	private ItemStack wand;
	public Player master;
	public Action currentAction;
	public int spellIndex;
	

	public WandDetails(WandEnum type, ItemStack wand) {
		this.type = type;
		this.wand = wand;
		this.spellIndex = 0;
	}	
	
	public WandEnum getType() {
		return this.type;
	}
	
	public ItemStack getWand() {
		return this.wand;
	}

}
