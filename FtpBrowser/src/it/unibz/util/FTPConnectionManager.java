package it.unibz.util;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPConnectionManager {

	private FTPClient client;


	public void doConnection(String user, String pass, String hostname,int port){
		client = new FTPClient();
		try {
			client.connect(hostname,port);
			boolean login = client.login(user, pass);
			if (login) {
				System.out.println("Login success...");
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

	public ArrayList getFileList(String path){
		ArrayList files = new ArrayList();
		try {  
			FTPFile [] fl = client.listFiles(path!=null?path:"/");
			for(FTPFile file:fl){
				files.add(file);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return files;
	}

	public boolean renameFile(String from, String to){
		boolean renamed = false;
		try {
			renamed = client.rename(from, to);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return renamed;
	}

	public boolean deleteFile(String filename) {
		boolean deleted = false;
		try {
			deleted = client.deleteFile(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deleted;
	}

	public void removeConnection(){
		try {
			client.logout();
			client.disconnect();
			System.out.println("Disconnected");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

