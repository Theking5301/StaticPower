package theking530.staticpower.assists;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class MaterialSet {
	private String name;
	
	private ItemStack ingot;
	private ItemStack dust;
	private ItemStack nugget;
	private Block block;
	private ItemStack gear;
	private ItemStack plate;
	
	public MaterialSet(String name, ItemStack ingot, ItemStack dust, ItemStack nugget, Block block, ItemStack gear, ItemStack plate) {
		this.ingot = ingot;
		this.dust = dust;
		this.nugget = nugget;
		this.block = block;
		this.gear = gear;
		this.plate = plate;
		this.name = name;
	}
	public ItemStack getIngot() {
		return ingot;
	}
	public ItemStack getDust() {
		return dust;
	}
	public ItemStack getNugget() {
		return nugget;
	}
	public Block getBlock() {
		return block;
	}
	public ItemStack getGear() {
		return gear;
	}
	public ItemStack getPlate() {
		return plate;
	}
	public String getName() {
		return name;
	}
}
