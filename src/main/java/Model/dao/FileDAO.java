package Model.dao;

import Model.bean.ConvertedFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FileDAO {
	public ArrayList<ConvertedFile> getAllConvertedFiles(int UserID) throws ClassNotFoundException {
		ArrayList<ConvertedFile> files = new ArrayList<ConvertedFile>();
		String query = "SELECT * FROM file WHERE UserID = '" + UserID + "'";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dulieultm", "root", "");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				ConvertedFile convertedFile = new ConvertedFile();
				convertedFile.setIDFile(rs.getInt("IDFile"));
				convertedFile.setNameFile(rs.getString("NameFile"));
				convertedFile.setUserID(rs.getInt("UserID"));
				files.add(convertedFile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return files;
	}
	
	public void insertConvertedFile(ConvertedFile convertedFile) throws ClassNotFoundException {
		String query = "INSERT INTO file (NameFile, UserID) VALUES ('" + convertedFile.getNameFile()
				+ "', " + convertedFile.getUserID() + ")";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dulieultm", "root", "");
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
