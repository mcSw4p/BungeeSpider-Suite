package net.wynsolutions.bsc;

public class BSC {

	private static BSCPluginLoader plug;
	
	public BSC(BSCPluginLoader par1) {
		plug = par1;
	}
	
	public static void sendMessage(String... str){
		plug.sendMessage(str);
	}
	
}
