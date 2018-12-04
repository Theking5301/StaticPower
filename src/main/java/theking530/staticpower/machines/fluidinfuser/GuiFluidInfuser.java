package theking530.staticpower.machines.fluidinfuser;

import api.gui.tab.BaseGuiTab.TabSide;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.buttons.ArrowButton;
import theking530.staticpower.client.gui.widgets.progressbars.ArrowProgressBar;
import theking530.staticpower.client.gui.widgets.tabs.GuiInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiMachinePowerInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiFluidBarFromTank;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;
import theking530.staticpower.handlers.PacketHandler;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent.FluidContainerInteractionMode;
import theking530.staticpower.machines.tileentitycomponents.PacketFluidContainerComponent;

public class GuiFluidInfuser extends BaseGuiContainer{
	

	private GuiInfoTab infoTab;
	private TileEntityFluidInfuser infuserTileEntity;
	
	public GuiFluidInfuser(InventoryPlayer invPlayer, TileEntityFluidInfuser teInfuser) {
		super(new ContainerFluidInfuser(invPlayer, teInfuser), 176, 166);
		infuserTileEntity = teInfuser;	
		
		registerWidget(new GuiPowerBarFromEnergyStorage(teInfuser, 8, 50, 16, 42));
		registerWidget(new GuiFluidBarFromTank(teInfuser.fluidTank, 154, 68, 16, 60, Mode.Input, teInfuser));
		registerWidget(new ArrowProgressBar(teInfuser, 73, 32, 32, 16));
		
		getTabManager().registerTab(infoTab = new GuiInfoTab(100, 85));
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, teInfuser));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, teInfuser));	
		getTabManager().registerTab(new GuiMachinePowerInfoTab(80, 80, teInfuser).setTabSide(TabSide.LEFT).setOffsets(-31, 0));
	}	
	@Override
	public void initGui() {
		super.initGui();
		
	    this.buttonList.add(new ArrowButton(1, guiLeft-24, guiTop+30, 16, 10, "<"));
	    
	    if(infuserTileEntity.fluidContainerInteractionComponent.getMode() == FluidContainerInteractionMode.FILL) {
	    	buttonList.get(0).displayString = ">";
	    }else{
	    	buttonList.get(0).displayString = "<";
	    }
	}
	@Override
	protected void actionPerformed(GuiButton B) {
		if(B.id == 1) {
			IMessage msg = new PacketFluidContainerComponent(infuserTileEntity.getFluidInteractionComponent().getInverseMode(), infuserTileEntity.getComponents().indexOf(infuserTileEntity.getFluidInteractionComponent()), infuserTileEntity.getPos());
			PacketHandler.net.sendToServer(msg);
			infuserTileEntity.getFluidInteractionComponent().setMode(infuserTileEntity.getFluidInteractionComponent().getInverseMode());
			
		    if(infuserTileEntity.getFluidInteractionComponent().getMode() == FluidContainerInteractionMode.FILL) {
		    	buttonList.get(0).displayString = ">";
		    }else{
		    	buttonList.get(0).displayString = "<";
		    }
		}
	}	

	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.infuserTileEntity.getName());
	
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752 );
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 3, 4210752);
	}	
	@Override
	protected void drawExtra(float f, int i, int j) {		
		this.drawGenericBackground(-30, 5, 28, 60);
		this.drawGenericBackground(-30, 70, 28, 64);
		this.drawGenericBackground();
		this.drawPlayerInventorySlots();
		
    	this.drawContainerSlots(infuserTileEntity, this.inventorySlots.inventorySlots);
    	
//		int powerCost; 
//		int fluidCost; 
//		if(infuserTileEntity.slotsInternal.getStackInSlot(0) != ItemStack.EMPTY) {
//			fluidCost = InfuserRecipeRegistry.Infusing().getInfusingFluidCost(infuserTileEntity.slotsInternal.getStackInSlot(0), infuserTileEntity.fluidTank.getFluid());
//			powerCost = infuserTileEntity.getProcessingEnergy(infuserTileEntity.slotsInternal.getStackInSlot(0));
//		}else{
//			fluidCost = InfuserRecipeRegistry.Infusing().getInfusingFluidCost(infuserTileEntity.slotsInput.getStackInSlot(0), infuserTileEntity.fluidTank.getFluid());
//			powerCost = infuserTileEntity.getProcessingEnergy(infuserTileEntity.slotsInput.getStackInSlot(0));
//		}
//		String power = NumberFormat.getNumberInstance(Locale.US).format(powerCost);
//		String fluid = NumberFormat.getNumberInstance(Locale.US).format(fluidCost);
//    	String text = ("Infuse items with the" + "=" + "power of exceptional" + "=" + "Liquids." + "=" + "=" + EnumTextFormatting.GREEN +"Power Cost: " +  power + "=" + EnumTextFormatting.AQUA +"Fluid Cost: "+ fluid + EnumTextFormatting.WHITE);
//
		infoTab.setText(infuserTileEntity.getBlockType().getLocalizedName(), "test");
	}	
}


