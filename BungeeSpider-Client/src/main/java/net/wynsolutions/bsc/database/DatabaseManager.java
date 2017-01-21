package net.wynsolutions.bsc.database;

import java.sql.ResultSet;

import org.bukkit.configuration.file.FileConfiguration;

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
public class DatabaseManager {

	private PostgreSQLController psqlController;
	private MsSQLController sqlController;
	private MySQLController mySQLController;
	
	private boolean psql = false, sql = false, mysql = false, enabled =  true;
	//private FileConfiguration config;
	
	public DatabaseManager(FileConfiguration con) {
		//this.config = con;
		
		boolean taken = false;
		
		if(this.enabled = con.getBoolean("database.enabled")){
			if(con.getBoolean("database.postgresql.enable")){
				if(taken){
					System.out.println("[WRN] You have two or more database types selected. Using the first database type set.");		
				}else{
					taken = true;
					psql = true;
					// Enable postgresql controller
					this.psqlController = new PostgreSQLController(con.getString("database.postgresql.ip") + ":" + con.getInt("database.postgresql.port"), 
							con.getString("database.postgresql.user"), con.getString("database.postgresql.pass"), con.getString("database.postgresql.database"));
				}
			}
			
			if(con.getBoolean("database.sql.enable")){
				if(taken){
					System.out.println("[WRN] You have two or more database types selected. Using the first database type set.");
				}else{
					taken = true;
					sql = true;
					// Enable sql controller
					this.sqlController = new MsSQLController(con.getString("database.sql.ip") + ":" + con.getInt("database.sql.port"), 
							con.getString("database.sql.user"), con.getString("database.sql.pass"), con.getString("database.sql.database"));
				}
			}
			
			if(con.getBoolean("database.mysql.enable")){
				if(taken){	
					System.out.println("[WRN] You have two or more database types selected. Using the first database type set.");
				}else{
					taken = true;
					mysql = true;
					// Enable mysql controller
					this.mySQLController = new MySQLController(con.getString("database.mysql.ip") + ":" + con.getInt("database.mysql.port"), 
							con.getString("database.mysql.user"), con.getString("database.mysql.pass"), con.getString("database.mysql.database"));
				}
			}
		}
		
	}

	/**
	 * <p>Creates a statement in a database.</p>
	 * 
	 * <b>Note:</b><br> 
	 * This should always be ran Async as it will lag the main thread.
	 * <p></p>
	 * 
	 * @param database String
	 * @param statement String
	 * @return True if the first result is a ResultSet. False if it is an update count or there are no results
	 */
	public boolean createStatement(String database, String statement){	
		if(psql){
			return this.psqlController.runStatement(database, statement);
		}else if(sql){
			this.sqlController.runStatement(database, statement);
		}else if(mysql){
			this.mySQLController.runStatement(database, statement);
		}
		return false;
	}
	/**
	 * <p>Create a insert statement in a database.</p>
	 * 
	 * <b>Note:</b><br> 
	 * This should always be ran Async as it will lag the main thread.
	 * <p></p>
	 * 
	 * @param database
	 * @param statement
	 * @return Either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
	 */
	public int insertStatement(String database, String statement){
		if(psql){
			return this.psqlController.insertStatement(database, statement);
		}else if(sql){
			//this.sqlController.runStatement(database, statement);
		}else if(mysql){
			//this.mySQLController.runStatement(database, statement);
		}
		return 0;
	}

	/**
	 * <p>Create a large update statement in a database. This is used when the row count may exceed Integer.MAX_VALUE</p>
	 * 
	 * <b>Note:</b><br> 
	 * <ol><p>This method cannot be called on a PreparedStatement or CallableStatement. 
	 * The default implementation will throw UnsupportedOperationException</p></ol>
	 * 
	 * <ol>This should always be ran Async as it will lag the main thread.</ol>
	 * 
	 * @param database
	 * @param statement
	 * @return Either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
	 */
	public long largeUpdateStatement(String database, String statement){
		if(psql){
			return this.psqlController.largeUpdateStatement(database, statement);
		}else if(sql){
			//this.sqlController.runStatement(database, statement);
		}else if(mysql){
			//this.mySQLController.runStatement(database, statement);
		}
		return 0;
	}
	
	/**
	 * <p>Create a query statement in a database.</p>
	 * 
	 * <b>Note:</b><br>
	 * This should always be ran Async as it will lag the main thread.
	 * 
	 * <p></p>
	 * @param database
	 * @param statement
	 * @return A ResultSet.class with all of the results.
	 */
	public ResultSet queryStatement(String database, String statement){
		if(psql){
			return this.psqlController.queryStatement(database, statement);
		}else if(sql){
			//this.sqlController.runStatement(database, statement);
		}else if(mysql){
			//this.mySQLController.runStatement(database, statement);
		}
		return null;
	}
	
	/**
	 * <p>Create a update statement in a database.</p>
	 * 
	 * <b>Note:</b><br> 
	 * This should always be ran Async as it will lag the main thread.
	 * <p></p>
	 * 
	 * @param database
	 * @param statement
	 * @return Either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
	 */
	public long updateStatement(String database, String statement){
		if(psql){
			return this.psqlController.updateStatement(database, statement);
		}else if(sql){
			//this.sqlController.runStatement(database, statement);
		}else if(mysql){
			//this.mySQLController.runStatement(database, statement);
		}
		return 0;
	}
	
	/**
	 * <p>Create a delete statement in a database.</p>
	 * 
	 * <b>Note:</b><br> 
	 * This should always be ran Async as it will lag the main thread.
	 * <p></p>
	 * 
	 * @param database
	 * @param statement
	 * @return Either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
	 */
	public long deleteStatement(String database, String statement){
		if(psql){
			return this.psqlController.deleteStatement(database, statement);
		}else if(sql){
			//this.sqlController.runStatement(database, statement);
		}else if(mysql){
			//this.mySQLController.runStatement(database, statement);
		}
		return 0;
	}
	
	/**
	 * <p>Is the database enabled?</p>
	 * 
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean doesDatabaseExist(String database, boolean connect){
		if(psql){
			return this.psqlController.testDatabaseExists(database, connect);
		}else if(sql){
			//this.sqlController.runStatement(database, statement);
		}else if(mysql){
			//this.mySQLController.runStatement(database, statement);
		}
		return false;
	}
	
	public boolean doesTableExist(String database, String table){
		if(psql){
			return this.psqlController.testTableExists(database, table);
		}else if(sql){
			//this.sqlController.runStatement(database, statement);
		}else if(mysql){
			//this.mySQLController.runStatement(database, statement);
		}
		return false;
	}
	
}
