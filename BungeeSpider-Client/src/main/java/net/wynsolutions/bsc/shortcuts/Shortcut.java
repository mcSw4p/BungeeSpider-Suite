package net.wynsolutions.bsc.shortcuts;

public class Shortcut {

	private String permission, command, name;

	public Shortcut(String parName, String parPerm, String parCmd) {
		this.name = parName;
		this.permission = parPerm;
		this.command = parCmd;			
	}

	/**
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
}
