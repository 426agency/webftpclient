package it.unibz.util;


import it.unibz.model.MyFTPFile;
import java.io.IOException;
import java.util.ArrayList;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.FileTransferClient;

public class FTPConnectionManager {

	private FileTransferClient client = null;
private String user=null;
private String pass=null;
private String hostname=null;
private int port=21;
private String connectionname=null;

	public FTPConnectionManager(String conname){
		this.setConnectionname(conname);
	}

	public void doConnection(String user, String pass, String hostname,int port) throws Exception{
		this.user=user;
		this.pass=pass;
		this.hostname=hostname;
		this.port=port;
		client = new FileTransferClient();
		
    // set remote host
		client.setRemoteHost(hostname);
		client.setUserName(user);
		client.setPassword(pass);
		client.setRemotePort(port);
    // connect to the server

			login();


	}

	private void login() throws FTPException, IOException {
		client.connect();

	}

	public boolean downloadFile(String currentFolder,String filename,String temppath){

		try {
			if(!client.isConnected())
				login();
		
	     

         client.downloadFile(temppath+filename, currentFolder+"/" +filename);
        
         

     } catch (Exception e) {
        return false;
     }
        return true; 

	}
	
	public boolean uploadFile(String currentFolder,String filename,String localFile){

		/*try {
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
			return false;
		}
	        return true; */
		
		try {
			if(!client.isConnected())
				login();
		
	     

         client.uploadFile(localFile, currentFolder+"/" +filename);
        
         

     } catch (Exception e) {
        return false;
     }
        return true; 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getFileList(String path){
		ArrayList files = new ArrayList();
		try {  
			if(!client.isConnected())
				login();
			FTPFile [] fl = client.directoryList(path!=null?path:"/");
			String t=null;
			for(FTPFile file:fl){
				t=file.getName().trim();
				if(!t.equals("..")&&!t.equals("."))
				files.add(new MyFTPFile(file));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return files;
	}

	public boolean renameFile(String from, String to){
		boolean renamed = false;
		try {
			if(!client.isConnected())
				login();
			client.rename(from, to);
			renamed=true;
		} catch (Exception e) {
			renamed=false;
		}
		return renamed;
	}

	public boolean deleteItem(String filename,boolean isfolder) {
		boolean deleted = false;
		try {
			if(!client.isConnected())
				login();
			if(isfolder)
			client.deleteDirectory(filename);
				else
			client.deleteFile(filename);
			deleted=true;
		} catch (Exception e) {
		deleted=false;
		}
		return deleted;
	}

	public boolean makeDirectory(String dirname){
		boolean created = false;
		try {
			if(!client.isConnected())
				login();
				client.createDirectory(dirname);
				created=true;
						} catch (Exception e) {
							//invoke ourselves
							created=false;
		}
		return created;
	}
	
	public void removeConnection(){
		try {
			if(client!=null){
			client.disconnect();
			}
			//System.out.println("Disconnected");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	public boolean renameFileOrDir(String currentfolder, String oldname, String newname) {
		boolean renamed = false;
		try {
			if(!client.isConnected())
				login();
			client.rename(currentfolder+"/"+oldname,currentfolder+"/"+newname);
			renamed=true;
						} catch (Exception e) {
							//invoke ourselves
							renamed=false;	}
		return renamed;
	}

	public String getConnectionname() {
		return connectionname;
	}

	public void setConnectionname(String connectionname) {
		this.connectionname = connectionname;
	}
}

