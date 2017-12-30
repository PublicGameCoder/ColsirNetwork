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
		this.spellIndex = -1;
	}	
	
	public WandEnum getType() {
		return this.type;
	}
	
	public ItemStack getWand() {
		return this.wand;
	}

	public void mergeData(WandDetails wandDetails) {
		if (wandDetails.getType() != null) {
			this.type = wandDetails.getType();
		}
		if (wandDetails.getWand() != null) {
			this.wand = wandDetails.getWand();
		}
		if (wandDetails.master != null) {
			this.master = wandDetails.master;
		}
		if (wandDetails.currentAction != null) {
			this.currentAction = wandDetails.currentAction;
		}
		if (wandDetails.spellIndex >= 0) {
			this.spellIndex = wandDetails.spellIndex;
		}
	}

}
