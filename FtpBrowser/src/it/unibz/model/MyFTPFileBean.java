package it.unibz.model;

import com.enterprisedt.net.ftp.FTPFile;

/**
 * Class describes a FTP File
 * 
 * DERIVED from com.enterprisedt.net.ftp.FTPFile to add JSON Property fileType
 * 
 * Simple structure with private members and public access Methods
 * 
 */
public class MyFTPFileBean extends FTPFile
{

	/**
	 * Modified constructor to set property immediately after initialization
	 * @param file the object to clone
	 */
	public MyFTPFileBean(FTPFile file) {
		super(file.getName());
		this.type = file.isDir() ? 1 : 2;
		this.name = file.getName();
	}

	public void setType(int type) {
		this.type = type;
	}

	private int type = 1;

	public int getType() {
		return this.type;
	}

}
