package Model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.bean.Account;

public class AccountDAO {
	public Account isValidUser(String username, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dulieultm", "root", "");
			Statement sm = conn.createStatement();
			String sql = "SELECT * FROM account";
			ResultSet rs = sm.executeQuery(sql);
			while(rs.next()) {
				if((username.equals(rs.getString(3))) && (password.equals(rs.getString(4)))) {
					Account account = new Account();
                    account.setID(rs.getInt(1));
                    account.setName(rs.getString(2));
                    account.setUsername(rs.getString(3));
                    account.setPassword(rs.getString(4));
                    return account;
				}
			}
			conn.close();
		} catch (Exception e)
		{
			System.out.print(e);
		}
		return null;
	}
	public boolean addAccount(Account newAccount) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dulieultm", "root", "");
			Statement sm = conn.createStatement();
			String sql = "SELECT * FROM account";
			ResultSet rs = sm.executeQuery(sql);

			int nextId = 1;
			String name = newAccount.getUsername();
			while(rs.next()) {
				if (name.equals(rs.getString(3))) return false;
				nextId++;
			}			
			newAccount.setID(nextId);
			
			String insertQuery = "INSERT INTO account (ID, Name, Username, Password) VALUES (?, ?, ?, ?)";
			PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
			insertStmt.setInt(1, newAccount.getID());
			insertStmt.setString(2, newAccount.getName());
			insertStmt.setString(3, newAccount.getUsername());
			insertStmt.setString(4, newAccount.getPassword());
			int rows = insertStmt.executeUpdate();
			if (rows > 0) {
				return true;
			}
			
			conn.close();
			sm.close();
			insertStmt.close();
		} catch (Exception e)
		{
			System.out.print(e);
		}
		return false;
	}
}