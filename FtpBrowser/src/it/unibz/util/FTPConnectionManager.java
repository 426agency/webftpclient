package it.unibz.util;

import it.unibz.model.MyFTPFileBean;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.FileTransferClient;

/**
 * Class is responsible to interact woth the remote FTP Servers Provides basic
 * functionality such as Add/Remove/Edit Files and folders and upload and
 * download of files
 */
public class FTPConnectionManager
{

	private FileTransferClient client = null;

	private String user = null;

	private String pass = null;

	private String hostname = null;

	private int port = 21;

	private String connectionname = null;

	public FTPConnectionManager(String conname) {
		this.setConnectionname(conname);
	}

	/**
	 * Method creates the connection to a specific FTP Server
	 * 
	 * @param user
	 *          Username for the connection
	 * @param pass
	 *          Password for the connection
	 * @param hostname
	 *          Host to connect to
	 * @param port
	 *          Port to use
	 * @throws Exception
	 *           if connection cannot be established
	 */
	public void doConnection(String user, String pass, String hostname, int port)
			throws Exception {
		this.user = user;
		this.pass = pass;
		this.hostname = hostname;
		this.port = port;
		client = new FileTransferClient();

		// set remote host
		client.setRemoteHost(hostname);
		client.setUserName(user);
		client.setPassword(pass);
		client.setRemotePort(port);
		// connect to the server

		login();

	}

	/**
	 * Method tries to log in into the current Server
	 * 
	 * @throws FTPException
	 * @throws IOException
	 */
	private void login() throws FTPException, IOException {
		client.connect();

	}

	/**
	 * MEthod downloads file from the Server into a temporary folder on the
	 * application Server
	 * 
	 * @param currentFolder
	 *          Remote folder to switch to
	 * @param filename
	 *          File to download
	 * @param temppath
	 *          Temporary folder to store downloaded file
	 * @return True if succeeded
	 */
	public boolean downloadFile(String currentFolder, String filename,
			String temppath) {

		try {
			if (!client.isConnected())
				login();

			client.downloadFile(temppath + filename, currentFolder + "/" + filename);

		} catch (Exception e) {
			return false;
		}
		return true;

	}

	/**
	 * Method uploads a file to the current FTP Server
	 * 
	 * @param currentFolder
	 *          Remote folder to switch to
	 * @param filename
	 *          File to download
	 * @param localFile
	 *          is the local file to upload
	 * @return True if succeeded
	 */
	public boolean uploadFile(String currentFolder, String filename,
			String localFile) {

		try {
			if (!client.isConnected())
				login();

			client.uploadFile(localFile, currentFolder + "/" + filename);

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Method return the list of files and folders ordered by name indipendently
	 * if folder/file. Each item will be instance of MyFTPFile to have additional
	 * JSON Property Filetype
	 * 
	 * @param path
	 *          Remote folder path
	 * @return True if succeeded
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getFileList(String path) {
		ArrayList files = new ArrayList();
		try {
			if (!client.isConnected())
				login();
			FTPFile[] fl = client.directoryList(path != null ? path : "/");
			String t = null;
			for (FTPFile file : fl) {
				t = file.getName().trim();
				// DO not return linux move folders
				if (!t.equals("..") && !t.equals("."))
					files.add(new MyFTPFileBean(file));
			}
		}
		catch (FTPException e) {
			//invoke ourselfes once again
			try {
				login();
			} catch (FTPException e1) {
			} catch (IOException e1) {
			}
			return getFileList(path);
		} catch (IOException e) {
		//invoke ourselfes once again
			try {
				client.disconnect();
				client.connect();
			} catch (FTPException e1) {
			} catch (IOException e1) {
			}
			return getFileList(path);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return files;
	}

	/**
	 * Method deleted a remote file or folder (if empty)
	 * 
	 * @param filename
	 *          file to remove
	 * @param isfolder
	 *          flag for filetype
	 * @return True if succeeded
	 */
	public boolean deleteItem(String filename, boolean isfolder) {
		boolean deleted = false;
		try {
			if (!client.isConnected())
				login();
			if (isfolder)
				client.deleteDirectory(filename);
			else
				client.deleteFile(filename);
			deleted = true;
		} catch (Exception e) {
			deleted = false;
		}
		return deleted;
	}

	/**
	 * MEthod creates a remote Directory
	 * 
	 * @param dirname
	 *          New directory's name with path
	 * @return True if succeeded
	 */
	public boolean makeDirectory(String dirname) {
		boolean created = false;
		try {
			if (!client.isConnected())
				login();
			client.createDirectory(dirname);
			created = true;
		} catch (Exception e) {
			created = false;
		}
		return created;
	}

	/**
	 * Method disconnects from current connection
	 */
	public void removeConnection() {
		try {
			if (client != null) {
				client.disconnect();
			}
			// System.out.println("Disconnected");
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	/**
	 * Method renames a remote file/Folder
	 * 
	 * @param oldname
	 *          Old filename
	 * @param newname
	 *          New filename
	 * @param currentfolder
	 *          folder to browse to remotely
	 * @return True if succeeded
	 */
	public boolean renameFileOrDir(String currentfolder, String oldname,
			String newname) {
		boolean renamed = false;
		try {
			if (!client.isConnected())
				login();
			client.rename(currentfolder + "/" + oldname, currentfolder + "/"
					+ newname);
			renamed = true;
		} catch (Exception e) {
			// invoke ourselves
			renamed = false;
		}
		return renamed;
	}

	public String getConnectionname() {
		return connectionname;
	}

	public void setConnectionname(String connectionname) {
		this.connectionname = connectionname;
	}
}
