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

	@Override
	protected void use() {
		
	}
}
