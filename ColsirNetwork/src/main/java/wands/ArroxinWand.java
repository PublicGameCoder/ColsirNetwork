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
	
	public static ArroxinWand getManager() {
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
