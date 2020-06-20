package theking530.api.gui.widgets.tabs.redstonecontrol;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import theking530.staticpower.network.NetworkMessage;
import theking530.staticpower.tileentities.TileEntityBase;
import theking530.staticpower.tileentities.components.RedstoneControlComponent;
import theking530.staticpower.tileentities.utilities.RedstoneMode;

public class PacketRedstoneComponentSync extends NetworkMessage {
	private RedstoneMode redstoneMode;
	private BlockPos position;
	private String componentName;

	public PacketRedstoneComponentSync() {
	}

	public PacketRedstoneComponentSync(RedstoneMode mode, BlockPos pos, String componentName) {
		redstoneMode = mode;
		position = pos;
		this.componentName = componentName;
	}

	@Override
	public void decode(PacketBuffer buf) {
		redstoneMode = RedstoneMode.values()[buf.readInt()];
		position = buf.readBlockPos();
		componentName = buf.readString();
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeInt(redstoneMode.ordinal());
		buf.writeBlockPos(position);
		buf.writeString(componentName);
	}

	@Override
	public void handle(Supplier<Context> context) {
		TileEntity rawTileEntity = context.get().getSender().world.getTileEntity(position);

		if (rawTileEntity != null && rawTileEntity instanceof TileEntityBase) {
			TileEntityBase tileEntity = (TileEntityBase) rawTileEntity;

			// Ensure this tile entity is valid and has the requested component.
			if (tileEntity.hasComponentOfType(RedstoneControlComponent.class)) {
				// Get a reference to the redstone control component.
				RedstoneControlComponent component = tileEntity.getComponent(RedstoneControlComponent.class, componentName);

				// Set the mode.
				component.setRedstoneMode(redstoneMode);
			}
		}
	}
}