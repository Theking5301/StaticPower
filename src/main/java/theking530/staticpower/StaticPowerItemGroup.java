package theking530.staticpower;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistries;
import theking530.staticpower.init.ModItems;
import theking530.staticpower.items.cableattachments.CableCover;

public class StaticPowerItemGroup extends ItemGroup {
	private List<ItemStack> subTypes = null;

	public StaticPowerItemGroup() {
		super("StaticPower");
	}

	@OnlyIn(Dist.CLIENT)
	public ItemStack createIcon() {
		calculateSubTypes();
		return new ItemStack(ModItems.StaticCrop);
	}

	@Override
	public void fill(NonNullList<ItemStack> items) {
		this.calculateSubTypes();
		items.addAll(subTypes);
	}

	private void calculateSubTypes() {
		// If we have calculated the subtypes, do nothing.
		if (this.subTypes != null) {
			return;
		}

		// Preallocate a large array.
		this.subTypes = new ArrayList<>(1000);

		// Add all the covers.
		for (final Block block : ForgeRegistries.BLOCKS) {
			try {
				// Skip blocks with tile entities.
				if (!CableCover.isValidForCover(block)) {
					continue;
				}

				Item blockItem = block.asItem();
				if (blockItem != Items.AIR && blockItem.getGroup() != null) {
					final ItemStack facade = ModItems.CableCover.makeCoverForBlock(block.getDefaultState());
					if (!facade.isEmpty()) {
						this.subTypes.add(facade);
					}
				}
			} catch (final Throwable t) {
				System.out.println(t);
			}
		}

		// Add the electric items.
		subTypes.add(ModItems.BasicPortableBattery.getFilledVariant());
		subTypes.add(ModItems.StaticPortableBattery.getFilledVariant());
		subTypes.add(ModItems.EnergizedPortableBattery.getFilledVariant());
		subTypes.add(ModItems.LumumPortableBattery.getFilledVariant());
		subTypes.add(ModItems.ElectringSolderingIron.getFilledVariant());

		// Add all the capsules for all fluids.
		for (Fluid fluid : GameRegistry.findRegistry(Fluid.class)) {
			subTypes.add(ModItems.IronCapsule.getFilledVariant(fluid));
			subTypes.add(ModItems.BasicCapsule.getFilledVariant(fluid));
			subTypes.add(ModItems.AdvancedCapsule.getFilledVariant(fluid));
			subTypes.add(ModItems.StaticCapsule.getFilledVariant(fluid));
			subTypes.add(ModItems.EnergizedCapsule.getFilledVariant(fluid));
			subTypes.add(ModItems.LumumCapsule.getFilledVariant(fluid));
			subTypes.add(ModItems.CreativeCapsule.getFilledVariant(fluid));
		}
	}
}