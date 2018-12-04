package theking530.staticpower.items;

import net.minecraft.item.ItemStack;

public class ItemComponents extends MultiItem {
	
	public ItemComponents() {
		super("component");
	}

	public static ItemStack basicProcessor;
	public static ItemStack staticProcessor;
	public static ItemStack energizedProcessor;
	public static ItemStack lumumProcessor;
	
	public static ItemStack rubber;
	public static ItemStack ioPort;
	public static ItemStack wireCopper;
	public static ItemStack wireSilver;
	public static ItemStack wireGold;
	public static ItemStack coilCopper;
	public static ItemStack coilSilver;
	public static ItemStack coilGold;

	public static ItemStack memoryChip;	
	public static ItemStack logicGatePowerSync;
	public static ItemStack invertedLogicGatePowerSync;
	public static ItemStack logicGateServo;
	public static ItemStack diode;
	public static ItemStack transistor;
	public static ItemStack internalClock;
	
	public static ItemStack basicUpgradePlate;
	public static ItemStack staticUpgradePlate;
	public static ItemStack energizedUpgradePlate;
	public static ItemStack lumumUpgradePlate;
	
	@Override
	protected void registerSubItems() {
		basicProcessor = createSubOreItem(1, "basicProcessor");
		staticProcessor = createSubItem(2, "staticProcessor");
		energizedProcessor = createSubItem(3, "energizedProcessor");
		lumumProcessor = createSubItem(4, "lumumProcessor");
		

		ioPort = createSubOreItem(5, "ioPort");
		wireCopper = createSubOreItem(6, "wireCopper");
		wireSilver = createSubOreItem(7, "wireSilver");
		wireGold = createSubOreItem(8, "wireGold");;
		coilCopper = createSubOreItem(9, "coilCopper");
		coilSilver = createSubOreItem(10, "coilSilver");
		coilGold = createSubOreItem(11, "coilGold");

		memoryChip = createSubItem(12, "memoryChip");
		logicGatePowerSync = createSubItem(13, "logicGatePowerSync");
		invertedLogicGatePowerSync = createSubItem(14, "invertedLogicGatePowerSync");
		logicGateServo = createSubItem(15, "logicGateServo");
		diode = createSubItem(16, "diode");
		transistor = createSubItem(17, "transistor");
		internalClock = createSubItem(18, "internalClock");
				
		basicUpgradePlate = createSubItem(19, "basicUpgradePlate");
		staticUpgradePlate = createSubItem(20, "staticUpgradePlate");
		energizedUpgradePlate = createSubItem(21, "energizedUpgradePlate");
		lumumUpgradePlate = createSubItem(22, "lumumUpgradePlate");
	}
}
