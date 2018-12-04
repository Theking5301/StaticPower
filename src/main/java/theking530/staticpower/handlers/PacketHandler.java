package theking530.staticpower.handlers;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.client.gui.widgets.tabs.PacketPowerControlTab;
import theking530.staticpower.client.gui.widgets.tabs.PacketRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.PacketSideConfigTab;
import theking530.staticpower.items.itemfilter.PacketItemFilter;
import theking530.staticpower.logic.gates.powercell.PacketPowerCell;
import theking530.staticpower.logic.gates.timer.PacketTimer;
import theking530.staticpower.logic.gates.transducer.PacketSignalMultiplier;
import theking530.staticpower.machines.batteries.PacketGuiBattery;
import theking530.staticpower.machines.refinery.controller.PacketFluidRefineryController;
import theking530.staticpower.machines.tileentitycomponents.PacketFluidContainerComponent;
import theking530.staticpower.tileentity.digistorenetwork.digistore.PacketLockDigistore;

public class PacketHandler {
	public static SimpleNetworkWrapper net;
	private static int nextPacketId = 0; 
	
	public static void initPackets() {
		net = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
		registerMessage(PacketSignalMultiplier.Message.class, PacketSignalMultiplier.class);
		registerMessage(PacketPowerCell.Message.class, PacketPowerCell.class);
		registerMessage(PacketTimer.Message.class, PacketTimer.class);
		registerMessage(PacketGuiBattery.Message.class, PacketGuiBattery.class);
		registerMessage(PacketRedstoneTab.Message.class, PacketRedstoneTab.class);
		registerMessage(PacketPowerControlTab.Message.class, PacketPowerControlTab.class);
		registerMessage(PacketSideConfigTab.Message.class, PacketSideConfigTab.class);
		registerMessage(PacketItemFilter.Message.class, PacketItemFilter.class);
		registerMessage(PacketFluidContainerComponent.Message.class, PacketFluidContainerComponent.class);
		
		registerMessage(PacketFluidContainerComponent.Message.class, PacketFluidContainerComponent.class);
		registerMessage(PacketLockDigistore.Message.class, PacketLockDigistore.class);
		registerMessage(PacketFluidRefineryController.Message.class, PacketFluidRefineryController.class);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void registerMessage(Class packet, Class message) {
	    net.registerMessage(packet, message, nextPacketId, Side.CLIENT);
	    net.registerMessage(packet, message, nextPacketId, Side.SERVER);
	    nextPacketId++;
	}
}

