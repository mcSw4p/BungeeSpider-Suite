package net.wynsolutions.bst.ticket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;

import net.wynsolutions.bsc.BSC;
import net.wynsolutions.bst.BSTAddon;

public class Ticket {

	/*
	 * Client
	 */

	private int id;
	private UUID playerUUID;
	private Date createdDate;
	private String loaction, message;
	
	private HashMap<String, Date> actionDates = new HashMap<String, Date>();
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	
	public Ticket(UUID uid, Location loc, String msg) {
	
		this.id = BSTAddon.getNewId();
		this.playerUUID = uid;
		this.createdDate = new Date();
		this.loaction = loc.getWorld().getName() + "/" + loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ() + "/" + loc.getYaw() + "/" + loc.getPitch();
		this.message = msg;
		
	}
	
	public void addActionDate(String s){
		actionDates.put(s, new Date());
	}
	
	public boolean sendToServer(){
		try{
			Socket s = new Socket(BSC.getServerIp(), BSC.getServerPort());

			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

			s.setSoTimeout(15*1000);
			
			out.println("ticket");
			out.println(this.id);
			out.println(this.playerUUID.toString());
			out.println(this.format.format(this.createdDate));
			out.println(this.loaction);
			out.println(this.message);

			if(!this.actionDates.isEmpty()){
				this.sendActionDates(out, this.format);
			}
			
			String response = in.readLine();

			if(response == null){
				//Server must be unreachable
				System.out.println("[BST] --------------");
				System.out.println("[BST] Did not get response from the server! Check your settings or make sure the server is running.");
				System.out.println("[BST] --------------");
				s.close();
				return false;
			}

			s.close();
			return true;
		}catch(Exception ex){
			System.out.println("[BST] --------------");
			System.out.println("[BST] Did not get response from the server! Check your settings or make sure the server is running.");
			System.out.println("[BST] --------------");
			return false;
		}
	}
	
	public void sendActionDates(PrintWriter out, SimpleDateFormat format){
		for(String s : this.actionDates.keySet()){
			out.println(s + ":" + format.format(this.actionDates.get(s)));
		}
	}
	
	//Setters and Getters

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the playerUUID
	 */
	public UUID getPlayerUUID() {
		return playerUUID;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @return the loaction
	 */
	public String getLoaction() {
		return loaction;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the actionDates
	 */
	public HashMap<String, Date> getActionDates() {
		return actionDates;
	}
	
}
