package org.zenses;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import org.zenses.utils.CustomThread;

public class RootLauncher {
    public static void main(String[] args) {
        if (System.getProperty("os.name").toLowerCase().startsWith("mac")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true"); 
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Zenses");
        }
        
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			Logger.getLogger(ZensesApplication.class.getName()).log(Level.SEVERE, null, e);
		}
    	
    	
        new CustomThread<String[]>(args) {
    		public void run() {
    			ZensesApplication.launch(ZensesApplication.class, this.getData());
    		}
    	}.start();
    }
}
