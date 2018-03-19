package drive.pojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.plaf.ProgressBarUI;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.CopyStreamAdapter;

import drive.main.home;

public class FTPUpload {
	public static void Upload(String file) {
		String server = "ip";
		int port = 21;
		String user = "user";
		String pass = "pass";
		
		File MyFile = new File(file);
		
		CopyStreamAdapter streamListener = new CopyStreamAdapter() {

		    @Override
		    public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {

		       int percent = (int)(totalBytesTransferred*100/MyFile.length());
		       System.out.println(percent +"%");

		    }

		 };

		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(server, port);
			ftpClient.login(user, pass);

			ftpClient.setCopyStreamListener(streamListener);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();

			String FileName = MyFile.getName();
			InputStream inputStream = new FileInputStream(FileName);
			ftpClient.storeFile(FileName, inputStream);
			

			System.out.println("Transfert des fichiers en cours...");
			OutputStream outputStream = ftpClient.storeFileStream("upload/" + FileName);
			byte[] bytesIn = new byte[4096];
			int read = 0;

			while ((read = inputStream.read(bytesIn)) != -1) {
				outputStream.write(bytesIn, 0, read);
			}
			
			inputStream.close();
			outputStream.close();

			boolean completed = ftpClient.completePendingCommand();
			if (completed) {
				System.out.println("Le fichier a été transféré.");
			}

		} catch (IOException ex) {
			System.out.println("Erreur: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}

