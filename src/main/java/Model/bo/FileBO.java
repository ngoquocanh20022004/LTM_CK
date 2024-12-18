package Model.bo;

import java.util.ArrayList;

import Model.bean.ConvertedFile;
import Model.dao.FileDAO;

public class FileBO {
	public ArrayList<ConvertedFile> getAllConvertedFiles(int UserID) throws ClassNotFoundException {
		return new FileDAO().getAllConvertedFiles(UserID);
	}
	
	public void insertConvertedFile(ConvertedFile convertedFile) throws ClassNotFoundException {
		new FileDAO().insertConvertedFile(convertedFile);
	}
}
