package net.wynsolutions.bsc;

public class BSC {

	private static BSCPluginLoader plug;
	
	public BSC(BSCPluginLoader par1) {
		plug = par1;
	}
	
	/**
	 * Sends @param to the server as commands to be handled by the input handlers.
	 * @param str
	 */
	public static void sendMessage(String... str){
		plug.sendMessage(str);
	}
	
	/**
	 * Returns the BungeeSpider Server ip
	 * @return
	 */
	public static String getServerIp(){
		return plug.getServerIP();
	}
	
	/**
	 * Returns the BungeeSpider Server port
	 * @return
	 */
	public static int getServerPort(){
		return plug.getServerPort();
	}
	
}
