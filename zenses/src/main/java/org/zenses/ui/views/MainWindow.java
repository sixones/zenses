package org.zenses.ui.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import org.zenses.ZensesApplication;
import org.zenses.ui.ViewHandler;
import javax.swing.SwingConstants;
import java.awt.Insets;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = -5029363732074202672L;

	protected ViewHandler _viewHandler;

	private JPanel jContentPane = null;

	private JToolBar _toolbar = null;

	private JButton toolbarFindDevicesButton = null;

	private JButton toolbarPreferencesButton = null;

	private JTabbedPane tabbedPane = null;

	private JPanel summaryTabPanel = null;

	private JPanel devicePanel = null;

	private JLabel connectedDevicesLabel = null;

	private JComboBox connectedDevicesComboBox = null;

	private JButton fetchTracksButton = null;

	private JPanel tracksTabPanel = null;

	private JPanel scrobbleOptionsPanel = null;

	private JLabel scrobbleTracksLabel = null;

	private JTextField scrobbleTimeField = null;

	private JButton scrobbleTracksButton = null;

	private JScrollPane jScrollPane = null;

	private JTable unscrobbledTracksTable = null;

	private JPanel historyTabPanel = null;

	private JTable scrobbledHistoryTable = null;

	private JPanel statusBarPanel = null;

	private JLabel statusLabel = null;

	private JScrollPane jScrollPane1 = null;

	private JLabel summaryTextLabel = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	/**
	 * This is the default constructor
	 */
	public MainWindow(ViewHandler viewHandler) {
		super();

		this._viewHandler = viewHandler;
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 350);
		//this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/zenses/resources/zenses-icon.png")));
		this.setContentPane(getJContentPane());
		// this.setContentPane(getJContentPane());
		this.setTitle("Zenses2 Beta");
	}

	// /**
	// * This method initializes devicePanel
	// *
	// * @return javax.swing.JPanel
	// */
	// private JPanel getDevicePanel() {
	// if (devicePanel == null) {
	// GridBagConstraints gridBagConstraints = new GridBagConstraints();
	// gridBagConstraints.insets = new Insets(11, 342, 244, 2);
	// gridBagConstraints.gridy = -1;
	// gridBagConstraints.gridx = -1;
	// GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
	// gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
	// gridBagConstraints1.gridx = -1;
	// gridBagConstraints1.gridy = -1;
	// gridBagConstraints1.weightx = 1.0;
	// gridBagConstraints1.insets = new Insets(6, 3, 238, 2);
	// GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
	// gridBagConstraints2.insets = new Insets(5, 3, 237, 343);
	// gridBagConstraints2.gridy = -1;
	// gridBagConstraints2.gridx = -1;
	// devicePanel.add(getConnectedDevicesComboBox(), null);
	// devicePanel.add(getFetchTracksButton(), null);
	// }
	// return devicePanel;
	// }

	// /**
	// * This method initializes scrobbleOptionsPanel
	// *
	// * @return javax.swing.JPanel
	// */
	// private JPanel getScrobbleOptionsPanel() {
	// if (scrobbleOptionsPanel == null) {
	// GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
	// gridBagConstraints6.fill = GridBagConstraints.VERTICAL;
	// gridBagConstraints6.gridy = -1;
	// gridBagConstraints6.weightx = 1.0;
	// gridBagConstraints6.gridx = 2;
	// scrobbleOptionsPanel.add(getScrobbleTracksButton(), gridBagConstraints6);
	// scrobbleOptionsPanel.add(getScrobbleTimeField(),
	// getScrobbleTimeField().getName());
	// }
	// return scrobbleOptionsPanel;
	// }

	public JLabel getStatusLabel() {
		return this.statusLabel;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(get_toolbar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getTabbedPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getStatusBarPanel(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes _toolbar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar get_toolbar() {
		if (_toolbar == null) {
			_toolbar = new JToolBar();
			_toolbar.setFloatable(false);
			_toolbar.add(getToolbarFindDevicesButton());
			_toolbar.add(getToolbarPreferencesButton());
		}
		return _toolbar;
	}

	/**
	 * This method initializes toolbarFindDevicesButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getToolbarFindDevicesButton() {
		if (toolbarFindDevicesButton == null) {
			toolbarFindDevicesButton = new JButton();
			toolbarFindDevicesButton.setText("Find Devices");
			toolbarFindDevicesButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					ZensesApplication.getApplication().getZenses().getViewHandler().findDevices();
				}
			});
		}
		return toolbarFindDevicesButton;
	}

	/**
	 * This method initializes toolbarPreferencesButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getToolbarPreferencesButton() {
		if (toolbarPreferencesButton == null) {
			toolbarPreferencesButton = new JButton();
			toolbarPreferencesButton.setText("Preferences");
			toolbarPreferencesButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					ZensesApplication.getApplication().getZenses().getViewHandler().showPreferencesView();
				}
			});
		}
		return toolbarPreferencesButton;
	}

	/**
	 * This method initializes tabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane();
			tabbedPane.setBackground(null);
			tabbedPane.addTab("Zenses Summary", null, getSummaryTabPanel(), null);
			tabbedPane.addTab("Unscrobbled Tracks", null, getTracksTabPanel(), null);
			tabbedPane.addTab("Scrobbled History", null, getHistoryTabPanel(), null);
		}
		return tabbedPane;
	}

	/**
	 * This method initializes summaryTabPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getSummaryTabPanel() {
		if (summaryTabPanel == null) {
			summaryTextLabel = new JLabel();
			summaryTextLabel.setText("Nothing Scrobbled Yet :(");
			summaryTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
			summaryTextLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			summaryTabPanel = new JPanel();
			summaryTabPanel.setLayout(new FlowLayout());
			summaryTabPanel.setBackground(null);
			summaryTabPanel.add(getJPanel(), null);
		}
		return summaryTabPanel;
	}

	public JLabel getSummaryTextLabel() {
		return this.summaryTextLabel;
	}
	
	/**
	 * This method initializes devicePanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getDevicePanel2() {
		if (devicePanel == null) {
			connectedDevicesLabel = new JLabel();
			connectedDevicesLabel.setName("connectedDevicesLabel");
			connectedDevicesLabel.setText("Connected Device:");
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			gridLayout.setHgap(2);
			gridLayout.setColumns(1);
			devicePanel = new JPanel();
			devicePanel.setLayout(gridLayout);
			devicePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			devicePanel.add(connectedDevicesLabel, connectedDevicesLabel.getName());
			devicePanel.add(getConnectedDevicesComboBox(), null);
			devicePanel.add(getFetchTracksButton(), null);
		}
		return devicePanel;
	}

	/**
	 * This method initializes connectedDevicesComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	public JComboBox getConnectedDevicesComboBox() {
		if (connectedDevicesComboBox == null) {
			connectedDevicesComboBox = new JComboBox();
			connectedDevicesComboBox.setEnabled(false);
			connectedDevicesComboBox.setName("connectedDevicesComboBox");
		}
		return connectedDevicesComboBox;
	}

	/**
	 * This method initializes fetchTracksButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getFetchTracksButton() {
		if (fetchTracksButton == null) {
			fetchTracksButton = new JButton();
			fetchTracksButton.setName("fetchTracksButton");
			fetchTracksButton.setPreferredSize(new Dimension(40, 26));
			fetchTracksButton.setText("Fetch Tracks");
			fetchTracksButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					ZensesApplication.getApplication().getZenses().getViewHandler().fetchTracksFromSelectedDevice();
				}

			});
		}
		return fetchTracksButton;
	}

	/**
	 * This method initializes tracksTabPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getTracksTabPanel() {
		if (tracksTabPanel == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridy = 2;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.weighty = 1.0;
			gridBagConstraints3.gridx = 0;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridheight = 1;
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 1;
			gridBagConstraints8.ipadx = 5;
			gridBagConstraints8.ipady = 5;
			gridBagConstraints8.gridwidth = 300;
			tracksTabPanel = new JPanel();
			tracksTabPanel.setLayout(new GridBagLayout());
			tracksTabPanel.add(getScrobbleOptionsPanel2(), gridBagConstraints8);
			tracksTabPanel.add(getJScrollPane(), gridBagConstraints3);
		}
		return tracksTabPanel;
	}

	/**
	 * This method initializes scrobbleOptionsPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getScrobbleOptionsPanel2() {
		if (scrobbleOptionsPanel == null) {
			scrobbleTracksLabel = new JLabel();
			scrobbleTracksLabel.setName("scrobbleTracksLabel");
			scrobbleTracksLabel.setText("Scrobble Tracks from:");
			scrobbleOptionsPanel = new JPanel();
			scrobbleOptionsPanel.setLayout(new BoxLayout(scrobbleOptionsPanel, BoxLayout.X_AXIS));
			scrobbleOptionsPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			scrobbleOptionsPanel.add(scrobbleTracksLabel, null);
			scrobbleOptionsPanel.add(getScrobbleTimeField(), null);
			scrobbleOptionsPanel.add(getScrobbleTracksButton(), null);
		}
		return scrobbleOptionsPanel;
	}

	/**
	 * This method initializes scrobbleTimeField
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getScrobbleTimeField() {
		if (scrobbleTimeField == null) {
			scrobbleTimeField = new JTextField();
			scrobbleTimeField.setName("scrobbleTimeField");
			scrobbleTimeField.setPreferredSize(new Dimension(332, 28));
			scrobbleTimeField.setMinimumSize(new Dimension(300, 28));
			scrobbleTimeField.setText(_viewHandler.getZenses().getDeviceTrackService().getNextSubmissionTime());
		}
		return scrobbleTimeField;
	}

	/**
	 * This method initializes scrobbleTracksButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getScrobbleTracksButton() {
		if (scrobbleTracksButton == null) {
			scrobbleTracksButton = new JButton();
			scrobbleTracksButton.setName("scrobbleTracksButton");
			scrobbleTracksButton.setText("Scrobble");
			scrobbleTracksButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ZensesApplication.getApplication().getZenses().getViewHandler().scrobbleSelectedTracks();
					scrobbleTimeField.setText(_viewHandler.getZenses().getDeviceTrackService().getNextSubmissionTime());
				}
			});
		}
		return scrobbleTracksButton;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getUnscrobbledTracksTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes unscrobbledTracksTable
	 * 
	 * @return javax.swing.JTable
	 */
	public JTable getUnscrobbledTracksTable() {
		if (unscrobbledTracksTable == null) {
			unscrobbledTracksTable = new JTable();
			unscrobbledTracksTable.setShowGrid(true);
		}
		return unscrobbledTracksTable;
	}

	/**
	 * This method initializes historyTabPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getHistoryTabPanel() {
		if (historyTabPanel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			historyTabPanel = new JPanel();
			historyTabPanel.setLayout(new GridBagLayout());
			historyTabPanel.add(getJScrollPane1(), gridBagConstraints);
		}
		return historyTabPanel;
	}

	/**
	 * This method initializes scrobbledHistoryTable
	 * 
	 * @return javax.swing.JTable
	 */
	public JTable getScrobbledHistoryTable() {
		if (scrobbledHistoryTable == null) {
			scrobbledHistoryTable = new JTable();
			scrobbledHistoryTable.setShowGrid(true);
		}
		return scrobbledHistoryTable;
	}

	/**
	 * This method initializes statusBarPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getStatusBarPanel() {
		if (statusBarPanel == null) {
			statusLabel = new JLabel();
			statusLabel.setName("statusLabel");
			statusLabel.setText("Loading ...");
			CardLayout cardLayout = new CardLayout();
			cardLayout.setHgap(5);
			cardLayout.setVgap(5);
			statusBarPanel = new JPanel();
			statusBarPanel.setLayout(cardLayout);
			statusBarPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			statusBarPanel.add(statusLabel, statusLabel.getName());
		}
		return statusBarPanel;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getScrobbledHistoryTable());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(0, 0, 1, 0);
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.ipadx = 315;
			gridBagConstraints2.ipady = 17;
			gridBagConstraints2.gridx = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.ipadx = 122;
			gridBagConstraints1.gridy = 0;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanel.setPreferredSize(new Dimension(400, 60));
			jPanel.add(getDevicePanel2(), gridBagConstraints1);
			jPanel.add(getJPanel1(), gridBagConstraints2);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getSummaryTextLabel(), new GridBagConstraints());
		}
		return jPanel1;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
