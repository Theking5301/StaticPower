package theking530.staticpower.logic.gates;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.blocks.BaseItemBlock;

public class ItemLogicGate extends BaseItemBlock{

	public BlockLogicGate BLOCK;
	
	public ItemLogicGate(BlockLogicGate block, String name) {
		super(block, name);
		BLOCK = block;
	}
	public boolean showHiddenTooltips() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}
	@Override  
	public void addInformation(ItemStack itemstack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		String DESC = BLOCK.getDescrption(itemstack);
		String IN = BLOCK.getInputDescrption(itemstack);
		String OUT = BLOCK.getOutputDescrption(itemstack);
		String EXTRA = BLOCK.getExtraDescrption(itemstack);

		String input = EnumTextFormatting.BOLD + "" + EnumTextFormatting.BLUE + "Input: " + EnumTextFormatting.REGULAR + "" + EnumTextFormatting.WHITE;
		String output = EnumTextFormatting.BOLD + "" + EnumTextFormatting.GOLD + "Output: " + EnumTextFormatting.REGULAR + "" + EnumTextFormatting.WHITE;
		String extra = EnumTextFormatting.BOLD + "" + EnumTextFormatting.DARK_PURPLE + "Extra Output: " + EnumTextFormatting.REGULAR + "" + EnumTextFormatting.WHITE;
		if(DESC != null) {
    		list.add(EnumTextFormatting.WHITE + DESC);	
		}
		if(showHiddenTooltips()) {
    		if(IN != null) {
	    		list.add(input + IN);	
    		}
    		if(OUT != null) {
	    		list.add(output + OUT);	
    		}
    		if(EXTRA != null) {
	    		list.add(extra + EXTRA);	
    		}
    	}else{
    		if(IN != null || OUT != null || EXTRA != null)
    		list.add(EnumTextFormatting.ITALIC +  "Hold Shift");
	    }
	}
}
