package it.unibz.model;

import com.enterprisedt.net.ftp.FTPFile;

public class MyFTPFile extends FTPFile
{

	public MyFTPFile(FTPFile file) {
		super(file.getName());
		this.type=file.isDir()?1:2;
		this.name=file.getName();
	}

	public void setType(int type) {
		this.type = type;
	}

	private int type = 1;

	public int getType() {
		return this.type;
	}
	
	

}
