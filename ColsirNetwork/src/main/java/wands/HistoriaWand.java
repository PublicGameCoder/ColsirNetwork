package wands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import colsirnetwork.ColsirNetwork;

public class HistoriaWand extends Wand implements Listener {

	private List<Block> unbreakableBlocks;
	private static HistoriaWand instance;

	public static HistoriaWand getManager(WandDetails wandDetails) {
		if (instance == null) {
			instance = new HistoriaWand();
		}
		instance.wandDetails = wandDetails;
		return instance;
	}
	
	private HistoriaWand() {
		unbreakableBlocks = new ArrayList<Block>();
		ColsirNetwork.getInstance().getServer().getPluginManager().registerEvents(this, ColsirNetwork.getInstance());
	}

	@Override
	protected void use() {
		useWallSpell();
	}
	
	private void useWallSpell() {
		Player master = wandDetails.master;
		master.sendMessage("Spell used!");
		List<Block> blocks = master.getLineOfSight(null, 2);
		if (blocks.isEmpty())return;
		Block block = blocks.get(blocks.size()-1);
		changeTempIfPossible(block, Material.OBSIDIAN);
	}

	private void changeTempIfPossible(final Block b, final Material type) {
		if (b.getType() != Material.AIR &&
				b.getType() != Material.LONG_GRASS &&
				b.getType() != Material.YELLOW_FLOWER &&
				b.getType() != Material.RED_ROSE &&
				b.getType() != Material.DEAD_BUSH)return;
		final Material oldType = b.getType();
		@SuppressWarnings("deprecation")
		final
		byte oldData = b.getData();
		
		b.setType(type);
		unbreakableBlocks.add(b);
		new BukkitRunnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				b.setType(oldType);
				b.setData(oldData);
				unbreakableBlocks.remove(b);
			}
		}.runTaskLater(ColsirNetwork.getInstance(), 20 * 5);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		for (Block b : unbreakableBlocks) {
			if (e.getBlock().equals(b)) {
				e.setCancelled(true);
				break;
			}
		}
	}
}
