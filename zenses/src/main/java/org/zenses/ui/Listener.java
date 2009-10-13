package org.zenses.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.zenses.Zenses;
import org.zenses.ZensesApplication;

public class Listener implements ActionListener {
	private Zenses _zenses;
	private ViewHandler _viewHandler;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ListenerActions action = ListenerActions.valueOf(e.getActionCommand());
		
		this._zenses = Zenses.getInstance();
		this._viewHandler = ViewHandler.getInstance();
		
		switch (action) {
		case FIND_DEVICES:
			ZensesApplication.getApplication().getZenses().getViewHandler().findDevices();
			break;
		case UPDATE_CHECK:
			if (!Zenses.getInstance().updateAvailable()) {
				String title = "You're Up-to-date!";
				String message = "Zenses " + Zenses.version + " is currently the latest version available.";

				JOptionPane.showMessageDialog(ViewHandler.getInstance().getMainWindow(), message, title, JOptionPane.PLAIN_MESSAGE);
			}
			break;
		case PREFERENCES:
			break;
		case EXIT:
			break;
		case ABOUT:
			break;
		case ABOUT_FORUMS:
			break;
		case ABOUT_SITE:
			break;
		case ABOUT_DEV_BLOG1:
			break;
		default:
			break;
		}
	}
}
