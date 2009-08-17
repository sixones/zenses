package org.zenses;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdesktop.application.ResourceMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.zenses.data.dao.DeviceTrackDao;
import org.zenses.data.service.DeviceTrackService;
import org.zenses.lastfm.LastFmTracksSubmitter;
import org.zenses.lastfm.TracksSubmitter;
import org.zenses.lib.ContextProvider;
import org.zenses.models.PreferencesModel;
import org.zenses.mtp.MtpDeviceService;
import org.zenses.ui.ViewHandler;
import org.zenses.utils.Update;

public class Zenses {
	public static String version = "2.0.3b1";
	public static String versionAvailable = "";
	
	public static Update update;
	
	protected PreferencesModel _preferences;

	protected boolean _isMacOSX = false;
	protected boolean _isLinux = false;
	protected boolean _isWindows = false;

	protected boolean _servicesReady = false;
	
	protected ResourceMap _resourceMap;

	@SuppressWarnings("unchecked")
	protected MtpDeviceService _deviceService;
	protected DeviceTrackService _deviceTrackService;
	protected TracksSubmitter _tracksSubmitter;
	protected DeviceTrackDao _deviceTrackDao;

	private ViewHandler _viewHandler;
	private String _updateChanges = "";
	
	private static Zenses __instance;

	public Zenses() throws Exception {
		if (Zenses.__instance != null) {
			throw new Exception();
		}
		
		Zenses.__instance = this;
		
		this.findOperatingSystem();

		// set the database
		String dbtype = System.getProperty("dbtype");
		if (dbtype == null) {
			System.setProperty("dbtype", "hsql");
		}
		
		// check for database driver
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

		// set our desired mtp library
		if (this.isMacOSX() || this.isLinux()) {
			System.setProperty("mtplib", "linux");
		} else if (this.isWindows()) {
			System.setProperty("mtplib", "jmtp");
		}
		
		// make pointers of the lib references
		new Thread() {
			public void run() {
				Zenses.getInstance()._deviceService = ContextProvider.getDeviceService();
				Zenses.getInstance()._deviceTrackService = ContextProvider.getDeviceTrackService();
				Zenses.getInstance()._tracksSubmitter = ContextProvider.getTracksSubmitter();
				
				Zenses.getInstance()._servicesReady = true;
			}
		}.start();

		// setup our preferences store
		this._preferences = new PreferencesModel();
		this._preferences.populate();
		
		// setup the view handler
		this._viewHandler = new ViewHandler(this);
		
		new Thread() {
			public void run() {
				Zenses.getInstance().updateAvailable();
			}
		}.start();
	}
	
	public boolean getServicesReady() {
		return this._servicesReady;
	}
	
	public boolean updateAvailable() {
		if (Zenses.getInstance().checkForUpdate()) {
			String changes = Zenses.getInstance().getUpdateChanges();
			
			ViewHandler.getInstance().updateAvailable(changes);
			
			return true;
		}
		
		return false;
	}
	
	public void unauthenticate() {
		this.getPreferences().setSessionToken("");
		this.getPreferences().save();
		
		this.getViewHandler().getMainWindow().unauthenticate();
	}
	
	public String getUpdateChanges() {
		return this._updateChanges;
	}
	
	public boolean checkForUpdate() {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			
			URL url = new URL("http://sixones.com/projects/zenses/updates.xml");
			InputStream inputStream = url.openStream();
			
			Document document = documentBuilder.parse(inputStream);
			document.getDocumentElement().normalize();
			
			NodeList updates = document.getElementsByTagName("update");
			Node updateNode = updates.item(0);
			
			if (updateNode.getNodeType() == Node.ELEMENT_NODE) {
				Zenses.update = new Update();
				
				Element updateElement = (Element)updateNode;
				
				// Parse Version
				NodeList versionsList = updateElement.getElementsByTagName("version");
				Element versionElement = (Element)versionsList.item(0);
				NodeList versionChildren = versionElement.getChildNodes();
				
				Zenses.update.version = (versionChildren.item(0).getNodeValue().trim());
			
				// Parse Changes
				NodeList changesList = updateElement.getElementsByTagName("changes");
				Element changeElement = (Element)changesList.item(0);
				NodeList changeChildren = changeElement.getChildNodes();
				
				Zenses.update.changes = (changeChildren.item(0).getNodeValue().trim());
			}
				
			inputStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		System.out.println("Version Running: " + Zenses.version);
		
		if (Zenses.update != null) {
			Zenses.versionAvailable = Zenses.update.version;
		}
		
		if (Zenses.versionAvailable != "" && !Zenses.version.equalsIgnoreCase(Zenses.versionAvailable))
		{
			System.out.println("New Version Available: " + Zenses.versionAvailable);
			return true;
		}
		
		return false;
	}
	
	public boolean isAuthorised() {
		return this.authenticate(false);
	}
	
	public boolean authenticate() {
		return this.authenticate(true);
	}
	
	public boolean authenticate(boolean authenticateNow) {
		String token = this.getPreferences().getSessionToken();
		
		try {
			if (!this.getTracksSubmitter().authenticatesNow(token)) {
				if (!authenticateNow) return false;
				
				// open browser
				String requestToken = this.getTracksSubmitter().getRequestToken();

				String title = "Zenses2 Unauthenticated";
				String message = "Would you like to authenticate Zenses2 with Last.fm in your default browser?";
				Object[] options = { "Yes", "No" };
				
				int choice = JOptionPane.showOptionDialog(this.getViewHandler().getMainWindow(), message, title, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				
				if (choice == 0) {
					this.openBrowser("http://www.last.fm/api/auth/?api_key="+LastFmTracksSubmitter.API_KEY+"&token="+requestToken);
					
					title = "Authentication";
					message = "Have you successfully authenticated Zenses2 on Last.fm?";
							
					choice = JOptionPane.showOptionDialog(this.getViewHandler().getMainWindow(), message, title, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
					
					if (choice == 0) {
						this.getTracksSubmitter().authenticate();
						
						String sessionToken = this.getTracksSubmitter().getSessionToken();

						this.getPreferences().setSessionToken(sessionToken);
						this.getPreferences().save();
						
						this.getViewHandler().getMainWindow().authenticated();
						
						return true;
					}
					
					return false;
				} else {
					return false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		this.getViewHandler().getMainWindow().authenticated();
		
		return true;
	}
	
	public void openBrowser(String url) {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			
			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					desktop.browse(new URI(url));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	protected void findOperatingSystem() {
		String operatingSystem = System.getProperty("os.name").toLowerCase();

		this._isMacOSX = operatingSystem.startsWith("mac os x");
		this._isWindows = operatingSystem.contains("windows");
		this._isLinux = (!this._isMacOSX && !this._isWindows);
	}

	// Properties

	public PreferencesModel getPreferences() {
		return this._preferences;
	}

	public boolean isMacOSX() {
		return this._isMacOSX;
	}

	public boolean isLinux() {
		return this._isLinux;
	}

	public boolean isWindows() {
		return this._isWindows;
	}

	public ViewHandler getViewHandler() {
		return this._viewHandler;
	}

	@SuppressWarnings("unchecked")
	public MtpDeviceService getDeviceService() {
		return this._deviceService;
	}

	public DeviceTrackService getDeviceTrackService() {
		return this._deviceTrackService;
	}

	public TracksSubmitter getTracksSubmitter() {
		return this._tracksSubmitter;
	}

	public DeviceTrackDao getDeviceTrackDao() {
		return this._deviceTrackDao;
	}
	
	public static Zenses getInstance() {
		return Zenses.__instance;
	}

	public String getLastScrobbledDate() {
		long lastSubmission = this.getDeviceTrackService().getLastSubmissionTimestamp() + 60000;
		
		Date lastScrobbledDate = new Date(lastSubmission);
		return new SimpleDateFormat(this.getPreferences().getDateFormat()).format(lastScrobbledDate);
	}
	
	public String getLastScrobbledTime() {
		long lastSubmission = this.getDeviceTrackService().getLastSubmissionTimestamp() + 60000;
		
		Date lastScrobbledDate = new Date(lastSubmission);
		return new SimpleDateFormat("HH:mm").format(lastScrobbledDate);
	}
}
