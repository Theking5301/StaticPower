package theking530.staticpower.items;

import net.minecraft.item.Item;
import theking530.staticpower.Registry;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.items.book.StaticPowerBook;
import theking530.staticpower.items.containers.BaseBattery;
import theking530.staticpower.items.containers.BaseFluidCapsule;
import theking530.staticpower.items.itemfilter.FilterTier;
import theking530.staticpower.items.itemfilter.ItemFilter;
import theking530.staticpower.items.tools.BlockReader;
import theking530.staticpower.items.tools.CoordinateMarker;
import theking530.staticpower.items.tools.ElectricSolderingIron;
import theking530.staticpower.items.tools.MetalHammer;
import theking530.staticpower.items.tools.SolderingIron;
import theking530.staticpower.items.tools.StaticWrench;
import theking530.staticpower.items.tools.WireCutters;
import theking530.staticpower.items.upgrades.BaseDigistoreCapacityUpgrade;
import theking530.staticpower.items.upgrades.BaseOutputMultiplierUpgrade;
import theking530.staticpower.items.upgrades.BasePowerUpgrade;
import theking530.staticpower.items.upgrades.BaseQuarryingUpgrade;
import theking530.staticpower.items.upgrades.BaseRangeUpgrade;
import theking530.staticpower.items.upgrades.BaseSpeedUpgrade;
import theking530.staticpower.items.upgrades.BaseTankUpgrade;
import theking530.staticpower.items.upgrades.DigistoreMiscUpgrades;
import theking530.staticpower.items.upgrades.ExperienceVacuumUpgrade;
import theking530.staticpower.items.upgrades.TeleportUpgrade;

public class ModItems {
	public static ItemMaterials Materials;
	public static FormerMolds FormerMolds;
	public static MiscItems MiscItems;
	public static ItemComponents Components;
	public static BaseDigistoreCapacityUpgrade DigistoreCapacityUpgrade;
	public static DigistoreMiscUpgrades DigistoreMiscUpgrade;
	
	public static BaseFluidCapsule BaseFluidCapsule;
	public static BaseFluidCapsule StaticFluidCapsule;
	public static BaseFluidCapsule EnergizedFluidCapsule;
	public static BaseFluidCapsule LumumFluidCapsule;
	
	public static BaseBattery BasicBattery;
	public static BaseBattery StaticBattery;
	public static BaseBattery EnergizedBattery;
	public static BaseBattery LumumBattery;	
	
	public static Item TeleportUpgrade;
	public static Item ExperienceVacuumUpgrade;
	public static Item BasicSpeedUpgrade;
	public static Item StaticSpeedUpgrade;
	public static Item EnergizedSpeedUpgrade;
	public static Item LumumSpeedUpgrade;
	
	public static Item BasicRangeUpgrade;
	public static Item StaticRangeUpgrade;
	public static Item EnergizedRangeUpgrade;
	public static Item LumumRangeUpgrade;
	
	public static Item BasicTankUpgrade;
	public static Item StaticTankUpgrade;
	public static Item EnergizedTankUpgrade;
	public static Item LumumTankUpgrade;
	
	public static Item BasicPowerUpgrade;
	public static Item StaticPowerUpgrade;
	public static Item EnergizedPowerUpgrade;
	public static Item LumumPowerUpgrade;
	
	public static Item StaticQuarryingUpgrade;
	public static Item EnergizedQuarryingUpgrade;
	public static Item LumumQuarryingUpgrade;
	
	public static Item CreativePowerUpgrade;
	public static Item CreativeTankUpgrade;
	public static Item CreativeSpeedUpgrade;
	
	public static Item BasicOutputMultiplierUpgrade;
	public static Item StaticOutputMultiplierUpgrade;
	public static Item EnergizedOutputMultiplierUpgrade;
	public static Item LumumOutputMultiplierUpgrade;
	
	public static Item StaticWrench;
	public static Item SolderingIron;
	public static Item ElectricSolderingIron;
	public static Item StaticBook;
	public static Item CoordinateMarker;
	public static Item WireCutters;
	public static Item MetalHammer;
	public static Item NetworkReader;
	
	public static Item BasicItemFilter;
	public static Item UpgradedItemFilter;
	public static Item AdvancedItemFilter;

	public static void init(Registry registry) {
		registry.PreRegisterItem(Materials = new ItemMaterials());
		registry.PreRegisterItem(MiscItems = new MiscItems());
		registry.PreRegisterItem(Components = new ItemComponents());
		registry.PreRegisterItem(FormerMolds = new FormerMolds());

		StaticWrench = new StaticWrench().setUnlocalizedName("StaticWrench");
		registry.PreRegisterItem(StaticWrench);
		SolderingIron = new SolderingIron("SolderingIron", 20);
		registry.PreRegisterItem(SolderingIron);
		ElectricSolderingIron = new ElectricSolderingIron("ElectricSolderingIron", 50000);
		registry.PreRegisterItem(ElectricSolderingIron);
		CoordinateMarker = new CoordinateMarker("CoordinateMarker");
		registry.PreRegisterItem(CoordinateMarker);
		WireCutters = new WireCutters(64);
		registry.PreRegisterItem(WireCutters);
		MetalHammer = new MetalHammer(64);
		registry.PreRegisterItem(MetalHammer);
		
		NetworkReader = new BlockReader("NetworkReader");
		registry.PreRegisterItem(NetworkReader);
		BasicItemFilter = new ItemFilter("BasicItemFilter", FilterTier.BASIC);
		registry.PreRegisterItem(BasicItemFilter);
		UpgradedItemFilter = new ItemFilter("UpgradedItemFilter", FilterTier.UPGRADED);
		registry.PreRegisterItem(UpgradedItemFilter);
		AdvancedItemFilter = new ItemFilter("AdvancedItemFilter", FilterTier.ADVANCED);
		registry.PreRegisterItem(AdvancedItemFilter);

		BasicBattery = new BaseBattery("BasicPortableBattery", Tier.BASIC.getPortableBatteryCapacity());
		registry.PreRegisterItem(BasicBattery);
		StaticBattery = new BaseBattery("StaticPortableBattery", Tier.STATIC.getPortableBatteryCapacity());
		registry.PreRegisterItem(StaticBattery);
		EnergizedBattery = new BaseBattery("EnergizedPortableBattery", Tier.ENERGIZED.getPortableBatteryCapacity());
		registry.PreRegisterItem(EnergizedBattery);
		LumumBattery = new BaseBattery("LumumPortableBattery", Tier.LUMUM.getPortableBatteryCapacity());
		registry.PreRegisterItem(LumumBattery);
		
		registry.PreRegisterItem(StaticBook = new StaticPowerBook("StaticBook"));	
				
		CreativeSpeedUpgrade = new BaseSpeedUpgrade("CreativeSpeedUpgrade", Tier.CREATIVE);
		registry.PreRegisterItem(CreativeSpeedUpgrade);	
		/**
		CreativeTankUpgrade = new BaseTankUpgrade("CreativeTankUpgrade", Tier.CREATIVE);
		registry.PreRegisterItem(CreativeTankUpgrade);	
		CreativeSpeedUpgrade = new BasePowerUpgrade("CreativeSpeedUpgrade", Tier.CREATIVE);
		registry.PreRegisterItem(CreativeSpeedUpgrade);
		*/
		
		registry.PreRegisterItem(TeleportUpgrade = new TeleportUpgrade("TeleportUpgrade"));
		registry.PreRegisterItem(ExperienceVacuumUpgrade = new ExperienceVacuumUpgrade("ExperienceVacuumUpgrade"));
		
		BasicSpeedUpgrade = new BaseSpeedUpgrade("BasicSpeedUpgrade", Tier.BASIC);
		registry.PreRegisterItem(BasicSpeedUpgrade);	
		StaticSpeedUpgrade = new BaseSpeedUpgrade("StaticSpeedUpgrade", Tier.STATIC);
		registry.PreRegisterItem(StaticSpeedUpgrade);	
		EnergizedSpeedUpgrade = new BaseSpeedUpgrade("EnergizedSpeedUpgrade", Tier.ENERGIZED);
		registry.PreRegisterItem(EnergizedSpeedUpgrade);	
		LumumSpeedUpgrade = new BaseSpeedUpgrade("LumumSpeedUpgrade", Tier.LUMUM);
		registry.PreRegisterItem(LumumSpeedUpgrade);	
		
		BasicTankUpgrade = new BaseTankUpgrade("BasicTankUpgrade", Tier.BASIC);
		registry.PreRegisterItem(BasicTankUpgrade);	
		StaticTankUpgrade = new BaseTankUpgrade("StaticTankUpgrade", Tier.STATIC);
		registry.PreRegisterItem(StaticTankUpgrade);	
		EnergizedTankUpgrade = new BaseTankUpgrade("EnergizedTankUpgrade", Tier.ENERGIZED);
		registry.PreRegisterItem(EnergizedTankUpgrade);	
		LumumTankUpgrade = new BaseTankUpgrade("LumumTankUpgrade", Tier.LUMUM);
		registry.PreRegisterItem(LumumTankUpgrade);	
		
		registry.PreRegisterItem(BasicPowerUpgrade = new BasePowerUpgrade("BasicPowerUpgrade", Tier.BASIC));
		registry.PreRegisterItem(StaticPowerUpgrade = new BasePowerUpgrade("StaticPowerUpgrade", Tier.STATIC));
		registry.PreRegisterItem(EnergizedPowerUpgrade = new BasePowerUpgrade("EnergizedPowerUpgrade", Tier.ENERGIZED));
		registry.PreRegisterItem(LumumPowerUpgrade = new BasePowerUpgrade("LumumPowerUpgrade", Tier.LUMUM));
		
		registry.PreRegisterItem(StaticQuarryingUpgrade = new BaseQuarryingUpgrade("StaticQuarryingUpgrade", Tier.STATIC));	
		registry.PreRegisterItem(EnergizedQuarryingUpgrade = new BaseQuarryingUpgrade("EnergizedQuarryingUpgrade", Tier.ENERGIZED));	
		registry.PreRegisterItem(LumumQuarryingUpgrade = new BaseQuarryingUpgrade("LumumQuarryingUpgrade", Tier.LUMUM));	

		registry.PreRegisterItem(BasicRangeUpgrade = new BaseRangeUpgrade("BasicRangeUpgrade", Tier.BASIC));	
		registry.PreRegisterItem(StaticRangeUpgrade = new BaseRangeUpgrade("StaticRangeUpgrade", Tier.STATIC));	
		registry.PreRegisterItem(EnergizedRangeUpgrade = new BaseRangeUpgrade("EnergizedRangeUpgrade", Tier.ENERGIZED));	
		registry.PreRegisterItem(LumumRangeUpgrade = new BaseRangeUpgrade("LumumRangeUpgrade", Tier.LUMUM));	
		
		registry.PreRegisterItem(BasicOutputMultiplierUpgrade = new BaseOutputMultiplierUpgrade("BasicOutputMultiplierUpgrade", Tier.BASIC));	
		registry.PreRegisterItem(StaticOutputMultiplierUpgrade = new BaseOutputMultiplierUpgrade("StaticOutputMultiplierUpgrade", Tier.STATIC));	
		registry.PreRegisterItem(EnergizedOutputMultiplierUpgrade = new BaseOutputMultiplierUpgrade("EnergizedOutputMultiplierUpgrade", Tier.ENERGIZED));	
		registry.PreRegisterItem(LumumOutputMultiplierUpgrade = new BaseOutputMultiplierUpgrade("LumumOutputMultiplierUpgrade", Tier.LUMUM));	
		
		registry.PreRegisterItem(DigistoreCapacityUpgrade = new BaseDigistoreCapacityUpgrade("DigistoreCapacityUpgrade"));
		registry.PreRegisterItem(DigistoreMiscUpgrade = new DigistoreMiscUpgrades("DigistoreMiscUpgrade"));
		
		BaseFluidCapsule = new BaseFluidCapsule("BaseFluidCapsule", Tier.BASIC.getFluidCanisterCapacity());
		registry.PreRegisterItem(BaseFluidCapsule);
		StaticFluidCapsule = new BaseFluidCapsule("StaticFluidCapsule", Tier.STATIC.getFluidCanisterCapacity());
		registry.PreRegisterItem(StaticFluidCapsule);
		EnergizedFluidCapsule = new BaseFluidCapsule("EnergizedFluidCapsule", Tier.ENERGIZED.getFluidCanisterCapacity());
		registry.PreRegisterItem(EnergizedFluidCapsule);
		LumumFluidCapsule = new BaseFluidCapsule("LumumFluidCapsule", Tier.LUMUM.getFluidCanisterCapacity());
		registry.PreRegisterItem(LumumFluidCapsule);
	}
}
