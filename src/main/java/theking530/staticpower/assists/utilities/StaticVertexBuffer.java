package theking530.staticpower.assists.utilities;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;

public class StaticVertexBuffer {

	public static final Tessellator tessellator = Tessellator.getInstance();
	public static final BufferBuilder vertexbuffer = tessellator.getBuffer();
    
	public static void pos(float x, float y, float z, float u, float v) {
		vertexbuffer.pos(x, y, z).tex(u, v).endVertex();
	}
	public static void pos(double x, double y, double z, double u, double v) {
		vertexbuffer.pos(x, y, z).tex(u, v).endVertex();
	}
}
