package net.wynsolutions.bsc.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
public class PostgreSQLController {

	private String serverHost, serverUser, serverPass, database;
	private Connection connection;

	public PostgreSQLController(String host, String username, String password, String database) {
		this.serverHost = host;
		this.serverUser = username;
		this.serverPass = password;
		this.database = database;
		// Do a test connection to make sure the server can be connected to before continuing
		
		this.testConnection();
	}

	public boolean runStatement(final String database, final String statement){
		boolean flag = false;
		Debug.info("Running new statement to PostgreSQL server.");
		Debug.info("Starting connection to PostgreSQL server.");
		connect(database);
		try {
			Debug.info("Executing statement \"" + statement + "\".");
			Statement st = connection.createStatement();
			flag = st.execute(statement);
			st.close();
			close();
		} catch (SQLException e) {
			Debug.severe("Could not execute statement on PostgreSQL server \"" + serverHost + "\".");
			e.printStackTrace();
		}	
		return flag;
	}
	
	public int insertStatement(String database, String statement){
		int flag = 0;
		Debug.info("Running new statement to PostgreSQL server.");
		Debug.info("Starting connection to PostgreSQL server.");
		connect(database);
		try {
			Debug.info("Executing statement \"" + statement + "\".");
			Statement st = connection.createStatement();
			flag = st.executeUpdate(statement);
			st.close();
			close();
		} catch (SQLException e) {
			Debug.severe("Could not execute statement on PostgreSQL server \"" + serverHost + "\".");
			e.printStackTrace();
		}	
		return flag;	
	}
	
	public long largeUpdateStatement(String database, String statement){
		long flag = 0;
		Debug.info("Running new statement to PostgreSQL server.");
		Debug.info("Starting connection to PostgreSQL server.");
		connect(database);
		try {
			Debug.info("Executing statement \"" + statement + "\".");
			Statement st = connection.createStatement();
			flag = st.executeLargeUpdate(statement);
			st.close();
			close();
		} catch (SQLException e) {
			Debug.severe("Could not execute statement on PostgreSQL server \"" + serverHost + "\".");
			e.printStackTrace();
		}	
		return flag;	
	}
	
	public ResultSet queryStatement(String database, String statement){
		ResultSet flag = null;
		Debug.info("Running new statement to PostgreSQL server.");
		Debug.info("Starting connection to PostgreSQL server.");
		connect(database);
		try {
			Debug.info("Executing statement \"" + statement + "\".");
			Statement st = connection.createStatement();
			flag = st.executeQuery(statement);
			flag.close();
			st.close();
			close();
		} catch (SQLException e) {
			Debug.severe("Could not execute statement on PostgreSQL server \"" + serverHost + "\".");
			e.printStackTrace();
		}	
		
		return flag;
	}
	
	public int updateStatement(String database, String statement){
		int flag = 0;
		Debug.info("Running new statement to PostgreSQL server.");
		Debug.info("Starting connection to PostgreSQL server.");
		connect(database);
		try {
			Debug.info("Executing statement \"" + statement + "\".");
			Statement st = connection.createStatement();
			flag = st.executeUpdate(statement);
			st.close();
			close();
		} catch (SQLException e) {
			Debug.severe("Could not execute statement on PostgreSQL server \"" + serverHost + "\".");
			e.printStackTrace();
		}	
		
		return flag;
	}
	
	public int deleteStatement(String database, String statement){
		int flag = 0;
		Debug.info("Running new statement to PostgreSQL server.");
		Debug.info("Starting connection to PostgreSQL server.");
		connect(database);
		try {
			Debug.info("Executing statement \"" + statement + "\".");
			Statement st = connection.createStatement();
			flag = st.executeUpdate(statement);
			st.close();
			close();
		} catch (SQLException e) {
			Debug.severe("Could not execute statement on PostgreSQL server \"" + serverHost + "\".");
			e.printStackTrace();
		}	
		
		return flag;
	}

	private boolean connect(String database){
		try {
			Class.forName("org.postgresql.Driver");
			String host = "jdbc:postgresql://" + this.serverHost + "/" + this.database;
			Debug.info("Connecting to PostgreSQL server.");
			connection = DriverManager.getConnection(host, this.serverUser, this.serverPass);
		} catch (SQLException e) {
			Debug.severe("Could not connect to PostgreSQL server. Check your settings and your pSQL settings.");
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			Debug.severe("Could not find the Driver class for PostgreSQL. Package is \"org.postgresql.Driver\". Maybe the jar is missing?");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void close(){
		try {
			Debug.info("Closing connection to PostgreSQL server.");
			this.connection.close();
		} catch (SQLException e) {
			Debug.warn("Was unable to close connection to PostgreSQL(" + this.serverHost + ").");
		}
	}

	public boolean testTableExists(String database, String table){

		this.connect(database);
		boolean flag = false;
		try{
			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, table, new String[] {"TABLE"});
			while (rs.next()) {
				flag = true;
				break;
			}
		} catch (SQLException e) {
			System.out.println("Database Error: Error checking for database \"" + database + "\". \n" + e.getMessage());
		}
		return flag;
	}
	
	public boolean testDatabaseExists(String database, boolean flag){
		
		boolean flag3 = false;
		if(flag){
			flag3 = this.connect(database);
		}
		
		if(!flag3){
			return false;
		}
		
		boolean flag2 = false;
		try {
			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			while (rs.next()) {
				flag2 = true;
				break;
			}
			
			if(!flag2){
				// needs db
				int f = 0;
				Statement st = connection.createStatement();
				f = st.executeUpdate("CREATE DATABASE " + database + ";");
				st.close();
				
				
				if(f == 0){
					System.err.println("BungeeSpider-Client was unable to create a database with name " + database + ". Please do so.");
					flag2 = false;
				}
				
			}
			
		} catch (SQLException e) {
			flag2 = false;
			System.out.println("Database Error: Error checking for database \"" + database + "\". \n" + e.getMessage());
		}
		
		if(flag){
			this.close();
		}
		
		return flag2;
	}

	public boolean testConnection(){

		boolean flag = true;
		Debug.info("Testing connection to PostgreSQL Server \"" + this.serverHost + "\".");
		Debug.info("Starting connection to PostgreSQL server.");
		this.connect(this.database);

		// Check to see if server exists
		
		if(this.connection == null){
			Preconditions.checkArgument(this.connection != null, "PostgreSQL server \'" + this.serverHost + "\' did not respond to request.");
			flag = false;
		}else{
			Debug.info("Connection to \"" + this.serverHost + "\" was established.");
		}
		
		// Check to see if database name exists
		
		if(flag){
			Debug.info("Checking to make sure the database \"" + this.database + "\" exists.");
			if(this.testDatabaseExists(this.database, false)){
				Debug.info("Database \"" + this.database + "\" does exist!");
			}else{
				Debug.warn("Somehow you connected to a database that does not exist! I suggest you create it. Name: \"" + this.database + "\".");
			}
		}

		this.close();

		return flag;
	}

}
