package it.betacom.singleton;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBHandler {
	
	Properties connProp;
	private Connection con = null;
	private static DBHandler instance = null;
	private String db;
//	private String db ="jdbc:mysql://localhost:3306/";
//	private String utente = "root";
//	private String pw = "betacom1";
	
	private DBHandler() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver caricato correttamente");
		} catch (ClassNotFoundException e) {
			System.out.println("Non è stato possibile caricare il Driver");
			e.printStackTrace();
		}
		connProp = new Properties();
		try (InputStream is = DBHandler.class.getClassLoader().getResourceAsStream("config.properties")){
			connProp.load(is);
			db = connProp.getProperty("db.url")+connProp.getProperty("db.name");
			connProp.put("user", connProp.getProperty("db.user"));
			connProp.put("password", connProp.getProperty("db.password"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static DBHandler getInstance() {
		if (instance == null ) 
			instance = new DBHandler();
		return instance;
	}
	public Connection getConnection() {
		try {
			//con = DriverManager.getConnection(db, utente, pw);
			con = DriverManager.getConnection(db, connProp);
			System.out.println("Connessione avvenuta");
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void closeConnection() {
		try {
			if (con != null) {
				con.close();
				System.out.println("Connessione chiusa");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
