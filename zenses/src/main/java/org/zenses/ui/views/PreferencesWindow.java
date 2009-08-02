package org.zenses.ui.views;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Dimension;
import javax.swing.JButton;

import org.zenses.ZensesApplication;
import org.zenses.models.PreferencesModel;

import java.awt.FlowLayout;
import java.io.IOException;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;

@SuppressWarnings("unused")
public class PreferencesWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel lastfmPanel = null;
	private JLabel lastfmUsernameLabel = null;
	private JTextField lastfmUsernameField = null;
	private JLabel lastfmPasswordLabel = null;
	private JPasswordField lastfmPasswordField = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	private JPanel generalSettingsPanel = null;
	private JLabel intervalBetweenScrobblesLabel = null;
	private JTextField intervalBetweenScrobblesField = null;
	/**
	 * This is the default constructor
	 */
	public PreferencesWindow() {
		super();
		initialize();
	}
	
	public void setPreferences() {
		PreferencesModel prefs = ZensesApplication.getApplication().getZenses().getPreferences();

		this.lastfmUsernameField.setText(prefs.get_lastfmUsername());
		this.lastfmPasswordField.setText(prefs.get_lastfmPassword());
		this.intervalBetweenScrobblesField.setText(Integer.toString(prefs.getIntervalBetweenScrobbles()));
	}

	public void savePreferences() {
		PreferencesModel prefs = ZensesApplication.getApplication().getZenses().getPreferences();

		prefs.set_lastfmUsername(this.lastfmUsernameField.getText());
		prefs.set_lastfmPassword(new String(this.lastfmPasswordField.getPassword()));
		prefs.setIntervalBetweenScrobbles(Integer.valueOf(this.intervalBetweenScrobblesField.getText()));

		prefs.save();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(440, 172);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle("Preferences");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(5);
			flowLayout.setVgap(15);
			jContentPane = new JPanel();
			jContentPane.setLayout(flowLayout);
			jContentPane.add(getLastfmPanel(), null);
			jContentPane.add(getGeneralSettingsPanel(), null);
			jContentPane.add(getCancelButton(), null);
			jContentPane.add(getOkButton(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes lastfmPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getLastfmPanel() {
		if (lastfmPanel == null) {
			lastfmPasswordLabel = new JLabel();
			lastfmPasswordLabel.setName("connectedDevicesLabel");
			lastfmPasswordLabel.setText("Last.fm Password:");
			lastfmUsernameLabel = new JLabel();
			lastfmUsernameLabel.setName("connectedDevicesLabel");
			lastfmUsernameLabel.setText("Last.fm Username:");
			GridLayout gridLayout2 = new GridLayout();
			gridLayout2.setRows(2);
			lastfmPanel = new JPanel();
			lastfmPanel.setPreferredSize(new Dimension(380, 40));
			lastfmPanel.setLayout(gridLayout2);
			lastfmPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			lastfmPanel.setName("lastfmPanel");
			lastfmPanel.add(lastfmUsernameLabel, null);
			lastfmPanel.add(getLastfmUsernameField(), null);
			lastfmPanel.add(lastfmPasswordLabel, null);
			lastfmPanel.add(getLastfmPasswordField(), null);
		}
		return lastfmPanel;
	}

	/**
	 * This method initializes lastfmUsernameField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getLastfmUsernameField() {
		if (lastfmUsernameField == null) {
			lastfmUsernameField = new JTextField();
			lastfmUsernameField.setName("lastfmUsernameField");
		}
		return lastfmUsernameField;
	}

	/**
	 * This method initializes lastfmPasswordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getLastfmPasswordField() {
		if (lastfmPasswordField == null) {
			lastfmPasswordField = new JPasswordField();
			lastfmPasswordField.setName("lastfmPasswordField");
		}
		return lastfmPasswordField;
	}

	/**
	 * This method initializes okButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setName("fetchTracksButton");
			okButton.setText("Ok");
			okButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					ZensesApplication.getApplication().getZenses().getViewHandler().hidePreferencesWindow(true);
				}
			});
		}
		return okButton;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
			cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					ZensesApplication.getApplication().getZenses().getViewHandler().hidePreferencesWindow(false);
				}
			});
		}
		return cancelButton;
	}

	/**
	 * This method initializes generalSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getGeneralSettingsPanel() {
		if (generalSettingsPanel == null) {
			intervalBetweenScrobblesLabel = new JLabel();
			intervalBetweenScrobblesLabel.setName("connectedDevicesLabel");
			intervalBetweenScrobblesLabel.setPreferredSize(new Dimension(190, 16));
			intervalBetweenScrobblesLabel.setText("Interval Between Scrobbles:");
			generalSettingsPanel = new JPanel();
			generalSettingsPanel.setLayout(new BoxLayout(getGeneralSettingsPanel(), BoxLayout.X_AXIS));
			generalSettingsPanel.setPreferredSize(new Dimension(380, 20));
			generalSettingsPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			generalSettingsPanel.setName("lastfmPanel");
			generalSettingsPanel.add(intervalBetweenScrobblesLabel, null);
			generalSettingsPanel.add(getIntervalBetweenScrobblesField(), null);
		}
		return generalSettingsPanel;
	}

	/**
	 * This method initializes intervalBetweenScrobblesField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getIntervalBetweenScrobblesField() {
		if (intervalBetweenScrobblesField == null) {
			intervalBetweenScrobblesField = new JTextField();
			intervalBetweenScrobblesField.setName("lastfmUsernameField");
			intervalBetweenScrobblesField.setPreferredSize(new Dimension(4, 120));
		}
		return intervalBetweenScrobblesField;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
