package theking530.staticpower.client;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public interface MeshDefinitionFix extends ItemMeshDefinition {
		
	ModelResourceLocation getLocation(ItemStack stack);

	// Helper method to easily create lambda instances of this class
	static ItemMeshDefinition create(MeshDefinitionFix lambda) {
		return lambda;
	}

	default ModelResourceLocation getModelLocation(ItemStack stack) {
		return getLocation(stack);
	}

}
