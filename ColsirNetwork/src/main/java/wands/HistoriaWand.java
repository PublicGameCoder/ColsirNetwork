package wands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
	
	public static HistoriaWand getManager() {
		if (instance == null) {
			instance = new HistoriaWand();
		}
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
	
	private int width = 3;
	private int height = 2;
	private void useWallSpell() {
		Player master = wandDetails.master;
		master.sendMessage("Spell used!");
		List<Block> blocks = master.getLineOfSight(null, 2);
		if (blocks.isEmpty())return;
		Block block = blocks.get(blocks.size()-1);
		Location bLoc = block.getLocation();
		bLoc.setY(master.getLocation().getY());
		BlockFace face = getFace(block.getFace(blocks.get(blocks.size()-2)),-90f);
		bLoc.setY(bLoc.getY()+(height/2));
		block = block.getWorld().getBlockAt(bLoc);
		Block b = block.getRelative(getFace(face, 180f));
		bLoc = b.getLocation();
		for (int y = 0; y < height; y++ ) {
			for (int x = 0; x < width; x++ ) {
				changeTempIfPossible(b, Material.OBSIDIAN);
				b = b.getRelative(face);
			}
			bLoc.setY(bLoc.getY()-1);
			b = block.getWorld().getBlockAt(bLoc);
		}
	}
	
	public BlockFace getFace(BlockFace currentFace, float rotationDeg) {
		if (rotationDeg <= 45) {
			rotationDeg += 405;
		}
		if (rotationDeg > 45 && rotationDeg <= 135) {
			switch(currentFace) {
			case NORTH:
				return BlockFace.EAST;
			case EAST:
				return BlockFace.SOUTH;
			case SOUTH:
				return BlockFace.WEST;
			case WEST:
				return BlockFace.NORTH;
			}
		}else if (rotationDeg > 135 && rotationDeg <= 225) {
			switch(currentFace) {
			case NORTH:
				return BlockFace.SOUTH;
			case EAST:
				return BlockFace.WEST;
			case SOUTH:
				return BlockFace.NORTH;
			case WEST:
				return BlockFace.EAST;
			}
		}else if (rotationDeg > 225 && rotationDeg <= 315) {
			switch(currentFace) {
			case NORTH:
				return BlockFace.WEST;
			case EAST:
				return BlockFace.NORTH;
			case SOUTH:
				return BlockFace.EAST;
			case WEST:
				return BlockFace.SOUTH;
			}
		}else if (rotationDeg > 315 && rotationDeg <= 405) {
			 return currentFace;
		}
	    return currentFace;
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
		BukkitRunnable t = new BukkitRunnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				b.setType(oldType);
				b.setData(oldData);
				unbreakableBlocks.remove(b);
			}
		};
		t.runTaskLater(ColsirNetwork.getInstance(), 20 * 5);
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
	
	public void destructor() {
	}
}
