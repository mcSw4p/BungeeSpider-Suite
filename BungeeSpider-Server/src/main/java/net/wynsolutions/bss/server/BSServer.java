package net.wynsolutions.bss.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import net.wynsolutions.bss.BSSLaunch;

public class BSServer extends Thread{

	private BSSLaunch plugin;
	private ServerSocket serverListener;
	public static List<String> tempUnrecognizedIps = new ArrayList<String>();

	public BSServer(BSSLaunch par1) {
		plugin = par1;
	}

	public void run() {
		this.startConnectionsServer();
	}

	public void startConnectionsServer(){

		System.out.println("[BSS] Starting Server Connection server.");

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
	
	public static void addUnrecognizedIp(final String ip){
		tempUnrecognizedIps.add(ip);
	}

}
