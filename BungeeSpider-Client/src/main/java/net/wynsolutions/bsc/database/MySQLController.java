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
public class MySQLController {
	private String serverHost, serverUser, serverPass, database;
	private Connection connection;
	
	public MySQLController(String host, String username, String password, String database) {
		this.serverHost = host;
		this.serverUser = username;
		this.serverPass = password;
		this.database = database;
		// Do a test connection to make sure the server can be connected to before continuing
		
		this.testConnection();
	}
	
	public void runStatement(final String database, final String statement){
		Debug.info("Running new statement to MySQL server.");
		Thread t = new Thread(new Runnable(){
			@Override public void run() {
				Debug.info("Starting connection to MySQL server.");
				connect(database);
				try {
					Debug.info("Executing statement \"" + statement + "\".");
					Statement st = connection.createStatement();
					st.execute(statement);
					close();
				} catch (SQLException e) {
					Debug.severe("Could not execute statement on MySQL server \"" + serverHost + "\".");
					e.printStackTrace();
				}
			}	
		});
		
		t.start();
	}
	
	private void connect(String database){
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        	String host = "jdbc:mysql://" + this.serverHost + "/" + database;
        	Debug.info("Connecting to MySQL server.");
			connection = DriverManager.getConnection(host, this.serverUser, this.serverPass);
		} catch (SQLException e) {
			Debug.severe("Could not connect to MySQL server. Check your settings and your MySQL settings.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			Debug.severe("Could not find the Driver class for MySQL. Package is \"com.mysql.jdbc.Driver\". Maybe the jar is missing?");
			e.printStackTrace();
		}
	}
	
	private void close(){
		try {
			Debug.info("Closing connection to MySQL server.");
			this.connection.close();
		} catch (SQLException e) {
			Debug.warn("Was unable to close connection to MySQL(" + this.serverHost + ").");
		}
	}
	
	public boolean testConnection(){
		
		boolean flag = true;
		Debug.info("Testing connection to MySQL Server \"" + this.serverHost + "\".");
		Debug.info("Starting connection to MySQL server.");
		this.connect(this.database);
		
		if(this.connection == null){
			Preconditions.checkArgument(this.connection != null, "MySQL server \'" + this.serverHost + "\' did not respond to request.");
			flag = false;
		}else{
			Debug.info("Connection to \"" + this.serverHost + "\" was established.");
		}
		
		this.close();
		
		return flag;
	}
	
}
