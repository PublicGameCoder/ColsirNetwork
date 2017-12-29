package wands;

public class LevitherosWand extends Wand {

	private static LevitherosWand instance;

	public static LevitherosWand getManager(WandDetails wandDetails) {
		if (instance == null) {
			instance = new LevitherosWand();
		}
		instance.wandDetails = wandDetails;
		return instance;
	}

	@Override
	protected void use() {
		
	}
}
