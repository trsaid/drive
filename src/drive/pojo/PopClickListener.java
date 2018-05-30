package drive.pojo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import drive.main.Popup;

public class PopClickListener extends MouseAdapter {
	
	Dossier dossier;
	
	public PopClickListener(Dossier dossier) {
		this.dossier = dossier;
	}
	
    public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
    	Popup menu = new Popup(dossier);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
