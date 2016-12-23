package net.wynsolutions.bss.addons;

public class Addon {
	
	private AddonDescription description;
	
	public void init(AddonDescription desc){
		this.setDescription(desc);
	}
	
	public void onEnable(){

	}

	public void onLoad()  {
		
	}

	public void onDisable(){

	}

	public AddonDescription getDescription() {
		return description;
	}

	public void setDescription(AddonDescription description) {
		this.description = description;
	}

}
