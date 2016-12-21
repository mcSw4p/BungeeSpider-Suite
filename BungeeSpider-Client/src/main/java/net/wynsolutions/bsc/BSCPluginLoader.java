package net.wynsolutions.bsc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.wynsolutions.bsc.server.BSCServer;

public class BSCPluginLoader extends BSCPlugin{

	private int serverPort, serverTimeout;
	private String serverIP, serverName;
	
	private BSCServer BSCSERVER;
	private static BSCPluginLoader instance;
	
	@Override public void onEnable() {
		
		this.loadVars();
		instance = this;
		
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){

			public void run() {
				BSCSERVER = new BSCServer(instance);
				BSCSERVER.start();
			}
			
		}, 0L, serverTimeout*20L);

		
		super.onEnable();
	}
	
	@Override public void onDisable() {
		try{
		Socket s = new Socket(serverIP, serverPort);
		
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		 
        out.println("shutdown");
        out.println(serverName);
        
        String response = in.readLine();
        
        if(response == null || !response.equalsIgnoreCase("shutdown")){
       	 //Server must be unreachable
       	 this.getLogger().warning("--------------");
       	 this.getLogger().warning("[BSC] Did not get response from the server! Check your settings or make sure the server is running.");
       	 this.getLogger().warning("--------------");
        }
        
        s.close();
		}catch(Exception ex){
			 this.getLogger().warning("--------------");
        	 this.getLogger().warning("[BSC] Did not get response from the server! Check your settings or make sure the server is running.");
        	 this.getLogger().warning("--------------");
		}
		super.onDisable();
	}
	
	private void loadVars(){
		
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		
		this.serverIP = this.getConfig().getString("server.ip");
		this.serverPort = this.getConfig().getInt("server.port");
		this.serverName = this.getConfig().getString("server.name");
		this.serverTimeout = this.getConfig().getInt("server.timeout");
	}
	
	@Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(label.equalsIgnoreCase("bsc")){
			
			if(args.length > 0){
				
				if(args[0].equalsIgnoreCase("update")){
					sender.sendMessage(ChatColor.GOLD + "Sending update ping to server.");
					
					try{
						Socket s = new Socket(serverIP, serverPort);
						
						PrintWriter out = new PrintWriter(s.getOutputStream(), true);
						BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
						 
				        out.println("Fhello");
				        out.println(serverName);
				        
				        String response = in.readLine();
				        
				        if(response == null || !response.equalsIgnoreCase("Fhello")){

				       	 //Server must be unreachable
				       	 this.getLogger().warning("--------------");
				       	 this.getLogger().warning("[BSC] Did not get response from the server! Check your settings or make sure the server is running.");
				       	 this.getLogger().warning("--------------");
				        }else{
				        	sender.sendMessage(ChatColor.GREEN + "Server was updated");
				        }
				        
				        s.close();
						}catch(Exception ex){
							 this.getLogger().warning("--------------");
				        	 this.getLogger().warning("[BSC] Did not get response from the server! Check your settings or make sure the server is running.");
				        	 this.getLogger().warning("--------------");
						}
				}
				
				
			}else{
				//Menu
				sender.sendMessage(ChatColor.GOLD + "BungeeSpider-Client Commands:");
				sender.sendMessage(ChatColor.GREEN + "- /bsc update = Update this server to the main server.");	
			}
			
		}
		
		return super.onCommand(sender, command, label, args);
	}
	
	public String getServerIP(){
		return serverIP;
	}
	
	public int getServerPort(){
		return serverPort;
	}
	
	public String getServerName(){
		return serverName;
	}
	
	public int getServerTimeout(){
		return serverTimeout;
	}
	
}
