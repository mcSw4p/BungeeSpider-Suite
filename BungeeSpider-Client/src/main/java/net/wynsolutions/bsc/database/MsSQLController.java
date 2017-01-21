package net.wynsolutions.bsc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.common.base.Preconditions;

import net.wynsolutions.bsc.api.debug.Debug;

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
public class MsSQLController {
	
	private String serverHost, serverUser, serverPass, database;
	private Connection connection;
	
	public MsSQLController(String host, String username, String password, String database) {
		this.serverHost = host;
		this.serverUser = username;
		this.serverPass = password;
		this.database = database;
		
		// Do a test connection to make sure the server can be connected to before continuing
		this.testConnection();
	}
	
	public void runStatement(final String database, final String statement){
		Debug.info("Running new statement to SQL server.");
		Thread t = new Thread(new Runnable(){
			@Override public void run() {
				Debug.info("Starting connection to SQL server.");
				connect(database);
				try {
					Debug.info("Executing statement \"" + statement + "\".");
					Statement st = connection.createStatement();
					st.execute(statement);
					close();
				} catch (SQLException e) {
					Debug.severe("Could not execute statement on SQL server \"" + serverHost + "\".");
					e.printStackTrace();
				}
			}	
		});
		
		t.start();
	}
	
	private void connect(String database){
        try {
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        	String host = "jdbc:sqlserver://" + this.serverHost + "/" + database;
        	Debug.info("Connecting to SQL server.");
			connection = DriverManager.getConnection(host, this.serverUser, this.serverPass);
		} catch (SQLException e) {
			Debug.severe("Could not connect to SQL server. Check your settings and your SQL settings.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			Debug.severe("Could not find the Driver class for SQL. Package is \"com.microsoft.sqlserver.jdbc.SQLServerDriver\". Maybe the jar is missing?");
			e.printStackTrace();
		}
	}
	
	private void close(){
		try {
			Debug.info("Closing connection to SQL server.");
			this.connection.close();
		} catch (SQLException e) {
			Debug.warn("Was unable to close connection to SQL(" + this.serverHost + ").");
		}
	}
	
	public boolean testConnection(){
		
		boolean flag = true;
		Debug.info("Testing connection to SQL Server \"" + this.serverHost + "\".");
		Debug.info("Starting connection to SQL server.");
		this.connect(this.database);
		
		if(this.connection == null){
			Preconditions.checkArgument(this.connection != null, "SQL server \'" + this.serverHost + "\' did not respond to request.");
			flag = false;
		}else{
			Debug.info("Connection to \"" + this.serverHost + "\" was established.");
		}
		
		this.close();
		
		return flag;
	}
}
