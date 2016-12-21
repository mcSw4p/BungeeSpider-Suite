package net.wynsolutions.bss.server;

import java.io.IOException;
import java.net.ServerSocket;

import net.wynsolutions.bss.BSSPluginLoader;

public class BSServer extends Thread{

	private static BSSPluginLoader plugin;
	private ServerSocket serverListener;

	public BSServer(BSSPluginLoader par1) {
		plugin = par1;
	}

	public void run() {
		this.startConnectionsServer();
	}

	public void startConnectionsServer(){
		plugin.getProxy().getLogger().info("Starting Server Connection server.");
		
		try {
			serverListener = new ServerSocket(plugin.getServerPort());

			while(true){
				new BSServerInputHandler(serverListener.accept());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				serverListener.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		}

}
