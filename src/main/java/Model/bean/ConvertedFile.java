package Model.bean;

public class ConvertedFile {
	private int IDFile;
	private String NameFile;
	private int UserID;
	
	public ConvertedFile() {
		super();
	}
	public ConvertedFile(int iDFile, String nameFile, int userID) {
		super();
		IDFile = iDFile;
		NameFile = nameFile;
		UserID = userID;
	}
	public int getIDFile() {
		return IDFile;
	}
	public void setIDFile(int iDFile) {
		IDFile = iDFile;
	}
	public String getNameFile() {
		return NameFile;
	}
	public void setNameFile(String nameFile) {
		NameFile = nameFile;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	
}
