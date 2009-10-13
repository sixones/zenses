package org.zenses;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import org.zenses.utils.OSXHandler;

/**
 * Zenses GUI Application
 * 
 * Requires the zen-scrobbler lib for obvious reasons
 * 
 * @author sixones
 *
 */
public class ZensesApplication extends SingleFrameApplication
{
    private Zenses _zenses;
    
    protected OSXHandler _osxHandler;

    protected void startup() {    	
        try {
			this._zenses = new Zenses();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (this.getZenses().isMacOSX()) {
			this._osxHandler = new OSXHandler(this.getZenses());
		}

        this.getZenses().getViewHandler().showMainWindow();
    }

    public Zenses getZenses() {
    	return this._zenses;
    }
    
    protected void configureWindow(java.awt.Window root) {
    	
    }

    public static ZensesApplication getApplication() {
        return (ZensesApplication) Application.getInstance(ZensesApplication.class);
    }
}