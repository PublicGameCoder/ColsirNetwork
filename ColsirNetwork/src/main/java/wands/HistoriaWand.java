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
import org.bukkit.util.Vector;

import colsirnetwork.ColsirNetwork;
import colsirnetwork.ParticlesManager;
import net.minecraft.server.v1_12_R1.EnumParticle;

public class HistoriaWand extends Wand implements Listener {
	
	private enum SpellsEnum {
		ObsidianWall,
		Eathquake;
	}
	private SpellsEnum currentSpell;

	private List<Block> unbreakableBlocks;
	private static HistoriaWand instance;

	public static HistoriaWand getManager(WandDetails wandDetails) {
		if (instance == null) {
			instance = new HistoriaWand(wandDetails);
		}else {
			instance.wandDetails = wandDetails;
		}
		return instance;
	}
	
	public static HistoriaWand getManager() {
		return instance;
	}
	
	private HistoriaWand(WandDetails wandDetails) {
		unbreakableBlocks = new ArrayList<Block>();
		ColsirNetwork.getInstance().getServer().getPluginManager().registerEvents(this, ColsirNetwork.getInstance());
		this.wandDetails = wandDetails;
		currentSpell = SpellsEnum.values()[wandDetails.spellIndex];
	}

	@Override
	protected void use() {
		switch (currentSpell) {
		case ObsidianWall:
			useEathquakeSpell();//useWallSpell();
			break;
		case Eathquake:
			useEathquakeSpell();
			break;
		default:
			useWallSpell();
			break;
		}
	}
	
	private int quakeWidth = 5;
	private int quakeDepth = 8;
	private float viewRange = 20f;
	@SuppressWarnings("incomplete-switch")
	private void useEathquakeSpell() {
		Player master = wandDetails.master;
		master.sendMessage("Spell used!");
		List<Block> blocks = master.getLineOfSight(null, 2);
		if (blocks.isEmpty())return;
		Block block = blocks.get(blocks.size()-1);
		Location bLoc = block.getLocation();
		bLoc.setY(master.getLocation().getY());
		BlockFace front = blocks.get(blocks.size()-2).getFace(block);
		BlockFace face = getFace(block.getFace(blocks.get(blocks.size()-2)),-90f);
		block = block.getWorld().getBlockAt(bLoc);
		Block b = block.getRelative(getFace(face, -90f));
		bLoc = b.getLocation();
		
		BlockFace left = getFace(front,-90);
		BlockFace right = getFace(front,90);
		List<Block> Blocations = new ArrayList<Block>();
		Block neightbore = b;
		for (int leftWidth = 0; leftWidth < quakeWidth/2; leftWidth++) {
			neightbore = neightbore.getRelative(left);
			Blocations.add(neightbore);
		}
		neightbore = b;
		Blocations.add(b);
		for (int rightWidth = 0; rightWidth < quakeWidth/2; rightWidth++) {
			neightbore = neightbore.getRelative(right);
			Blocations.add(neightbore);
		}
		
		List<Vector> particleLocs = new ArrayList<Vector>();
		for (Block blockLoc : Blocations) {
			particleLocs.add(blockLoc.getLocation().toVector());
		}
		
		float depthincreasement = 0.5f;
		Vector increasement = null;
		switch (front) {
		case NORTH:
			increasement = new Vector(0,0,-depthincreasement);
			break;
		case EAST:
			increasement = new Vector(depthincreasement,0,0);
			break;
		case SOUTH:
			increasement = new Vector(0,0,depthincreasement);
			break;
		case WEST:
			increasement = new Vector(-depthincreasement,0,0);
			break;
		default:
			increasement = new Vector(0,0,-depthincreasement);
			break;
		}
		
		float currentDepth = 0.0f;
		while(currentDepth <= quakeDepth) {
			for (Vector pLoc : particleLocs) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					ParticlesManager.getManager().spawnParticlePlayerPacketInReach(player, EnumParticle.HEART, (int)viewRange, new Location(b.getWorld(),pLoc.getX(),pLoc.getY(),pLoc.getZ()), new Vector(0,0,0), 1, 2);
				}
				pLoc.add(increasement);
			}
			currentDepth += depthincreasement;
		}
	}

	private int blockWidth = 3;
	private int blockHeight = 2;
	private void useWallSpell() {
		Player master = wandDetails.master;
		master.sendMessage("Spell used!");
		List<Block> blocks = master.getLineOfSight(null, 2);
		if (blocks.isEmpty())return;
		Block block = blocks.get(blocks.size()-1);
		Location bLoc = block.getLocation();
		bLoc.setY(master.getLocation().getY());
		BlockFace face = getFace(block.getFace(blocks.get(blocks.size()-2)),-90f);
		bLoc.setY(bLoc.getY()+(blockHeight/2));
		block = block.getWorld().getBlockAt(bLoc);
		Block b = block.getRelative(getFace(face, 180f));
		bLoc = b.getLocation();
		for (int y = 0; y < blockHeight; y++ ) {
			for (int x = 0; x < blockWidth; x++ ) {
				changeTempIfPossible(b, Material.OBSIDIAN);
				b = b.getRelative(face);
			}
			bLoc.setY(bLoc.getY()-1);
			b = block.getWorld().getBlockAt(bLoc);
		}
	}
	
	@SuppressWarnings("incomplete-switch")
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
}
