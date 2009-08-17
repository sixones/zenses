package org.zenses.ui.views;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import org.zenses.Zenses;
import org.zenses.ZensesApplication;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;

public class AboutWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel zensesLabel = null;
	private JLabel versionLabel = null;
	private JPanel jPanel = null;
	private JLabel websiteLabel = null;

	/**
	 * This is the default constructor
	 */
	public AboutWindow() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("About Zenses2");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			websiteLabel = new JLabel();
			websiteLabel.setText("http://getzenses.com/");
			websiteLabel.setPreferredSize(new Dimension(121, 30));
			websiteLabel.setHorizontalAlignment(SwingConstants.CENTER);
			websiteLabel.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					ZensesApplication.getApplication().getZenses().openBrowser("http://getzenses.com");
				}
			});
			versionLabel = new JLabel();
			versionLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			versionLabel.setText(Zenses.version);
			versionLabel.setForeground(new Color(153, 153, 153));
			versionLabel.setHorizontalAlignment(SwingConstants.CENTER);
			zensesLabel = new JLabel();
			zensesLabel.setText("Zenses2");
			zensesLabel.setHorizontalAlignment(SwingConstants.CENTER);
			zensesLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
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
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 2;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 1;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setPreferredSize(new Dimension(121, 104));
			jPanel.add(zensesLabel, new GridBagConstraints());
			jPanel.add(versionLabel, gridBagConstraints);
			jPanel.add(websiteLabel, gridBagConstraints1);
		}
		return jPanel;
	}

}
