package net.wynsolutions.bst.ticket;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Ticket {

	/*
	 * Server
	 */
	
	private int id;
	private UUID playerUUID;
	private String location, message, createdDate, claimedDate, closedDate;
	
	private HashMap<String, Date> actionDates = new HashMap<String, Date>();
	
	public Ticket(int parId, UUID uid, String cDate, String loc, String msg) {
		this.id = parId;
		this.playerUUID = uid;
		this.createdDate = cDate;
		this.location = loc;
		this.message = msg;
	}
	
	public Ticket(BufferedReader in){
		
		try {
			new Ticket(in.read(), UUID.fromString(in.readLine()), in.readLine(), in.readLine(), in.readLine());	
			this.readActionDates(in);
			this.sendToSheet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void readActionDates(BufferedReader in){
		try {
			while(in.ready()){
				String aDate = in.readLine();
				String[] split = aDate.split(":");
				switch(split[0]){
				case "cr":
					this.createdDate = split[1];
					break;
				case "cl":
					this.claimedDate = split[1];
					break;
				case "cd":
					this.closedDate = split[1];
					break;
				default:
					break;
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendToSheet(){
		
	}
	
	// Setters and Getters

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
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @return the claimedDate
	 */
	public String getClaimedDate() {
		return claimedDate;
	}

	/**
	 * @return the closedDate
	 */
	public String getClosedDate() {
		return closedDate;
	}

	/**
	 * @return the actionDates
	 */
	public HashMap<String, Date> getActionDates() {
		return actionDates;
	}
	
}
