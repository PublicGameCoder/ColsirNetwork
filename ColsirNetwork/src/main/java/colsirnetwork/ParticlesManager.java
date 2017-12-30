package colsirnetwork;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class ParticlesManager {
	
	private static ParticlesManager pm;

	public static ParticlesManager getManager() {
		if (pm == null) pm = new ParticlesManager();
		return pm;
	}
	
	public void spawnParticlePlayerPacket(final Player[] players, final EnumParticle particle, final Location loc, final Vector offset, final int speed, final int amount) {
		new BukkitRunnable() {
			public void run() {
				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                         particle, //particleType
                         true, //true
                         (float)loc.getX(), //x position
                         (float)loc.getY(), //y position
                         (float)loc.getZ(), //z position
                         (float) offset.getX(),	// x offset
                         (float) offset.getY(),	// y offset
                         (float) offset.getZ(), // z offset
                         speed, // speed
                         amount, // amount
                         null
				);
				for (Player p : players) {
					((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
				}
				this.cancel();
			}
		}.runTaskTimer(ColsirNetwork.getInstance(), 0, 1);
	}
	
	public void spawnParticlePlayerPacket(final Player player, final EnumParticle particle, final Location loc, final Vector offset, final int speed, final int amount) {
		new BukkitRunnable() {
			public void run() {
				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                         particle, //particleType
                         true, //true
                         (float)loc.getX(), //x position
                         (float)loc.getY(), //y position
                         (float)loc.getZ(), //z position
                         (float) offset.getX(),	// x offset
                         (float) offset.getY(),	// y offset
                         (float) offset.getZ(), // z offset
                         speed, // speed
                         amount, // amount
                         null
				);
				((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
				this.cancel();
			}
		}.runTaskTimer(ColsirNetwork.getInstance(), 0, 1);
	}
	
	public void spawnParticlePlayerPacketInReach(final Player[] players, final EnumParticle particle, final int reach, final Location loc, final Vector offset, final int speed, final int amount) {
		new BukkitRunnable() {
			public void run() {
				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                         particle, //particleType
                         true, //true
                         (float)loc.getX(), //x position
                         (float)loc.getY(), //y position
                         (float)loc.getZ(), //z position
                         (float) offset.getX(),	// x offset
                         (float) offset.getY(),	// y offset
                         (float) offset.getZ(), // z offset
                         speed, // speed
                         amount, // amount
                         null
				);
				for (Player p : players) {
					if (loc.distance(p.getLocation()) < reach) {
						((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
					}
				}
				this.cancel();
			}
		}.runTaskTimer(ColsirNetwork.getInstance(), 0, 1);
	}
	
	public void spawnParticlePlayerPacketInReach(final Player player, final EnumParticle particle, final int reach, final Location loc, final Vector offset, final int speed, final int amount) {
		new BukkitRunnable() {
			public void run() {
				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                         particle, //particleType
                         true, //true
                         (float)loc.getX(), //x position
                         (float)loc.getY(), //y position
                         (float)loc.getZ(), //z position
                         (float) offset.getX(),	// x offset
                         (float) offset.getY(),	// y offset
                         (float) offset.getZ(), // z offset
                         speed, // speed
                         amount, // amount
                         null
				);
				if (loc.distance(player.getLocation()) < reach) {
					((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
				}
				this.cancel();
			}
		}.runTaskTimer(ColsirNetwork.getInstance(), 0, 1);
	}
	
	public void spawnParticlePlayersPacketInReach(final Player[] players, final EnumParticle particle, final int reach, final Location loc, final Vector offset, final int speed, final int amount) {
		new BukkitRunnable() {
			public void run() {
				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                         particle, //particleType
                         true, //true
                         (float)loc.getX(), //x position
                         (float)loc.getY(), //y position
                         (float)loc.getZ(), //z position
                         (float) offset.getX(),	// x offset
                         (float) offset.getY(),	// y offset
                         (float) offset.getZ(), // z offset
                         speed, // speed
                         amount, // amount
                         null
				);
				for (Player player : players) {
					if (loc.distance(player.getLocation()) < reach) {
						((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
					}
				}
				this.cancel();
			}
		}.runTaskTimer(ColsirNetwork.getInstance(), 0, 1);
	}
}
