package theking530.staticpower.items.tools.basictools;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import theking530.staticpower.StaticPower;
import theking530.staticpower.items.EquipmentMaterial;
import theking530.staticpower.utilities.EnumTextFormatting;


public class BaseSword extends ItemSword {

	public String NAME = "";
	public EquipmentMaterial MATERIAL;
	
	public BaseSword(EquipmentMaterial material, String unlocalizedName) {
		super(material.getToolMaterial());
		NAME= unlocalizedName;
		setUnlocalizedName(unlocalizedName);
		setRegistryName(unlocalizedName);
		setCreativeTab(StaticPower.StaticPower);
		MATERIAL = material;
	}
	
	@Override  
		public void addInformation(ItemStack itemstack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
    	if(showHiddenTooltips()) {
    		//String tempSpeed = "Attack Speed: " +  temp.toArray()[0];
    		String tempDamage = "Damage: " + (Math.round(getAttackDamage()) + 4); 
    		String tempDurability = "Durability: " + (MATERIAL.getToolMaterial().getMaxUses()-itemstack.getMetadata()) + "/" + MATERIAL.getToolMaterial().getMaxUses();
    		
    		
    		//list.add(tempSpeed);
    		list.add(tempDurability);
    		list.add(tempDamage);
    	}else{
    		list.add(EnumTextFormatting.ITALIC + "Hold Shift");
	    }
	}
	public boolean showHiddenTooltips() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}
}