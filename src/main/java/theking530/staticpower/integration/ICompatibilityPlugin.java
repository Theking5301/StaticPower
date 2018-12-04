package theking530.staticpower.integration;

public interface ICompatibilityPlugin {

	public void register();
	
	public void preInit();
	public void init();
	public void postInit();
	
	public boolean shouldRegister();
	public boolean isRegistered();

	public String getPluginName();
}
