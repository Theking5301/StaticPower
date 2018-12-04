package theking530.staticpower.logic.gates.led;

import api.RegularWrenchMode;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theking530.staticpower.assists.utilities.Color;
import theking530.staticpower.logic.gates.BlockLogicGate;

public class BlockLED extends BlockLogicGate {

    public static final PropertyInteger SIDE = PropertyInteger.create("side", 0, 5);
	
	public BlockLED(String name) {
		super(name, 0);
		setLightLevel(15.0f);
	}

	@Override
		public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntityLED tempLED = (TileEntityLED) world.getTileEntity(pos);
			if(player.getHeldItem(hand) != null) {
				if(player.getHeldItem(hand).getItem() instanceof ItemDye) {
					tempLED.setColor(Color.values()[player.getHeldItem(hand).getMetadata()]);
					return true;	
				}
			}
			if(player.isSneaking()) {
				tempLED.cycleColor(true);					
			}else{
				tempLED.cycleColor(false);	
			}
			player.sendMessage(new TextComponentString("Color Set: " + tempLED.COLOR));
    		return true;
    	}
		return true;
	}
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
    	if(world.getTileEntity(pos) instanceof TileEntityLED) {
			TileEntityLED tempMachine = (TileEntityLED)world.getTileEntity(pos);
			if(placer.getHeldItemMainhand().hasTagCompound()) {
				tempMachine.onMachinePlaced(placer.getHeldItemMainhand().getTagCompound());
			}
		}
    }
    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos){
		return super.getLightValue(state, world, pos);
    }
	@Override
	public void wrenchBlock(EntityPlayer player, RegularWrenchMode mode, ItemStack wrench, World world, BlockPos pos, EnumFacing facing, boolean returnDrops) {
		if (!world.isRemote) {
			TileEntityLED tempLED = (TileEntityLED) world.getTileEntity(pos);
			tempLED.INVERTED = !tempLED.INVERTED;
			if(tempLED.INVERTED) {
				player.sendMessage(new TextComponentString("Behaviour: Inverted"));
			}else{
				player.sendMessage(new TextComponentString("Behaviour: Regular"));
			}
		}
	}
	public String getDescrption(ItemStack stack){
		if(stack.hasTagCompound()) {
			return "A dimmable redstone powered light.\nColor: " + Color.values()[stack.getTagCompound().getInteger("COLOR")].toString();
		}
		return null;	
	}
	public String getInputDescrption(ItemStack stack){
		return "A redstone singlal of variable strength.";
	}
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityLED();
	}
}
