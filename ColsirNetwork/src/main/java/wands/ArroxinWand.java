package wands;

class ArroxinWand extends Wand {
	
	private static ArroxinWand instance;

	public static ArroxinWand getManager(WandDetails wandDetails) {
		if (instance == null) {
			instance = new ArroxinWand();
		}
		instance.wandDetails = wandDetails;
		return instance;
	}

	@Override
	protected void use() {
		
	}

}
