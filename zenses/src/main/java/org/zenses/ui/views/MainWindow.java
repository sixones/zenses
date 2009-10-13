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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.WindowConstants;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.zenses.Zenses;
import org.zenses.ZensesApplication;
import org.zenses.ui.DateChooser;
import org.zenses.ui.ListenerActions;
import org.zenses.ui.ViewHandler;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Color;
import javax.swing.ListSelectionModel;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = -5029363732074202672L;

	protected ViewHandler _viewHandler;

	private JPanel jContentPane = null;

	private JTabbedPane tabbedPane = null;

	private JPanel summaryTabPanel = null;

	private JPanel devicePanel = null;

	private JLabel connectedDevicesLabel = null;

	private JComboBox connectedDevicesComboBox = null;

	private JButton fetchTracksButton = null;

	private JPanel tracksTabPanel = null;

	private JPanel scrobbleOptionsPanel = null;

	private JLabel scrobbleTracksLabel = null;

	private JButton scrobbleTracksButton = null;

	private JCheckBox goBackInTimeCheckbox = null; 

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

	private JPanel jPanel2 = null;

	private JButton authenticateButton = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel4 = null;

	private JPanel jPanel5 = null;

	private JPanel jPanel11 = null;

	private JLabel summaryTextLabel2 = null;

	private JButton authenticateWithButton = null;

	private JMenuBar jJMenuBar = null;

	private JMenu jMenu = null;

	private JMenuItem exitMenuItem = null;

	private JMenu windowMenu = null;

	private JMenuItem preferencesMenuItem = null;

	private JMenuItem findDevicesMenuItem = null;

	private JMenu helpMenu = null;

	private JMenuItem forumMenuItem = null;

	private JMenuItem aboutMenuItem = null;

	private JMenuItem blogMenuItem = null;

	private JMenuItem siteMenuItem = null;

	private JTextField scrobbleTimeField = null;

	private JPanel jPanel6 = null;

	private JTextField scrobbleDateField = null;

	private JPanel jPanel7 = null;

	private JMenuItem checkUpdateMenuItem = null;

	private JLabel unscrobbledCountLabel = null;

	private JPanel jPanel8 = null;

	private JPanel toolbarPanel = null;

	private JPanel jPanel9 = null;

	private JPanel jPanel10 = null;

	private JButton ignoreSelectedButton = null;

	/**
	 * This is the default constructor
	 */
	public MainWindow(ViewHandler viewHandler) {
		super();

		this._viewHandler = viewHandler;
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		initialize();
		
		this.unauthenticate();
	}
	
	public void authenticated() {
		this.jPanel5.setVisible(false);
		this.jPanel2.setVisible(true);
		
		this.summaryTextLabel2.setText("Logged in as " + this._viewHandler.getZenses().getTracksSubmitter().getSessionUsername());
	}
	
	public void unauthenticate() {
		this.jPanel5.setVisible(true);
		this.jPanel2.setVisible(false);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 350);
		//this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/zenses/resources/zenses-icon.png")));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(getJJMenuBar());
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
			jContentPane.add(getTabbedPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getStatusBarPanel(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes tabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane();
			//tabbedPane.setBackground(new Color(55, 238, 238));
			//tabbedPane.setForeground(new Color(238, 238, 238));
			tabbedPane.addTab("Zenses Summary", null, getSummaryTabPanel(), null);
			tabbedPane.addTab("Unscrobbled Tracks", null, getTracksTabPanel(), null);
			tabbedPane.addTab("Scrobbled History", null, getHistoryTabPanel(), null);

			//tabbedPane.setForeground(new Color(0, 0, 0));
			//tabbedPane.setBackground(new Color(0, 0, 0));
			
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				//tabbedPane.setBackgroundAt(i, new Color(238, 238, 238));
			}
			
			tabbedPane.updateUI();
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
			summaryTextLabel.setText("Nothing Scrobbled Yet");
			summaryTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
			summaryTextLabel.setPreferredSize(new Dimension(400, 46));
			summaryTextLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			summaryTabPanel = new JPanel();
			summaryTabPanel.setLayout(new BoxLayout(getSummaryTabPanel(), BoxLayout.Y_AXIS));
			summaryTabPanel.setBackground(Color.black);
			summaryTabPanel.add(getJPanel(), null);
			summaryTabPanel.add(getJPanel5(), null);
			summaryTabPanel.add(getJPanel2(), null);
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
			devicePanel = new JPanel();
			devicePanel.setPreferredSize(new Dimension(419, 36));
			devicePanel.setLayout(new FlowLayout());
			devicePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			devicePanel.add(connectedDevicesLabel, null);
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
			connectedDevicesComboBox.setPreferredSize(new Dimension(180, 26));
			connectedDevicesComboBox.setName("connectedDevicesComboBox");
		}
		return connectedDevicesComboBox;
	}

	/**
	 * This method initializes fetchTracksButton
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getFetchTracksButton() {
		if (fetchTracksButton == null) {
			fetchTracksButton = new JButton();
			fetchTracksButton.setName("fetchTracksButton");
			fetchTracksButton.setPreferredSize(new Dimension(110, 26));
			fetchTracksButton.setEnabled(false);
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
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridy = 3;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.weighty = 1.0;
			gridBagConstraints3.gridx = 0;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridheight = 1;
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 4;
			gridBagConstraints8.ipadx = 0;
			gridBagConstraints8.ipady = 0;
			gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.anchor = GridBagConstraints.EAST;
			gridBagConstraints8.weighty = 0.0D;
			gridBagConstraints8.weightx = 0.0D;
			gridBagConstraints8.gridwidth = 1;
			
			tracksTabPanel = new JPanel();
			tracksTabPanel.setLayout(new GridBagLayout());
			tracksTabPanel.setBackground(new Color(238, 238, 238));
			tracksTabPanel.add(getScrobbleOptionsPanel2(), gridBagConstraints8);
			tracksTabPanel.add(getJScrollPane(), gridBagConstraints3);
			tracksTabPanel.add(getToolbarPanel(), gridBagConstraints2);
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
			unscrobbledCountLabel = new JLabel();
			unscrobbledCountLabel.setText("0 of 0 tracks, with no durations");
			scrobbleTracksLabel = new JLabel();
			scrobbleTracksLabel.setName("scrobbleTracksLabel");
			scrobbleTracksLabel.setText("Scrobble Tracks from:");
			scrobbleOptionsPanel = new JPanel();
			scrobbleOptionsPanel.setLayout(new BoxLayout(getScrobbleOptionsPanel2(), BoxLayout.X_AXIS));
			scrobbleOptionsPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			//scrobbleOptionsPanel.setPreferredSize(new Dimension(360, 36));
			scrobbleOptionsPanel.add(getJPanel9(), null);
			//scrobbleOptionsPanel.setPreferredSize(new Dimension(562, 39));
			
			/// ARGHH HERE

			scrobbleOptionsPanel.add(scrobbleTracksLabel, null);
			scrobbleOptionsPanel.add(getJPanel6(), null);
			scrobbleOptionsPanel.add(getGoBackInTimeCheckbox(), null);
			scrobbleOptionsPanel.add(getScrobbleTracksButton(), null);
			scrobbleOptionsPanel.add(getIgnoreSelectedButton(), null);
		}
		return scrobbleOptionsPanel;
	}
	
	public JLabel getUnscrobbledCountLabel() {
		return unscrobbledCountLabel;
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
					//scrobbleTimeField.setText(_viewHandler.getZenses().getDeviceTrackService().getNextSubmissionTime());
				}
			});
		}
		return scrobbleTracksButton;
	}
	
	/**
	 * This method initializes scrobbleTracksButton
	 * 
	 * @return javax.swing.JButton
	 */
	public JCheckBox getGoBackInTimeCheckbox() {
		if (goBackInTimeCheckbox == null) {
			goBackInTimeCheckbox = new JCheckBox();
			goBackInTimeCheckbox.setName("goBackInTimeCheckbox");
			goBackInTimeCheckbox.setText("Set time as end time");
		}
		return goBackInTimeCheckbox;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBackground(Color.white);
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
			//unscrobbledTracksTable.setIntercellSpacing(new Dimension(0, 0));
			unscrobbledTracksTable.setGridColor(new Color(255, 240, 240));
			//unscrobbledTracksTable.setIntercellSpacing(new Dimension(5, 5));
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
			scrobbledHistoryTable.setGridColor(new Color(240, 240, 240));
			scrobbledHistoryTable.setSelectionBackground(Color.LIGHT_GRAY);
			scrobbledHistoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scrobbledHistoryTable.setSelectionForeground(Color.BLACK);
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
			statusBarPanel = new JPanel();
			statusBarPanel.setLayout(new BoxLayout(getStatusBarPanel(), BoxLayout.X_AXIS));
			statusBarPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			statusBarPanel.add(getJPanel10(), null);
			statusBarPanel.add(getJPanel8(), null);
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
			jScrollPane1.setBackground(Color.white);
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
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 0;
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.Y_AXIS));
			jPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanel.setPreferredSize(new Dimension(600, 92));
			jPanel.add(getDevicePanel2(), null);
			jPanel.add(getJPanel1(), null);
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
			jPanel1.setLayout(new FlowLayout());
			jPanel1.setPreferredSize(new Dimension(400, 56));
			jPanel1.add(getSummaryTextLabel(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(0, 0, 1, 0);
			gridBagConstraints4.gridy = -1;
			gridBagConstraints4.ipadx = 315;
			gridBagConstraints4.ipady = 17;
			gridBagConstraints4.gridx = -1;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridy = 0;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.gridy = 5;
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.setPreferredSize(new Dimension(450, 60));
			jPanel2.add(getJPanel11(), gridBagConstraints4);
			jPanel2.add(getJPanel32(), gridBagConstraints9);
			jPanel2.add(getJPanel4(), gridBagConstraints10);
		}
		return jPanel2;
	}

	/**
	 * This method initializes authenticateButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAuthenticateButton() {
		if (authenticateButton == null) {
			authenticateButton = new JButton();
			authenticateButton.setText("Remove Last.fm Authentication");
			authenticateButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					ZensesApplication.getApplication().getZenses().unauthenticate();
				}
			});
		}
		return authenticateButton;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel32() {
		if (jPanel3 == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = -1;
			gridBagConstraints5.gridy = -1;
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GridBagLayout());
			jPanel3.add(getAuthenticateButton(), gridBagConstraints5);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GridBagLayout());
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(1);
			jPanel5 = new JPanel();
			jPanel5.setLayout(gridLayout1);
			jPanel5.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanel5.setPreferredSize(new Dimension(450, 28));
			jPanel5.add(getJPanel7(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jPanel11	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			summaryTextLabel2 = new JLabel();
			summaryTextLabel2.setHorizontalAlignment(SwingConstants.CENTER);
			summaryTextLabel2.setText("Logged in as Nobody");
			summaryTextLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
			jPanel11 = new JPanel();
			jPanel11.setLayout(new GridBagLayout());
			jPanel11.setPreferredSize(new Dimension(300, 16));
			jPanel11.add(summaryTextLabel2, new GridBagConstraints());
		}
		return jPanel11;
	}

	/**
	 * This method initializes authenticateWithButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAuthenticateWithButton() {
		if (authenticateWithButton == null) {
			authenticateWithButton = new JButton();
			authenticateWithButton.setText("Authenticate with Last.fm");
			authenticateWithButton.setPreferredSize(new Dimension(200, 26));
			authenticateWithButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					ZensesApplication.getApplication().getZenses().authenticate();
				}
			});
		}
		return authenticateWithButton;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenu());
			
			if (!Zenses.getInstance().isMacOSX()) {
				jJMenuBar.add(getWindowMenu());
			}
			
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu() {
		if (jMenu == null) {
			jMenu = new JMenu();
			jMenu.setText("File");
			jMenu.add(getFindDevicesMenuItem());
			jMenu.add(getCheckUpdateMenuItem());
			
			if (!Zenses.getInstance().isMacOSX()) {
				jMenu.addSeparator();
				jMenu.add(getExitMenuItem());
			}
		}
		return jMenu;
	}

	/**
	 * This method initializes exitMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					ZensesApplication.getApplication().exit();
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes windowMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getWindowMenu() {
		if (windowMenu == null) {
			windowMenu = new JMenu();
			windowMenu.setText("Window");
			windowMenu.add(getPreferencesMenuItem());
		}
		return windowMenu;
	}

	/**
	 * This method initializes preferencesMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPreferencesMenuItem() {
		if (preferencesMenuItem == null) {
			preferencesMenuItem = new JMenuItem();
			preferencesMenuItem.setText("Preferences");
			preferencesMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					ZensesApplication.getApplication().getZenses().getViewHandler().showPreferencesView();
				}
			});
		}
		return preferencesMenuItem;
	}

	/**
	 * This method initializes findDevicesMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getFindDevicesMenuItem() {
		if (findDevicesMenuItem == null) {
			findDevicesMenuItem = new JMenuItem();
			findDevicesMenuItem.setText("Find Devices");
			findDevicesMenuItem.addActionListener(ViewHandler.getInstance().getListener());
			findDevicesMenuItem.setActionCommand(ListenerActions.FIND_DEVICES.toString());
		}
		return findDevicesMenuItem;
	}

	/**
	 * This method initializes helpMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getSiteMenuItem());
			helpMenu.add(getForumMenuItem());
			helpMenu.add(getBlogMenuItem());
			helpMenu.addSeparator();
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes forumMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getForumMenuItem() {
		if (forumMenuItem == null) {
			forumMenuItem = new JMenuItem();
			forumMenuItem.setText("Official Forums");
			forumMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					ZensesApplication.getApplication().getZenses().openBrowser("http://forum.sixones.com/");
				}
			});
		}
		return forumMenuItem;
	}

	/**
	 * This method initializes aboutMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					ZensesApplication.getApplication().getZenses().getViewHandler().showAboutWindow();
				}
			});
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes blogMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getBlogMenuItem() {
		if (blogMenuItem == null) {
			blogMenuItem = new JMenuItem();
			blogMenuItem.setText("Developer Blog");
			blogMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					ZensesApplication.getApplication().getZenses().openBrowser("http://sixones.com/");
				}
			});
		}
		return blogMenuItem;
	}

	/**
	 * This method initializes siteMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSiteMenuItem() {
		if (siteMenuItem == null) {
			siteMenuItem = new JMenuItem();
			siteMenuItem.setText("Zenses2 Official Site");
			siteMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					ZensesApplication.getApplication().getZenses().openBrowser("http://getzenses.com");
				}
			});
		}
		return siteMenuItem;
	}

	public JTextField getScrobbleTimeField() {
		if (scrobbleTimeField == null) {
			scrobbleTimeField = new JTextField();
			scrobbleTimeField.setName("scrobbleTimeField");
			scrobbleTimeField.setPreferredSize(new Dimension(60, 26));
			scrobbleTimeField.setMinimumSize(new Dimension(50, 28));
			scrobbleTimeField.setToolTipText("The 24h time to scrobble tracks from as HH:MM");
			scrobbleTimeField.setText("00:00");
		}
		return scrobbleTimeField;
	}

	/**
	 * This method initializes jPanel6	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setName("jPanel6");
			jPanel6.setPreferredSize(new Dimension(150, 26));
			jPanel6.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanel6.setLayout(new BoxLayout(getJPanel6(), BoxLayout.X_AXIS));
			jPanel6.add(getScrobbleTimeField(), null);
			jPanel6.add(getScrobbleDateField(), null);
		}
		return jPanel6;
	}

	/**
	 * This method initializes scrobbleDateField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getScrobbleDateField() {
		if (scrobbleDateField == null) {
			scrobbleDateField = new JTextField();
			scrobbleDateField.setName("scrobbleTimeField");
			scrobbleDateField.setPreferredSize(new Dimension(70, 26));
			scrobbleDateField.setToolTipText("Date to scrobble tracks from as " + Zenses.getInstance().getPreferences().getDateFormat());
			scrobbleDateField.setText("01/01/2000");
			scrobbleDateField.setMinimumSize(new Dimension(100, 28));
			scrobbleDateField.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					DateTimeFormatter formatter = DateTimeFormat.forPattern(Zenses.getInstance().getPreferences().getDateFormat());
					long currentDateStamp = formatter.parseMillis(((JTextField)e.getComponent()).getText());
					
					MainWindow mainWindow = ZensesApplication.getApplication().getZenses().getViewHandler().getMainWindow();
					
					Date date = new Date(currentDateStamp);
					DateChooser chooser = new DateChooser(mainWindow, "Select Date");
					chooser.setLocationRelativeTo(mainWindow.scrobbleDateField);
					Date newDate = chooser.select(date);
					
					if (newDate != null) {
						DateFormat dateFormatter = new SimpleDateFormat(Zenses.getInstance().getPreferences().getDateFormat()); // "dd/MM/yyyy"
					
						mainWindow.getScrobbleDateField().setText(dateFormatter.format(newDate));
					}
				}
			});
		}
		return scrobbleDateField;
	}

	/**
	 * This method initializes jPanel7	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(new GridBagLayout());
			jPanel7.add(getAuthenticateWithButton(), new GridBagConstraints());
		}
		return jPanel7;
	}

	/**
	 * This method initializes checkUpdateMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCheckUpdateMenuItem() {
		if (checkUpdateMenuItem == null) {
			checkUpdateMenuItem = new JMenuItem();
			checkUpdateMenuItem.setText("Check for Updates");
			checkUpdateMenuItem.addActionListener(ViewHandler.getInstance().getListener());
			checkUpdateMenuItem.setActionCommand(ListenerActions.UPDATE_CHECK.toString());
		}
		return checkUpdateMenuItem;
	}

	/**
	 * This method initializes jPanel8	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			FlowLayout flowLayout2 = new FlowLayout();
			flowLayout2.setHgap(5);
			flowLayout2.setAlignment(FlowLayout.RIGHT);
			flowLayout2.setVgap(2);
			jPanel8 = new JPanel();
			jPanel8.setLayout(flowLayout2);
			jPanel8.setComponentOrientation(ComponentOrientation.UNKNOWN);
			jPanel8.setName("jPanel8");
			jPanel8.add(getUnscrobbledCountLabel(), null);
		}
		return jPanel8;
	}

	/**
	 * This method initializes toolbarPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getToolbarPanel() {
		if (toolbarPanel == null) {
			toolbarPanel = new JPanel();
			toolbarPanel.setLayout(new BoxLayout(getToolbarPanel(), BoxLayout.X_AXIS));
		}
		return toolbarPanel;
	}

	/**
	 * This method initializes jPanel9	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			jPanel9 = new JPanel();
			jPanel9.setPreferredSize(new Dimension(382, 26));
			jPanel9.setLayout(flowLayout);
			jPanel9.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanel9.add(scrobbleTracksLabel, null);
			jPanel9.add(getJPanel6(), null);
			jPanel9.add(getScrobbleTracksButton(), null);
		}
		return jPanel9;
	}

	/**
	 * This method initializes jPanel10	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(FlowLayout.LEFT);
			flowLayout1.setVgap(3);
			jPanel10 = new JPanel();
			jPanel10.setLayout(flowLayout1);
			jPanel10.add(getStatusLabel(), null);
		}
		return jPanel10;
	}

	/**
	 * This method initializes ignoreSelectedButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIgnoreSelectedButton() {
		if (ignoreSelectedButton == null) {
			ignoreSelectedButton = new JButton();
			ignoreSelectedButton.setText("Ignore");
			ignoreSelectedButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ZensesApplication.getApplication().getZenses().getViewHandler().ignoreSelectedTracks();
					//scrobbleTimeField.setText(_viewHandler.getZenses().getDeviceTrackService().getNextSubmissionTime());
				}
			});
		}
		return ignoreSelectedButton;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
