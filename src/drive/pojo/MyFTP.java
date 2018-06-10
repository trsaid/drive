package drive.pojo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamAdapter;

import drive.dao.dossierDAO;
import drive.dao.uploadDAO;
import drive.main.Home_panel;
import drive.main.Main;
import drive.main.home;

public class MyFTP extends Thread {

	// private static int DELAY = 200;

	static JProgressBar progressBar;
	static int percent;

	static String server = "192.168.30.2";
	static int port = 21;
	static String user = "Cloud";
	static String pass = "123456";

	private List<File> filesList;
	private Dossier dest;

	public MyFTP(List<File> files, Dossier dossier) {
		filesList = files;
		dest = dossier;
	}

	public void run() {
		int fileNum = 1;
		String msg = "";
		for (File file : filesList) {

			String fileName = file.toString();
			System.out.println(fileName);

			home.progressBar_total.setMaximum(filesList.size());

			File MyFile = new File(fileName);
			final Integer innerfileNum = new Integer(fileNum);

			CopyStreamAdapter streamListener = new CopyStreamAdapter() {

				@Override
				public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {

					percent = (int) (totalBytesTransferred * 100 / MyFile.length());
					home.progressBar_file.setValue(percent);
					home.progressBar_total.setValue(innerfileNum);
					home.lbl_progres_fichier_p.setText(percent + "%");
					home.lbl_progres_fichier.setText("Fichier : " + file.getName());
					home.lbl_progres_total.setText("Progression totale : " + innerfileNum + "/" + filesList.size());
					System.out.println(percent);

				}

			};

			FTPClient ftpClient = loginFTP();
			try {

				ftpClient.setCopyStreamListener(streamListener);
				/*
				 * Vérification de l'éxistance du dossier. Renvoi 550 si le dossier n'existe
				 * pas, sinon 250.
				 */
				String destination = Main.getUser_logged().getId() + "/" + dest.getNom();
				int Exist_Error_code = ftpClient.cwd(destination);
				// System.out.println("code : " + Exist_Error_code);

				String FileName = MyFile.getName();
				InputStream inputStream = new FileInputStream(MyFile);

				// Si le dossier n'éxiste pas.
				if (Exist_Error_code == 550) {
					msg = "Dossier personnel inéxistant. Création en cours...";
					addDossier(destination, ftpClient);
				}
				msg = "Transfert des fichiers en cours...";
				boolean completed = ftpClient.storeFile(FileName, inputStream);

				inputStream.close();

				if (completed) {
					msg = "Le fichier " + file.getName() + " a été transféré.";
					uploadDAO.getInstance().uploadFile(MyFile, dest.getId());
				}

			} catch (IOException ex) {
				msg = "Erreur: " + ex.getMessage();
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
			Home_panel.addLog(msg);
		}
	}

	public static FTPClient loginFTP() {

		FTPClient ftpClient = new FTPClient();
		String msg = "";
		try {

			ftpClient.connect(server, port);

			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				msg = "L'opération a échoué. Code de réponse du serveur: " + replyCode;
				return null;
			}
			boolean success = ftpClient.login(user, pass);

			if (!success) {
				msg = "Impossible de se connecter au serveur.";
				return null;
			}

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
		} catch (IOException ex) {
			System.out.println("Une erreur est survenue lors de la connexion au FTP.");
			ex.printStackTrace();
		}
		if (!msg.isEmpty())
			JOptionPane.showMessageDialog(null, msg);
		Home_panel.addLog(msg);
		return ftpClient;

	}

	public static boolean dirExist(String name) {
		FTPClient ftpClient = loginFTP();

		int Exist_Error_code = 0;
		try {
			Exist_Error_code = ftpClient.cwd(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Si le dossier n'éxiste pas.
		if (Exist_Error_code == 550)
			return false;
		else
			return true;
	}

	public static void addDossier(String name, FTPClient ftpClient) {
		boolean success = false;
		String msg = "";
		try {
			// Si le dossier n'éxiste pas.
			String userid = String.valueOf(Main.getUser_logged().getId());
			if (!dirExist(userid))
				success = ftpClient.makeDirectory(userid);

			String destination = userid + "/" + name;
			if (!dirExist(destination)) {
				success = ftpClient.makeDirectory(destination);

				if (success) {
					msg = "Le dossier " + name + " a été créé.";
				} else {
					msg = "Une erreur s'est produite lors de la création du dossier.";
				}
			} else {
				msg = "Le dossier " + name + " est déjà éxistant.";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!msg.isEmpty())
			JOptionPane.showMessageDialog(null, msg);
		Home_panel.addLog(msg);
	}

	public static void download(Dossier dossier, Fichier fichier) {
		FTPClient ftpClient = loginFTP();

		String file = Main.getUser_logged().getId() + "/" + dossier.getNom() + "/" + fichier.getNom();
		File Path = new File(Main.getDlPath().toString() + "/" + dossier.getNom());
		File direction = new File(Path + "/" + fichier.getNom());
		
		Fonction.dirCreat(Path);

		String msg = "";

		try {
			OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(direction));
			InputStream inputStream = ftpClient.retrieveFileStream(file);
			byte[] bytesArray = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(bytesArray)) != -1) {
				outputStream2.write(bytesArray, 0, bytesRead);
			}

			boolean success = ftpClient.completePendingCommand();
			if (success) {
				msg = fichier.getNom() + " a été téléchargé.";
			}

			outputStream2.close();
			inputStream.close();
		} catch (IOException ex) {
			msg = "Erreur lors du téléchargement : " + ex.getMessage();
			ex.printStackTrace();
		}
		Home_panel.addLog(msg);
	}

	public static void rename(Dossier dossier, String name) {
		FTPClient ftpClient = loginFTP();
		String dirName = dossier.getNom();

		String msg = "";

		if (!dirExist(name)) {
			boolean success = false;
			try {
				success = ftpClient.rename(dirName, name);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (success) {
				dossierDAO.getInstance().renameFolder(dossier, name);
				msg = "Le dossier " + dirName + " a été renommé en " + name + ".";
			} else {
				msg = "Le dossier " + dirName + " n'a pas pu être rennomé.";
			}
		} else {
			msg = "Un dossier portant le nom " + name + " existe déjà!";
		}
		if (!msg.isEmpty())
			JOptionPane.showMessageDialog(null, msg);
		Home_panel.addLog(msg);
	}

	public static void delete(Dossier dossier) {
		FTPClient ftpClient = loginFTP();
		String dirName = dossier.getNom();
		String userid = String.valueOf(Main.getUser_logged().getId());
		String dir = userid + "/" + dirName;

		String msg = "";

		try {
			FTPFile[] subFiles = ftpClient.listFiles(dir);
			if (subFiles != null && subFiles.length > 0) {
				for (FTPFile aFile : subFiles) {
					String currentFileName = aFile.getName();
					if (currentFileName.equals(".") || currentFileName.equals("..")) {
						continue;
					}
					String filePath = dir + "/" + currentFileName;

					// Suppression du fichier
					boolean deleted = ftpClient.deleteFile(filePath);
					if (deleted) {

						msg = "Suppression du fichier : " + filePath;
					} else {
						msg = "Impossible de supprimer le fichier " + filePath;
					}
				}

				// Suppression du dossier
			}
			boolean removed = ftpClient.removeDirectory(dir);
			if (removed) {
				msg = "Suppression du dossier : " + dirName;
				dossierDAO.getInstance().delete(dossier);
			} else {
				msg = "Impossible de supprimer le dossier : " + dirName;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Home_panel.addLog(msg);
	}
}
