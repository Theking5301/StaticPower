package theking530.staticpower.client;

import theking530.staticpower.handlers.ModEvents;

public class CommonProxy  {
	
	public static void preInit() {
	   // ForgeChunkManager.setForcedChunkLoadingCallback(StaticPower.instance, null);
		ModEvents.init();
	}
	public void registerProxies() {
		
	}
}
