package it.unibz.util;

import java.io.IOException;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


public class FTPConnection {

	private static FTPClient client;

	public static void main(String[] args) {
		doConnection("ftpunibzteam","thehons88","ftp.alwaysdata.com");
		getFileList();
		removeConnection();
	}

	public static void doConnection(String user, String pass, String hostname){
		client = new FTPClient();
		try {
			client.connect(hostname);
			boolean login = client.login(user, pass);
			if (login) {
				System.out.println("Login success...");
				boolean logout = client.logout();
				if (logout) {
					System.out.println("Logout from FTP server...");
				}
			} else {
				System.out.println("Login fail...");
			}
		}
		catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getFileList(){
		FTPFile[] ftpFiles = null;
		try {
			ftpFiles = client.listFiles();
			for (FTPFile ftpFile : ftpFiles) {
				System.out.println("FTPFile: " + ftpFile.getName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean renameFile(String from, String to){
		boolean renamed = false;
		try {
			renamed = client.rename(from, to);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return renamed;
	}

	public static boolean deleteFile(String filename) {
		boolean deleted = false;
		try {
			deleted = client.deleteFile(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deleted;
	}

	public static void removeConnection(){
		try {
			//client.logout();
			client.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
