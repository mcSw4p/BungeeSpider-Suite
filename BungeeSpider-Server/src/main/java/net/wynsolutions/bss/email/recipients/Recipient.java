package net.wynsolutions.bss.email.recipients;

public class Recipient {

	private String group, email;
	
	public Recipient(String par1, String par2) {
		
		this.group = par1;
		this.email = par2;
		
	}
	
	public String getGroup(){
		return this.group;
	}
	
	public String getEmail(){
		return this.email;
	}
	
}
