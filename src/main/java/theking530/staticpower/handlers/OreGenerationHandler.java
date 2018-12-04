package theking530.staticpower.handlers;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;
import theking530.staticpower.StaticPowerConfig;
import theking530.staticpower.blocks.ModBlocks;

public class OreGenerationHandler implements IWorldGenerator {
	
    @Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    	switch (world.provider.getDimension()) {
    		case 0: generateSurface(world, random, chunkX, chunkZ);
    			break;
    	}
    }
    private void generateSurface(World world, Random rand, int chunkX, int chunkZ) {
        for (int k = 0; k < 16; k++) {
            if (StaticPowerConfig.COPPER_ORE_GEN) {
                generateOre(ModBlocks.CopperOre, world, rand, chunkX, chunkZ,2,6,4,30,90);
            }
            if (StaticPowerConfig.TIN_ORE_GEN) {
                generateOre(ModBlocks.TinOre, world, rand, chunkX, chunkZ,2,6,4,30,90);
            }
            if (StaticPowerConfig.LEAD_ORE_GEN) {
                generateOre(ModBlocks.LeadOre, world, rand, chunkX, chunkZ,1,5,3,0,32);
            }
            if (StaticPowerConfig.SILVER_ORE_GEN) {
                generateOre(ModBlocks.SilverOre, world, rand, chunkX, chunkZ,1,5,4,0,32);
            }
            if (StaticPowerConfig.PLATINUM_ORE_GEN) {
                generateOre(ModBlocks.PlatinumOre, world, rand, chunkX, chunkZ,2,4,2,0,30);
            }
            if (StaticPowerConfig.NICKEL_ORE_GEN) {
                generateOre(ModBlocks.NickelOre, world, rand, chunkX, chunkZ,1,3,3,0,40);
            }
            if (StaticPowerConfig.ALUMINIUM_ORE_GEN) {
                generateOre(ModBlocks.AluminiumOre, world, rand, chunkX, chunkZ,1,5,4,30,90);
            }
            if (StaticPowerConfig.SAPPHIRE_ORE_GEN) {
                generateOre(ModBlocks.SapphireOre, world, rand, chunkX, chunkZ,1,3,2,0,40);
            }
            if (StaticPowerConfig.RUBY_ORE_GEN) {
                generateOre(ModBlocks.RubyOre, world, rand, chunkX, chunkZ,1,4,2,0,40);
            }
        }
    }
    public static void intialize(){
    	GameRegistry.registerWorldGenerator(new OreGenerationHandler(), 0);
    }
	public void generateOre(Block block, World world, Random random, int chunkX, int chunkZ, int minVienSize, int maxVienSize, int chance, int minY, int maxY) {
		int vienSize = minVienSize + random.nextInt(maxVienSize - minVienSize);
		int heightRange = maxY - minY;
		WorldGenMinable gen = new WorldGenMinable(block.getDefaultState(), vienSize);
		for(int i = 0; i < chance; i++) {
			int xRand = chunkX * 16 + random.nextInt(16);
			int yRand = random.nextInt(heightRange) + minY;
			int zRand = chunkZ * 16 + random.nextInt(16);
			gen.generate(world, random, new BlockPos(xRand, yRand, zRand));
		}
	}
}
