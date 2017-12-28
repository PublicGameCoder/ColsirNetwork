package colsirnetwork;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;

public enum WandEnum {
	Historia("&2Historia", "ColsirWands.historia", Material.STONE, 1, (short)0, (byte)5),
	Arroxin("&1Arr&foxin", "ColsirWands.arroxin", Material.STICK, 1, (short)0, (byte)0),
	Sparta("&4Spar&fta", "ColsirWands.sparta", Material.BOW, 1, (short)0, (byte)0),
	Zorcus("&2Zor&fcus", "ColsirWands.zorcus", Material.DIAMOND_SWORD, 1, (short)0, (byte)0),
	Levitheros("&2Levi&0theros", "ColsirWands.levitheros", Material.DIAMOND_AXE, 1, (short)0, (byte)0)
	;
	
	private String _name;
	private String _permission;
	private Material _material;
	private int _amount;
	private short _damage;
	private byte _data;
	
	WandEnum(String name, String perm, Material mat, int amount, short damage, byte data) {
		this._name = ChatColor.translateAlternateColorCodes('&', name);
		this._permission = perm;
		this._material = mat;
		this._amount = amount;
		this._damage = damage;
		this._data = data;
	}

	/**
	 * Get the wand by its name
	 * @param name the name of the wand
	 * @return the wand. null if not found
	 */
	public static WandEnum getByName(String name) {
		WandEnum wand = null;
		
		for (WandEnum w : WandEnum.values()) {
			if (ChatColor.stripColor(w.getName()).equalsIgnoreCase(name)) {
				wand = w;
				break;
			}
		}
		
		return wand;
	}

	public String getName() {
		return this._name;
	}

	public String getPermission() {
		return this._permission;
	}

	public Material getMaterial() {
		return this._material;
	}

	public int getAmount() {
		return this._amount;
	}

	public short getDamage() {
		return this._damage;
	}

	public byte getData() {
		return this._data;
	}

}
