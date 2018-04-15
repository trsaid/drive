package drive.pojo;

import drive.main.Upload_panel;

public class Progress_Update extends Thread {
	private static final int DELAY = 200;

	@Override
	public void run() {
		super.run();
		try {
			Upload_panel.progressBar_total.setMaximum(Upload_panel.upload_file_size);
			Upload_panel.progressBar_total.setValue(Upload_panel.fileNumber);
			Upload_panel.lbl_progres_fichier.setText("Fichier : " + Upload_panel.fileName);
			Upload_panel.lbl_progres_total.setText("Progression totale : " + Upload_panel.fileNumber + "/" + Upload_panel.upload_file_size);
			Thread.sleep(DELAY);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
