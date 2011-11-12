package it.unibz.util;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPConnectionManager {

	private FTPClient client;
private String user=null;
private String pass=null;
private String hostname=null;
private int port=21;

	public void doConnection(String user, String pass, String hostname,int port){
		this.user=user;
		this.pass=pass;
		this.hostname=hostname;
		this.port=port;
		client = new FTPClient();
		try {
			login();
//			if (login) {
//				System.out.println("Login success...");
//			} else {
//				System.out.println("Login fail...");
//			}
		}
		catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean login() throws SocketException, IOException {
		client.connect(this.hostname,this.port);
		return client.login(this.user, this.pass);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getFileList(String path){
		ArrayList files = new ArrayList();
		try {  
			if(!client.isConnected())
				login();
			FTPFile [] fl = client.listFiles(path!=null?path:"/");
			String t=null;
			for(FTPFile file:fl){
				t=file.getName().trim();
				if(!t.equals("..")&&!t.equals("."))
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
			if(!client.isConnected())
				login();
			renamed = client.rename(from, to);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return renamed;
	}

	public boolean deleteItem(String filename,boolean isfolder) {
		boolean deleted = false;
		try {
			if(!client.isConnected())
				login();
			if(isfolder)
			deleted=client.removeDirectory(filename);
				else
			deleted = client.deleteFile(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deleted;
	}

	public boolean makeDirectory(String dirname){
		boolean created = false;
		try {
			if(!client.isConnected())
				login();
				created=client.makeDirectory(dirname);
						} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return created;
	}
	
	public void removeConnection(){
		try {
			client.logout();
			client.disconnect();
			//System.out.println("Disconnected");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

