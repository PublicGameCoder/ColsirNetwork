package wands;

abstract class Wand {

	protected WandDetails wandDetails;
	
	protected abstract void use();
	protected abstract String onSpellChange(int dynamicIndex);
}
