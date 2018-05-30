package drive.pojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.JProgressBar;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamAdapter;

import drive.dao.uploadDAO;
import drive.main.Upload_panel;

public class FTPUpload extends Thread {

	// private static int DELAY = 200;

	static JProgressBar progressBar;
	static int percent;

	static String server = "192.168.0.22";
	static int port = 21;
	static String user = "said";
	static String pass = "123456";

	private List<File> filesList;
	private Dossier dest;

	public FTPUpload(List<File> files, Dossier dossier) {
		filesList = files;
		dest = dossier;
	}

	public void run() {
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
					Upload_panel.lbl_progres_total
							.setText("Progression totale : " + innerfileNum + "/" + filesList.size());

				}

			};

			FTPClient ftpClient = loginFTP();
			try {

				ftpClient.setCopyStreamListener(streamListener);
				/*
				 * Vérification de l'éxistance du dossier. Renvoi 550 si le dossier n'existe
				 * pas, sinon 250.
				 */
				int Exist_Error_code = ftpClient.cwd(dest.getNom());
				// System.out.println("code : " + Exist_Error_code);
				

				String FileName = MyFile.getName();
				InputStream inputStream = new FileInputStream(MyFile);

				// Si le dossier n'éxiste pas.
				if (Exist_Error_code == 550) {
					System.out.println("Dossier personnel inéxistant.");
					System.out.println("Création en cours...");
					addDossier(dest.getNom(), ftpClient);
				}
				System.out.println("Transfert des fichiers en cours...");
				boolean completed = ftpClient.storeFile(FileName, inputStream);

				inputStream.close();

				if (completed) {
					System.out.println("Le fichier a été transféré.");
					uploadDAO.getInstance().uploadFile(MyFile, dest.getId());
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

	public static FTPClient loginFTP() {

		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(server, port);

            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("L'opération a échoué. Code de réponse du serveur: " + replyCode);
                return null;
            }
			boolean success = ftpClient.login(user, pass);

			if (!success) {
				System.out.println("Impossible de se connecter au serveur.");
				return null;
			}

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
		} catch (IOException ex) {
			System.out.println("Une erreur est survenue lors de la connexion au FTP.");
			ex.printStackTrace();
		}
		return ftpClient;

	}

	public static void addDossier(String name, FTPClient ftpClient) {
		boolean success = false;
		try {
			int Exist_Error_code = ftpClient.cwd(name);
			// Si le dossier n'éxiste pas.
			if (Exist_Error_code == 550) {
				success = ftpClient.makeDirectory(name);
					
				if (success) {
					System.out.println("Le dossier " + name + " a été créé.");
				} else {
					System.out.println("Une erreur s'est produite lors de la création du dossier.");
				}
			}else {
				System.out.println("Le dossier " + name + " est déjà éxistant.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
