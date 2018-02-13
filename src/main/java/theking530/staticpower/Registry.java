package theking530.staticpower;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.blocks.BaseItemBlock;
import theking530.staticpower.blocks.IItemBlockProvider;
import theking530.staticpower.client.ItemRenderRegistry;
import theking530.staticpower.client.model.fluidcapsule.ModelFluidCapsule.LoaderFluidCapsule;
import theking530.staticpower.fluids.ModFluids;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class Registry {

	public List<Item> ITEMS = new LinkedList<>();
	public List<Block> BLOCKS = new LinkedList<>();

    public void preInit(FMLPreInitializationEvent e) {
    	 MinecraftForge.EVENT_BUS.register(this);
    	 //ModelLoaderRegistry.registerLoader(theking530.staticpower.client.model.fluidcapsule.ModelFluidCapsule.LoaderFluidCapsule.INSTANCE);
    }
    
    public void PreRegisterItem(Item item) {
    	ITEMS.add(item);
    }
    public void PreRegisterBlock(Block block) {
    	BLOCKS.add(block);
    	if(block instanceof IItemBlockProvider) {
    		IItemBlockProvider provider = (IItemBlockProvider)block;
    		if(provider.getItemBlock() != null) {
            	ITEMS.add(provider.getItemBlock());	
    		}
    	}else{
        	ITEMS.add(new BaseItemBlock(block, block.getUnlocalizedName()));	
    	}
    }
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onTextureStitchedPre(TextureStitchEvent.Pre e) {
    	LoaderFluidCapsule.INSTANCE.register(e.getMap());
    }
    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> e) {
    	BLOCKS.forEach(e.getRegistry()::register);
    }
    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> e) {
    	ITEMS.forEach(e.getRegistry()::register);
    }

    @SideOnly(Side.CLIENT)
	@SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {	
		OBJLoader.INSTANCE.addDomain(Reference.MOD_ID);
	    ItemRenderRegistry.initItemRenderers();
	    ModFluids.initBlockRendering();
	    ModFluids.initItemRendering();	    
    }
}
