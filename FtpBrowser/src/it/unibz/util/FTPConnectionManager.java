package it.unibz.util;


import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.jibble.simpleftp.SimpleFTP;

public class FTPConnectionManager {

	private FTPClient client;
private String user=null;
private String pass=null;
private String hostname=null;
private int port=21;
private String connectionname=null;

	public FTPConnectionManager(String conname){
		this.setConnectionname(conname);
	}

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
		//client.connect(hostname)
		try {
			client.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return client.login(this.user, this.pass);
	}

	public boolean downloadFile(String currentFolder,String filename,String temppath) throws SocketException, IOException{

		InputStream fis = null;

        try {
        	//this is indeed not working. A bug
    		if(!client.isConnected())
    			login();
    		
            //
            // The remote filename to be downloaded.
            //
            fis = client.retrieveFileStream(currentFolder+"/" +filename);
            
            File f = new File(temppath+filename);
           // new FileOutputStream(temppath+filename);

            //
            // Download file from FTP server
            //
            	DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
    			int c;
    			while((c = fis.read()) != -1) {
    				out.writeByte(c);
    			}
    			fis.close();
    			out.close();
    		
        } catch (IOException e) {
            //Invoke yourself to reconnect
        	login();
        	downloadFile(currentFolder, filename, temppath);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true; 

	}
	
	public boolean uploadFile(String currentFolder,String filename,InputStream localFile) throws SocketException, IOException{

		try {
			//Using external library for avoiding Bug in Apache's FTPClient which
			//dows not allow binary upload on windows ftp servers
			
		    SimpleFTP ftp = new SimpleFTP();
		    // ReConnect to FTP server
		    ftp.connect(hostname, port, user, pass);
		    // Set binary mode.
		    ftp.bin();
		    // Change to a new working directory on the FTP server.
		    ftp.cwd(currentFolder);
		    // Upload the file
		    ftp.stor(localFile,filename);
		    // Quit from the FTP server.
		    ftp.disconnect();
		}
		catch (IOException e) {
			//invoke ourselfs
			login();
			uploadFile(currentFolder, filename, localFile);
		}
	        return true; 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getFileList(String path) throws SocketException, IOException{
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
			login();
			return getFileList(path);
		}
		
		return files;
	}

	public boolean renameFile(String from, String to) throws SocketException, IOException{
		boolean renamed = false;
		try {
			if(!client.isConnected())
				login();
			renamed = client.rename(from, to);
		} catch (IOException e) {
			//invoke ourselves
			login();
			renameFile(from, to);
		}
		return renamed;
	}

	public boolean deleteItem(String filename,boolean isfolder) throws SocketException, IOException {
		boolean deleted = false;
		try {
			if(!client.isConnected())
				login();
			if(isfolder)
			deleted=client.removeDirectory(filename);
				else
			deleted = client.deleteFile(filename);
		} catch (IOException e) {
			//invoke ourselves
			login();
			deleteItem(filename, isfolder);
		}
		return deleted;
	}

	public boolean makeDirectory(String dirname) throws SocketException, IOException{
		boolean created = false;
		try {
			if(!client.isConnected())
				login();
				created=client.makeDirectory(dirname);
						} catch (IOException e) {
							//invoke ourselves
							login();
							makeDirectory(dirname);
		}
		return created;
	}
	
	public void removeConnection(){
		try {
			if(client!=null){
			client.logout();
			client.disconnect();}
			//System.out.println("Disconnected");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	public boolean renameFileOrDir(String currentfolder, String oldname, String newname) throws SocketException, IOException {
		boolean renamed = false;
		try {
			if(!client.isConnected())
				login();
			renamed=client.rename(currentfolder+"/"+oldname,currentfolder+"/"+newname);
						} catch (IOException e) {
							//invoke ourselves
							login();
renameFileOrDir(currentfolder, oldname, newname);		}
		return renamed;
	}

	public String getConnectionname() {
		return connectionname;
	}

	public void setConnectionname(String connectionname) {
		this.connectionname = connectionname;
	}
}

