package com.rest.test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.rest.Source;

public class Jdbc implements Source {

	private String url;
	private String username;
	private String password;
	private String driverClass = "com.mysql.jdbc.Driver";
	
	public Jdbc() {}
	
	public Jdbc(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public Jdbc(String url, String username, String password, String driverClass) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.driverClass = driverClass;
	}
	
	public Connection getConnection() throws SQLException
	{
		try
		{
			Class.forName(driverClass);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		return DriverManager.getConnection(url, username, password);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public void open() {
		
	}

	public void close() {
		
	}
	
	
}
