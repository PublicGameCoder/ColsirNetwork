package wands;

public class ElrymWand extends Wand {

	private static ElrymWand instance;

	public static ElrymWand getManager(WandDetails wandDetails) {
		if (instance == null) {
			instance = new ElrymWand();
		}
		instance.wandDetails = wandDetails;
		return instance;
	}
	
	public static ElrymWand getManager() {
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
