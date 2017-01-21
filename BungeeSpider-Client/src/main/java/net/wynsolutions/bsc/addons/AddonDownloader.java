package net.wynsolutions.bsc.addons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.common.base.Preconditions;

import net.wynsolutions.bsc.api.BSC;
import net.wynsolutions.bsc.api.debug.Debug;
import net.wynsolutions.bsc.config.Configuration;
import net.wynsolutions.bsc.config.ConfigurationProvider;
import net.wynsolutions.bsc.config.YamlConfiguration;
/**
 * Copyright (C) 2017  Sw4p
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Sw4p
 *
 */
public class AddonDownloader {

	/*
	 * Create a way to use a file to load a preset of addons.
	 * For instance have a file with a list for direct urls and have the class load each url from that file and download the addon.
	 * 
	 * This will allow easy installation and fast deployment on server instances
	 */

	private URL pathToAddon;
	private File addonPath, addonTempDir, addonTempFile;
	private Addon addon;
	private AddonDescription desc;
	//private boolean createBackups = true; - Used to create updates to addons
	private boolean failed = false;

	private final int BUFFER_SIZE = 4096;

	public AddonDownloader(File listURL){

		Debug.info("Starting new addon download from list in file \"" + listURL.getPath() + "\".");
		
		List<String> lines;
		try {
			lines = Files.readAllLines(listURL.toPath(), Charset.forName("UTF-8"));
			for(String url : lines){
				
				if(url.startsWith("http://") && url.endsWith(".jar")){
					
					new AddonDownloader(url).startInstallation();
					
				}else{
					Debug.info("Skipping line \"" + url + "\" from file because it does not seem to be a addon.");
				}
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public AddonDownloader(String fileURL) {
		this.addonTempDir = new File(BSC.getServerProperties().getDataFolder().getPath() + File.separatorChar + "addons" + File.separatorChar + "tmp");
		try{
			this.pathToAddon = new URL(fileURL);
			Debug.info("Starting new URL connection to download addon. (" + fileURL + ")");
			HttpURLConnection httpConn = (HttpURLConnection) this.pathToAddon.openConnection();
			int responseCode = httpConn.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) { 
				Debug.info("Writing jar file to temp directory.");
				InputStream inputStream = httpConn.getInputStream();
				this.addonTempFile = new File(this.addonTempDir.getPath() + File.separatorChar + "temp.jar");
				if(!this.addonTempDir.exists()){
					this.addonTempDir.mkdirs();
				}

				FileOutputStream outputStream = new FileOutputStream(this.addonTempFile.getPath());

				int bytesRead = -1;
				byte[] buffer = new byte[BUFFER_SIZE];
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

				outputStream.close();
				inputStream.close();
				Debug.info("Finished writing addon to tmp directory.");
			} else {
				System.out.println("Server could not find a file at location (" + fileURL + "). Server replied HTTP code: " + responseCode);
			}
			httpConn.disconnect();
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Unable to download addon.");
		}

		if(!this.addonTempFile.exists()){
			try {
				this.addonTempFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Debug.info("Digging into jar file to find spider.yml.");
		try (JarFile jar = new JarFile(this.addonTempFile)) {
			JarEntry pdf = jar.getJarEntry("spider.yml");
			Preconditions.checkNotNull(pdf, "Addon must have a spider.yml");
			Debug.info("Found it!");
			try (InputStream in = jar.getInputStream(pdf)){
				Configuration con = ConfigurationProvider.getProvider(YamlConfiguration.class).load(in);
				this.desc = new AddonDescription();
				this.loadAddonDescriptionFile(con, desc);
				Debug.info("Loading description file.");
			}
		} catch (Exception ex){
			System.out.println("Could not load addon from URL(" + fileURL + "). Maybe it is not a addon?");
			failed = true;
		}

	}

	private AddonDescription loadAddonDescriptionFile(Configuration con, AddonDescription aDesc){

		Preconditions.checkArgument(con.contains("name"), "Could not load addon\'s name.");
		aDesc.setName(con.getString("name"));

		Preconditions.checkArgument(con.contains("main"), "Could not load addon\'s main class.");
		aDesc.setMain(con.getString("main"));

		Preconditions.checkArgument(con.contains("version"), "Could not load addon\'s version.");
		aDesc.setVersion(con.getString("version"));

		Preconditions.checkArgument(con.contains("author"), "Could not load addon\'s author.");
		aDesc.setAuthor(con.getString("author"));

		if(con.contains("description"))
			aDesc.setDescription(con.getString("description"));

		if(con.contains("client"))
			aDesc.setClient(con.getString("client"));
		else{
			Preconditions.checkArgument(con.contains("client"), "Addon(" + aDesc.getName() + ") is not a client addon or does not have the client tag in spider.yml");
		}

		return aDesc;
	}

	public boolean startInstallation(){

		Debug.info("Starting installation of addon.");

		if(failed){
			this.addonTempFile.delete();
			Debug.info("Download must have failed so i\'m deleting the temp file.");
			return false;
		}

		String msg = "Installing addon " + this.desc.getName() + " v" + this.desc.getVersion() + " by " + this.desc.getAuthor();
		if(BSC.getHandler().addonExists(this.desc.getName())){

			System.out.println("Server already has this addon installed.(" + this.desc.getName() + ")");
			this.addonTempFile.delete();
			return false;

			/*
			 * Currently runs into issues with process use. Need to find a way to cleanly unload the jar file from the plugin.
			 * 
			this.addon = BSS.getHandler().getAddon(this.desc.getName());
			BSS.getHandler().disableAddon(addon);
			if(addon.getDescription().getVersion().equalsIgnoreCase(this.desc.getVersion())){
				msg = "Reinstalling addon " + this.desc.getName() + " v" + this.desc.getVersion() + " by " + this.desc.getAuthor();
				System.out.println(msg);
				return this.loadAddon(createBackups);
			}else{
				msg = "Updating addon " + this.desc.getName() + " v" + this.desc.getVersion() + " by " + this.desc.getAuthor() + " from v" + 
				addon.getDescription().getVersion();
				System.out.println(msg);
				return this.loadAddon(createBackups);
			}
			 */


		}else{
			System.out.println(msg);
			return this.loadAddon(false);
		}
	}

	private boolean loadAddon(boolean backup){

		this.addonPath = new File(BSC.getServerProperties().getDataFolder().getPath() + File.separatorChar + "addons" + File.separatorChar + this.desc.getName() + ".jar");

		if(backup){
			File a = this.addon.getDescription().getFile();
			File backupsDir = new File(this.addonTempDir.getPath() + File.separatorChar + this.desc.getName() + "_v" + this.desc.getVersion() + ".jar");

			try {
				Files.move(a.toPath(), backupsDir.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		Debug.info("Moving temp file to correct location and changing name.");
		try {
			Files.move(this.addonTempFile.toPath(), this.addonPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Debug.info("Finished.");
		this.desc.setFile(this.addonPath);

		System.out.println("Finished installing addon " + this.desc.getName() + " v" + this.desc.getVersion() + " by " + this.desc.getAuthor());
		Debug.info("Enabling new addon.");
		BSC.getHandler().enableAddon(this.desc);
		return true;
	}

}
