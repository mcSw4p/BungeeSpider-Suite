package net.wynsolutions.bsc.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import net.wynsolutions.bsc.BSCPluginLoader;

public class BSCServer extends Thread{

	private BSCPluginLoader plugin;

	public BSCServer(BSCPluginLoader par1) {
		this.plugin = par1;
	}
	
	public void run(){
		this.startConnectionServer();
	}

	public void startConnectionServer() {
		try{
		Socket s = new Socket(plugin.getServerIP(), plugin.getServerPort());
		
		 PrintWriter out = new PrintWriter(s.getOutputStream(), true);
		 BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		 
         out.println("hello");
         out.println(plugin.getServerName());
         
         String response = in.readLine();
         
         if(response == null || !response.equalsIgnoreCase("hello")){
        	 //Server must be unreachable
        	 this.plugin.getLogger().warning("--------------");
        	 this.plugin.getLogger().warning("[BSC] Did not get response from the server! Check your settings or make sure the server is running.");
        	 this.plugin.getLogger().warning("--------------");
         }
         
         s.close();
		}catch(IOException e){
			 this.plugin.getLogger().warning("--------------");
        	 this.plugin.getLogger().warning("[BSC] Did not get response from the server! Check your settings or make sure the server is running.");
        	 this.plugin.getLogger().warning("--------------");
		}
	}

}
