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
import org.zenses.ui.ViewHandler;

import java.awt.FlowLayout;
import java.io.IOException;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import javax.swing.JCheckBox;

@SuppressWarnings("unused")
public class PreferencesWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	private JPanel generalSettingsPanel = null;
	private JLabel intervalBetweenScrobblesLabel = null;
	private JTextField intervalBetweenScrobblesField = null;
	private JPanel generalSettingsPanel1 = null;
	private JLabel dateFormatLabel = null;
	private JTextField dateFormatField = null;
	/**
	 * This is the default constructor
	 */
	public PreferencesWindow() {
		super();
		initialize();
	}
	
	public void setPreferences() {
		PreferencesModel prefs = ZensesApplication.getApplication().getZenses().getPreferences();

		this.intervalBetweenScrobblesField.setText(Integer.toString(prefs.getIntervalBetweenScrobbles()));
		this.dateFormatField.setText(prefs.getDateFormat());
	}

	public void savePreferences() {
		PreferencesModel prefs = ZensesApplication.getApplication().getZenses().getPreferences();

		prefs.setIntervalBetweenScrobbles(Integer.valueOf(this.intervalBetweenScrobblesField.getText()));
		prefs.setDateFormat(this.dateFormatField.getText());

		prefs.save();
		
		ViewHandler.getInstance().updateStateMessage("Preferences saved successfully");
		
		ViewHandler.getInstance().updateUI(false);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(440, 148);
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
			jContentPane.add(getGeneralSettingsPanel(), null);
			jContentPane.add(getGeneralSettingsPanel1(), null);
			jContentPane.add(getCancelButton(), null);
			jContentPane.add(getOkButton(), null);
		}
		return jContentPane;
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
			intervalBetweenScrobblesLabel.setToolTipText("The number of seconds to use between each track when calculating the scrobbles, can be positive or negative.");
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

	/**
	 * This method initializes generalSettingsPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getGeneralSettingsPanel1() {
		if (generalSettingsPanel1 == null) {
			dateFormatLabel = new JLabel();
			dateFormatLabel.setName("connectedDevicesLabel");
			dateFormatLabel.setText("Date Format:");
			dateFormatLabel.setToolTipText("The date format to use when reading any date input in the Zenses UI.");
			dateFormatLabel.setPreferredSize(new Dimension(190, 16));
			generalSettingsPanel1 = new JPanel();
			generalSettingsPanel1.setLayout(new BoxLayout(getGeneralSettingsPanel1(), BoxLayout.X_AXIS));
			generalSettingsPanel1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			generalSettingsPanel1.setName("lastfmPanel");
			generalSettingsPanel1.setPreferredSize(new Dimension(380, 20));
			generalSettingsPanel1.add(dateFormatLabel, null);
			generalSettingsPanel1.add(getDateFormatField(), null);
		}
		return generalSettingsPanel1;
	}

	/**
	 * This method initializes dateFormatField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getDateFormatField() {
		if (dateFormatField == null) {
			dateFormatField = new JTextField();
			dateFormatField.setName("lastfmUsernameField");
			dateFormatField.setText("DD/MM/YYYY");
			dateFormatField.setPreferredSize(new Dimension(4, 120));
		}
		return dateFormatField;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
