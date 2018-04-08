package drive.pojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JProgressBar;
import javax.swing.plaf.ProgressBarUI;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.CopyStreamAdapter;

import drive.main.home;

public class FTPUpload extends Thread {

private static int DELAY = 200;

	static JProgressBar progressBar;
	static int percent;

	public FTPUpload(JProgressBar bar) {
	  progressBar = bar;
	}

	public void run() {
//	  int minimum = progressBar.getMinimum();
//	  int maximum = progressBar.getMaximum();
//	  for (int i = minimum; i < maximum; i++) {
//	    try {
//	      int value = percent;
//	      progressBar.setValue(value);
//	
//	      Thread.sleep(DELAY);
//	    } catch (InterruptedException ignoredException) {
//	    }
//	  }
	}

	public static void Upload(String file) {
		String server = "192.168.0.11";
		int port = 21;
		String user = "said";
		String pass = "123456";
		
		File MyFile = new File(file);
		
		CopyStreamAdapter streamListener = new CopyStreamAdapter() {

		    @Override
		    public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {

		       percent = (int)(totalBytesTransferred*100/MyFile.length());
		       progressBar.setValue(percent);
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
			
			
			// Vérification de l'éxistance du dossier.
			// Renvoi 550 si le dossier n'existe pas, sinon 250.
			int Exist_Error_code = ftpClient.cwd("upload");
//			System.out.println("code : " + Exist_Error_code);

			String FileName = MyFile.getName();
			InputStream inputStream = new FileInputStream(MyFile);
			
			// Si le dossier n'éxiste pas.
			if(Exist_Error_code == 550) {
				System.out.println("Dossier personnel inéxistant.");
				System.out.println("Création en cours...");
				ftpClient.makeDirectory("upload");
			}
			System.out.println("Transfert des fichiers en cours...");
			boolean completed = ftpClient.storeFile(FileName, inputStream);

			inputStream.close();

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

