package theking530.staticpower.energy;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.tileentity.ISideConfigurable;

public class PowerDistributor {

	private TileEntity poweredTileEntity;
	private ISideConfigurable sideConfigurable;
	private IEnergyStorage energyStorage;
	
	public PowerDistributor(TileEntity tileEntity, IEnergyStorage energyStorage) {
		poweredTileEntity = tileEntity;
		this.energyStorage = energyStorage;
		if(poweredTileEntity instanceof ISideConfigurable) {
			sideConfigurable = (ISideConfigurable)poweredTileEntity;
		}
	}
	public void distributePower() {
		if(energyStorage != null && poweredTileEntity != null && !poweredTileEntity.isInvalid()) { 
			if(energyStorage.getEnergyStored() > 0) {
				for(EnumFacing facing : EnumFacing.values()) {
					if(sideConfigurable == null || (sideConfigurable != null && sideConfigurable.getSideConfiguration(facing) == Mode.Output)) {
						int maxExtract = energyStorage.extractEnergy(Integer.MAX_VALUE, true);
						provideRF(facing, Math.min(maxExtract, energyStorage.getEnergyStored()));
					}
				}	
			}	
		}
	}
	public int provideRF(EnumFacing facing, int amount) {
		return provideRF(poweredTileEntity.getPos().offset(facing), facing, amount);
	}
	/**
	 * @param pos - The position of the block to send energy to.
	 * @param facing - The side of the block we are sending energy to.
	 * @param amount - The amount of energy to send.
	 * @return - The actual amount of energy that was sent.
	 */
	public int provideRF(BlockPos pos, EnumFacing facing, int amount) {
		IEnergyStorage handler = getEnergyHandlerPosition(pos, facing.getOpposite());

		if(handler != null) {
			if(handler.getEnergyStored() < handler.getMaxEnergyStored() && handler.canReceive()) {
				int provided = handler.receiveEnergy(amount, false);
				energyStorage.extractEnergy(provided, false);
				return provided;
			}
		}
		return 0;
	}
	public IEnergyStorage getEnergyHandlerPosition(BlockPos pos, EnumFacing facing) {
		TileEntity te = poweredTileEntity.getWorld().getTileEntity(pos);

		if (te != null && te.hasCapability(CapabilityEnergy.ENERGY, facing)) {					
			IEnergyStorage  handler = te.getCapability(CapabilityEnergy.ENERGY, facing);//access side must be opposite
			return handler;
		}
		return null;
	}
}
