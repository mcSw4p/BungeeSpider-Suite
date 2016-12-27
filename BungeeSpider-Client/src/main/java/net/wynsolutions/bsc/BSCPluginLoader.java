package net.wynsolutions.bsc;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.wynsolutions.bsc.addons.AddonHandler;

public class BSCPluginLoader extends BSCPlugin{

	private int serverPort, serverTimeout;
	private String serverIP, serverName;
	private static boolean debug = false;
	
	public static BSCPluginLoader instance;
	private AddonHandler addonHandler;
	
	@Override public void onEnable() {

		instance = this;
		
		this.loadVars();

		new BSC(this);
		
		this.addonHandler = new AddonHandler(this);
		
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){

			public void run() {
				sendMessage("hello", serverName);
			}

		}, 0L, serverTimeout*20L);


		super.onEnable();
	}

	@Override public void onDisable() {
		this.sendMessage("shutdown", serverName);
		this.addonHandler.disableAddons();
		super.onDisable();
	}

	private void loadVars(){

		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		
		File f = new File(getDataFolder().getPath() + File.separatorChar + "addons");
		if(!f.exists()){
			f.mkdirs();
		}

		this.serverIP = this.getConfig().getString("server.ip");
		this.serverPort = this.getConfig().getInt("server.port");
		this.serverName = this.getConfig().getString("server.name");
		this.serverTimeout = this.getConfig().getInt("server.timeout");
		setDebug(this.getConfig().getBoolean("debug"));
	}

	@Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(label.equalsIgnoreCase("bsc")){

			if(args.length > 0){

				if(args[0].equalsIgnoreCase("update")){
					sender.sendMessage(ChatColor.GOLD + "Sending update ping to server.");

					if(this.sendMessage("Fhello", serverName)){
						sender.sendMessage(ChatColor.GREEN + "Server was updated.");
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

	public boolean sendMessage(String... str){
		try{
			Socket s = new Socket(serverIP, serverPort);

			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

			s.setSoTimeout(15*1000);
			
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
			return true;
		}catch(Exception ex){
			this.getLogger().warning("--------------");
			this.getLogger().warning("[BSC] Did not get response from the server! Check your settings or make sure the server is running.");
			this.getLogger().warning("--------------");
		}
		return false;
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

	public static boolean isDebug() {
		return debug;
	}

	public static void setDebug(boolean debug) {
		BSCPluginLoader.debug = debug;
	}

}
