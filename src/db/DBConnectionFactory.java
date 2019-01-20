package db;

import db.mysql.MySQLConnection;

public class DBConnectionFactory {
	// This should change based on the pipeline.
	private static final String DEFAULT_DB = "mysql";
	
	public static DBConnection getConnection(String db) {
		switch (db) {
		case "mysql":
			 return new MySQLConnection();

		case "mongodb":
			// return new MongoDBConnection();
			return null;
		default:
			throw new IllegalArgumentException("Invalid db:" + db);
		}

	}

	public static DBConnection getConnection() {//不传参数时默认return mysql
		return getConnection(DEFAULT_DB);
	}
}

//DBConnection connection = DBConnectionFactory.getConnection();
//DBConnection connection = new MySQLConnection();