package Model.bo;

import Model.bean.Account;
import Model.dao.AccountDAO;

public class AccountBO {
	AccountDAO accountDAO = new AccountDAO();
	public Account isValidUser(String username, String password) {
		return accountDAO.isValidUser(username, password);
	}
	
	public boolean addAccount(Account account) {
		return accountDAO.addAccount(account);
	}
}
