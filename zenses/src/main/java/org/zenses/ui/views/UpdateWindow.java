package org.zenses.ui.views;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.WindowConstants;

import org.zenses.Zenses;
import org.zenses.ui.ViewHandler;

import java.awt.Font;

public class UpdateWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JButton updateButton = null;
	private JButton cancelUpdateButton = null;
	private JPanel jPanel1 = null;
	private JTextArea changesTextArea = null;
	private JPanel jPanel2 = null;
	private JLabel versionMessageLabel = null;
	private JLabel jLabel = null;

	/**
	 * This is the default constructor
	 */
	public UpdateWindow() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(583, 298);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		this.setContentPane(getJContentPane());
		this.setTitle("Zenses Update Available");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new FlowLayout());
			jContentPane.add(getJPanel2(), null);
			//jContentPane.add(releaseNotesLabel, null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanel.setEnabled(false);
			jPanel.setPreferredSize(new Dimension(416, 36));
			jPanel.add(getCancelUpdateButton(), null);
			jPanel.add(getUpdateButton(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes updateButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getUpdateButton() {
		if (updateButton == null) {
			updateButton = new JButton();
			updateButton.setText("Download Update via Web Browser");
			updateButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					Zenses.getInstance().openBrowser("http://sixones.com/projects/zenses/update.html");
					ViewHandler.getInstance().hideUpdateWindow();
				}
			});
		}
		return updateButton;
	}

	/**
	 * This method initializes cancelUpdateButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelUpdateButton() {
		if (cancelUpdateButton == null) {
			cancelUpdateButton = new JButton();
			cancelUpdateButton.setText("Update Later");
			cancelUpdateButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					ViewHandler.getInstance().hideUpdateWindow();
				}
			});
		}
		return cancelUpdateButton;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			jPanel1 = new JPanel();
			jPanel1.setLayout(gridLayout);
			jPanel1.setPreferredSize(new Dimension(550, 176));
			jPanel1.add(getChangesTextArea(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes changesTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getChangesTextArea() {
		if (changesTextArea == null) {
			changesTextArea = new JTextArea();
			changesTextArea.setPreferredSize(new Dimension(400, 160));
			changesTextArea.setBorder(LineBorder.createBlackLineBorder());
			changesTextArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			changesTextArea.setFont(new Font("Verdana", Font.PLAIN, 12));
			changesTextArea.setEditable(false);
		}
		return changesTextArea;
	}
	
	public void updateVersionMessage() {
		String m = "Zenses "+Zenses.versionAvailable+" is now available, you have "+Zenses.version+". Would you like to visit the update site now?";
		
		this.versionMessageLabel.setText(m);
	}
	
	public void updateChanges() {
		this.changesTextArea.setText(Zenses.update.changes);
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel = new JLabel();
			jLabel.setText("Update Details:");
			jLabel.setFont(new Font("Dialog", Font.BOLD, 12));
			versionMessageLabel = new JLabel();
			versionMessageLabel.setText("Zenses 2.0.2 is now available, you have 2.0.0. Would you like to visit the update site now?");
			versionMessageLabel.setHorizontalAlignment(SwingConstants.LEFT);
			versionMessageLabel.setHorizontalTextPosition(SwingConstants.LEFT);
			versionMessageLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			versionMessageLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			versionMessageLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			versionMessageLabel.setPreferredSize(new Dimension(511, 200));
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BoxLayout(getJPanel2(), BoxLayout.Y_AXIS));
			jPanel2.setPreferredSize(new Dimension(550, 42));
			jPanel2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanel2.add(versionMessageLabel, null);
			jPanel2.add(jLabel, null);
		}
		return jPanel2;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
