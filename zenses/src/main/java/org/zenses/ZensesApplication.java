package org.zenses;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
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
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            Logger.getLogger(ZensesApplication.class.getName()).log(Level.SEVERE, null, e);
        }
        
        this._zenses = new Zenses();

        if (this.getZenses().isMacOSX()) {
            System.setProperty("apple.laf.useScreenMenuBar", "true"); 
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Zenses");
            
            this._osxHandler = new OSXHandler(this._zenses);
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
    
    public static void main() {
    	ZensesApplication.main(null);
    }

    public static void main(String[] args) {
        ZensesApplication.launch(ZensesApplication.class, args);
    }
}