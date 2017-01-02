package net.wynsolutions.bss;

public class BSS {

	private static BSSLaunch plug;
	
	public BSS(BSSLaunch par1) {
		plug = par1;
	}
	
	/**
	 * 
	 * @return The BungeeSpider Server port as set in the BungeeSpider-Client settings file.
	 */
	public static int getServerPort(){
		return plug.getServerPort();
	}
	
	/**
	 * 
	 * @return The Server timeout
	 */
	public static int getServerTimeout(){
		return plug.getServerTimeout();
	}
	
}
