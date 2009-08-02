package org.zenses.utils;

import javax.swing.JOptionPane;

import org.zenses.Zenses;

public class OSXHandler {
	protected Zenses _zenses;
	
	public OSXHandler(Zenses zenses) {
		this._zenses = zenses;
		
		try {
			OSXAdapter.setPreferencesHandler(this, this.getClass().getDeclaredMethod("handlePreferences", (Class[]) null));
			OSXAdapter.setQuitHandler(this, this.getClass().getDeclaredMethod("handleQuit", (Class[]) null));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public void handlePreferences()
    {
    	this._zenses.getViewHandler().showPreferencesView();
    }
	
    public boolean handleQuit() {
    	int option = JOptionPane.showConfirmDialog(
    			this._zenses.getViewHandler().getMainWindow(),
    			"Are you sure you want to quit Zenses?",
    			"Quit?",
    			JOptionPane.YES_NO_OPTION);
    	
    	return (option == JOptionPane.YES_OPTION);
    }
}
