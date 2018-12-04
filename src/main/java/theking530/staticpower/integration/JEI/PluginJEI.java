package theking530.staticpower.integration.JEI;

import javax.annotation.Nonnull;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.integration.JEI.centrifuge.CentrifugeRecipeCategory;
import theking530.staticpower.integration.JEI.condenser.CondenserRecipeCategory;
import theking530.staticpower.integration.JEI.cropsqueezer.CropSqueezerRecipeCategory;
import theking530.staticpower.integration.JEI.distillery.DistilleryRecipeCategory;
import theking530.staticpower.integration.JEI.esotericenchanter.EsotericEnchanterRecipeCategory;
import theking530.staticpower.integration.JEI.fermenter.FermenterRecipeCategory;
import theking530.staticpower.integration.JEI.fluidgenerator.FluidGeneratorRecipeCategory;
import theking530.staticpower.integration.JEI.fluidinfuser.FluidInfuserRecipeCategory;
import theking530.staticpower.integration.JEI.former.FormerRecipeCategory;
import theking530.staticpower.integration.JEI.fusionfurnace.FusionFurnaceRecipeCategory;
import theking530.staticpower.integration.JEI.grinder.PoweredGrinderRecipeCategory;
import theking530.staticpower.integration.JEI.lumbermill.LumberMillRecipeCategory;
import theking530.staticpower.integration.JEI.poweredfurnace.PoweredFurnaceRecipeCategory;
import theking530.staticpower.integration.JEI.solderingtable.SolderingTableRecipeCategory;

@JEIPlugin
public class PluginJEI implements IModPlugin{
	public static IJeiHelpers jeiHelpers;
	
	public static final String SOLDERING_TABLE_UID = Reference.MOD_ID + ":solderingTable";
	public static final String FLUID_INFUSER_UID = Reference.MOD_ID + ":fluidInfuser";
	public static final String POWERED_GRINDER_UID = Reference.MOD_ID + ":poweredGrinder";
	public static final String POWERED_FURNACE_UID = Reference.MOD_ID + ":poweredFurnace";
	public static final String FERMENTER_UID = Reference.MOD_ID + ":fermenter";
	public static final String SQUEEZER_UID = Reference.MOD_ID + ":squeezer";
	public static final String FORMER_UID = Reference.MOD_ID + ":former";
	public static final String ESOTERIC_ENCHANTER_UID = Reference.MOD_ID + ":esotericEnchanter";
	public static final String FUSION_FURNACE_UID = Reference.MOD_ID + ":fusionFurnace";
	public static final String FLUID_GENETATOR_UID = Reference.MOD_ID + ":fluidGenerator";
	public static final String CONDENSER_UID = Reference.MOD_ID + ":condenser";
	public static final String DISTILLERY_UID = Reference.MOD_ID + ":distillery";
	public static final String CENTRIFUGE_UID = Reference.MOD_ID + ":centrifuge";
	public static final String LUMBER_MILL_UID = Reference.MOD_ID + ":lumberMill";
	
	private static SolderingTableRecipeCategory solderingTable;
	private static FluidInfuserRecipeCategory fluidInfuser;
	private static PoweredGrinderRecipeCategory poweredGrinder;
	private static PoweredFurnaceRecipeCategory poweredFurnace;
	private static FermenterRecipeCategory fermenter;
	private static CropSqueezerRecipeCategory squeezer;
	private static FormerRecipeCategory former;
	private static EsotericEnchanterRecipeCategory enchanter;
	private static FusionFurnaceRecipeCategory fusionFurnace;
	private static FluidGeneratorRecipeCategory fluidGenerator;
	private static CondenserRecipeCategory condenser;
	private static DistilleryRecipeCategory distillery;
	private static CentrifugeRecipeCategory centrifuge;
	private static LumberMillRecipeCategory lumberMill;
	
    @Override
    public void registerCategories(@Nonnull IRecipeCategoryRegistration registry) {
    	jeiHelpers = registry.getJeiHelpers();
    	
    	solderingTable = new SolderingTableRecipeCategory(jeiHelpers.getGuiHelper());
    	fluidInfuser = new FluidInfuserRecipeCategory(jeiHelpers.getGuiHelper());
    	poweredGrinder = new PoweredGrinderRecipeCategory(jeiHelpers.getGuiHelper());
    	poweredFurnace = new PoweredFurnaceRecipeCategory(jeiHelpers.getGuiHelper());
    	fermenter = new FermenterRecipeCategory(jeiHelpers.getGuiHelper());
    	squeezer = new CropSqueezerRecipeCategory(jeiHelpers.getGuiHelper());
    	former = new FormerRecipeCategory(jeiHelpers.getGuiHelper());
    	enchanter = new EsotericEnchanterRecipeCategory(jeiHelpers.getGuiHelper());
    	fusionFurnace = new FusionFurnaceRecipeCategory(jeiHelpers.getGuiHelper());
    	fluidGenerator = new FluidGeneratorRecipeCategory(jeiHelpers.getGuiHelper()); 
    	condenser = new CondenserRecipeCategory(jeiHelpers.getGuiHelper()); 
    	distillery = new DistilleryRecipeCategory(jeiHelpers.getGuiHelper()); 
    	centrifuge = new CentrifugeRecipeCategory(jeiHelpers.getGuiHelper()); 
    	lumberMill = new LumberMillRecipeCategory(jeiHelpers.getGuiHelper()); 
    	
        registry.addRecipeCategories(solderingTable);
        registry.addRecipeCategories(fluidInfuser);
        registry.addRecipeCategories(poweredGrinder);
        registry.addRecipeCategories(poweredFurnace);
        registry.addRecipeCategories(fermenter);
        registry.addRecipeCategories(squeezer);
        registry.addRecipeCategories(former);
        registry.addRecipeCategories(enchanter);
        registry.addRecipeCategories(fusionFurnace);
        registry.addRecipeCategories(fluidGenerator);
        registry.addRecipeCategories(condenser);
        registry.addRecipeCategories(distillery);
        registry.addRecipeCategories(centrifuge);
        registry.addRecipeCategories(lumberMill);
    }

    @Override
    public void register(@Nonnull IModRegistry registry) {
    	solderingTable.initialize(registry);
    	fluidInfuser.initialize(registry);
    	poweredGrinder.initialize(registry);
    	poweredFurnace.initialize(registry);
    	fermenter.initialize(registry);
    	squeezer.initialize(registry);
    	former.initialize(registry);
    	enchanter.initialize(registry);
    	fusionFurnace.initialize(registry);
    	fluidGenerator.initialize(registry);
    	condenser.initialize(registry);
    	distillery.initialize(registry);
    	centrifuge.initialize(registry);
    	lumberMill.initialize(registry);
    	
        registry.addAdvancedGuiHandlers(new JEITabSlotAdjuster());
    }
}
