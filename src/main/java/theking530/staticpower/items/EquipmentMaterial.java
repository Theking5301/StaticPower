package theking530.staticpower.items;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.text.TextFormatting;

public class EquipmentMaterial {

	private String name;
	private TextFormatting color;
	private ArmorMaterial armorMaterial;
	private ToolMaterial toolMaterial;
	private EquipmentMaterial parentMaterial;

	public EquipmentMaterial(String name, TextFormatting color, EquipmentMaterial parentMaterial, boolean validArmor, boolean validTool) {
		this.name = name;
		this.color = color;
		this.parentMaterial = parentMaterial;
	}
	
	public EquipmentMaterial getParentMaterial() {
		if(parentMaterial != null) {
			return getParentMaterialWorker(parentMaterial);
		}else{
			return this;
		}
	}
	private EquipmentMaterial getParentMaterialWorker(EquipmentMaterial parentMaterial) {
		if(parentMaterial.parentMaterial != null) {
			return getParentMaterialWorker(parentMaterial);
		}else{
			return parentMaterial;
		}
	}
	public void initArmorMaterial(ArmorMaterial material){
		armorMaterial = material;
	}
	public void initToolMaterial(ToolMaterial material) {
		toolMaterial = material;
	}
	
	public ArmorMaterial getArmorMaterial() {
		return armorMaterial;
	}
	public boolean isValidArmorMaterial() {
		return getArmorMaterial() != null;
	}
	
	public ToolMaterial getToolMaterial() {
		return toolMaterial;
	}
	public boolean isValidToolMaterial() {
		return getToolMaterial() != null;
	}

	public String toColorString() {
		return color + name;
	}
}
