package org.zenses;

import org.jdesktop.application.ResourceMap;
import org.zenses.data.dao.DeviceTrackDao;
import org.zenses.data.service.DeviceTrackService;
import org.zenses.lastfm.TracksSubmitter;
import org.zenses.lib.ContextProvider;
import org.zenses.models.PreferencesModel;
import org.zenses.mtp.MtpDeviceService;
import org.zenses.ui.ViewHandler;

public class Zenses {
	protected PreferencesModel _preferences;

	protected boolean _isMacOSX = false;
	protected boolean _isLinux = false;
	protected boolean _isWindows = false;

	protected ResourceMap _resourceMap;

	@SuppressWarnings("unchecked")
	protected MtpDeviceService _deviceService;
	protected DeviceTrackService _deviceTrackService;
	protected TracksSubmitter _tracksSubmitter;
	protected DeviceTrackDao _deviceTrackDao;

	private ViewHandler _viewHandler;

	public Zenses() {
		this.findOperatingSystem();

		// set the database
		String dbtype = System.getProperty("dbtype");
		if (dbtype == null) {
			System.setProperty("dbtype", "hsql");
		}
		
		//try {
			//Class.forName("org.hsqldb.jbdcDriver");
		//} catch (ClassNotFoundException e) {
			//e.printStackTrace();
		//}

		// set our desired mtp library
		if (this.isMacOSX() || this.isLinux()) {
			System.setProperty("mtplib", "linux");
		} else if (this.isWindows()) {
			System.setProperty("mtplib", "jmtp");
		}

		// make pointers of the lib references
		this._deviceService = ContextProvider.getDeviceService();
		this._deviceTrackService = ContextProvider.getDeviceTrackService();
		this._tracksSubmitter = ContextProvider.getTracksSubmitter();

		// setup our preferences store
		this._preferences = new PreferencesModel();
		this._preferences.populate();
		
		// setup the view handler
		this._viewHandler = new ViewHandler(this);
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
}
