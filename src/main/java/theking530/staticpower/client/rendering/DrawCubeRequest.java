package theking530.staticpower.client.rendering;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import theking530.staticcore.utilities.Color;

@OnlyIn(Dist.CLIENT)
public class DrawCubeRequest {
	public Vector3f Position;
	public Vector3f Scale;
	public Color Color;

	public DrawCubeRequest(Vector3f position, Vector3f scale, theking530.staticcore.utilities.Color color) {
		Position = position;
		Scale = scale;
		Color = color;
	}

	public DrawCubeRequest(BlockPos position, Vector3f scale, theking530.staticcore.utilities.Color color) {
		this(new Vector3f(position.getX(), position.getY(), position.getZ()), scale, color);
	}
}
