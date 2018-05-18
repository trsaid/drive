package drive.pojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JProgressBar;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.CopyStreamAdapter;

import drive.dao.uploadDAO;
import drive.main.Upload_panel;
import drive.main.home;

public class FTPUpload extends Thread {

	// private static int DELAY = 200;

	static JProgressBar progressBar;
	static int percent;

	private List<File> filesList;

	public FTPUpload(List<File> files) {
		this.filesList = files;
	}

	public void run() {
		String server = "192.168.0.11";
		int port = 21;
		String user = "said";
		String pass = "123456";
		int fileNum = 1;

		for (File file : filesList) {
			
			String fileName = file.toString();
			System.out.println(fileName);
			
			Upload_panel.progressBar_total.setMaximum(filesList.size());

			File MyFile = new File(fileName);
			final Integer innerfileNum = new Integer(fileNum);

			CopyStreamAdapter streamListener = new CopyStreamAdapter() {

				@Override
				public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {

					percent = (int) (totalBytesTransferred * 100 / MyFile.length());
					Upload_panel.progressBar_file.setValue(percent);
					Upload_panel.progressBar_total.setValue(innerfileNum);
					Upload_panel.lbl_progres_fichier_p.setText(percent + "%");
					Upload_panel.lbl_progres_fichier.setText("Fichier : " + file.getName());
					Upload_panel.lbl_progres_total.setText("Progression totale : " + innerfileNum + "/" + filesList.size());

				}

			};

			FTPClient ftpClient = new FTPClient();
			try {

				ftpClient.connect(server, port);
				ftpClient.login(user, pass);

				ftpClient.setCopyStreamListener(streamListener);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();

				/*
				 * Vérification de l'éxistance du dossier. Renvoi 550 si le dossier n'existe
				 * pas, sinon 250.
				 */
				int Exist_Error_code = ftpClient.cwd("upload");
				// System.out.println("code : " + Exist_Error_code);

				String FileName = MyFile.getName();
				InputStream inputStream = new FileInputStream(MyFile);

				// Si le dossier n'éxiste pas.
				if (Exist_Error_code == 550) {
					System.out.println("Dossier personnel inéxistant.");
					System.out.println("Création en cours...");
					ftpClient.makeDirectory("upload");
				}
				System.out.println("Transfert des fichiers en cours...");
				boolean completed = ftpClient.storeFile(FileName, inputStream);
				System.out.println("1" + completed);

				inputStream.close();

				if (completed) {
					System.out.println("Le fichier a été transféré.");
					uploadDAO.getInstance().uploadFile(MyFile, 1);
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
			fileNum++;
		}
	}
}
