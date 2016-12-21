package net.wynsolutions.bsc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class BSCPluginLoader extends BSCPlugin{

	private int serverPort, serverTimeout;
	private String serverIP, serverName;
	
	@Override public void onEnable() {
		
		this.loadVars();
		
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){

			public void run() {
				sendMessage("hello", serverName);
			}
			
		}, 0L, serverTimeout*20L);

		
		super.onEnable();
	}
	
	@Override public void onDisable() {
		this.sendMessage("shutdown", serverName);
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
					
					this.sendMessage("Fhello", serverName);
				}
				
				
			}else{
				//Menu
				sender.sendMessage(ChatColor.GOLD + "BungeeSpider-Client Commands:");
				sender.sendMessage(ChatColor.GREEN + "- /bsc update = Update this server to the main server.");	
			}
			
		}
		
		return super.onCommand(sender, command, label, args);
	}
	
	public void sendMessage(String... str){
		try{
		Socket s = new Socket(serverIP, serverPort);
		
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		 
		for(String s1 : str){
			out.println(s1);
		}
        
        String response = in.readLine();
        
        if(response == null){
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
