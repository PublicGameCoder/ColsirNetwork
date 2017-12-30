package wands;

public class ZorcusWand extends Wand {

	private static ZorcusWand instance;

	public static ZorcusWand getManager(WandDetails wandDetails) {
		if (instance == null) {
			instance = new ZorcusWand();
		}
		instance.wandDetails = wandDetails;
		return instance;
	}
	
	public static ZorcusWand getManager() {
		return instance;
	}

	@Override
	protected void use() {
		
	}
	
	@Override
	protected String onSpellChange(int dynamicIndex) {
		return "";
	}
}
