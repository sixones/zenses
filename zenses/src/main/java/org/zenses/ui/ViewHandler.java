package org.zenses.ui;

import java.awt.Dimension;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.zenses.Zenses;
import org.zenses.ZensesApplication;
import org.zenses.data.dto.DeviceTrackDto;
import org.zenses.data.dto.LastFmSubmissionDto;
import org.zenses.models.ScrobbledTrackTableModel;
import org.zenses.models.TrackTableModel;
import org.zenses.mtp.MTPException;
import org.zenses.mtp.MtpDevice;
import org.zenses.mtp.MtpDeviceTrack;
import org.zenses.ui.views.AboutWindow;
import org.zenses.ui.views.MainWindow;
import org.zenses.ui.views.PreferencesWindow;
import org.zenses.ui.views.UpdateWindow;
import org.zenses.utils.CustomThread;
import org.zenses.utils.DataTimerTask;

public class ViewHandler
{
	private final static Logger logger = Logger.getLogger(ViewHandler.class);

	public static int TAB_SUMMARY = 0;
	public static int TAB_TRACKS = 1;
	public static int HISTORY = 2;
	
    protected MainWindow _mainWindow;
    protected PreferencesWindow _preferencesWindow;
    protected AboutWindow _aboutWindow;
    protected UpdateWindow _updateWindow;
    
    private Zenses _zenses;
    protected List<DeviceTrackDto> _tracksToScrobble;
    protected Timer _uiUpdateTimer;
    protected Timer _findDevicesTimer;
    protected Timer _devicesUpdateTimer;
    
    private static ViewHandler __instance;

    public ViewHandler(Zenses zenses) throws Exception
    {
		if (ViewHandler.__instance != null) {
			throw new Exception();
		}
		
		ViewHandler.__instance = this;
    	
        this._zenses = zenses;
        
        this.setupUserInterface();
    }
    
    public synchronized void updateUI() {
    	this.updateUI(true);
    }
    
    public synchronized void updateUI(boolean updateStateMessage) {
    	if (!this.getZenses().getServicesReady()) {
    		if (this._uiUpdateTimer == null) {
				this._uiUpdateTimer = new Timer();
    		}
    			
    		this._uiUpdateTimer.schedule(new DataTimerTask<Boolean>(updateStateMessage) {
				public void run() {
					ViewHandler.getInstance().updateUI(this.getData());
				}
			}, 500);
			
			return;
    	}
    	
    	if (this._uiUpdateTimer != null) {
    		this._uiUpdateTimer.cancel();
    	}
    	
    	new CustomThread<Boolean>(updateStateMessage) {
    		public void run() {
    			ViewHandler.getInstance().bindUnscrobbledTrable(this.getData());
    			ViewHandler.getInstance().bindHistoryTable(!this.getData());
    			ViewHandler.getInstance().updateSummaryMessage();
    		}
    	}.start();
    	
    	this.getMainWindow().getScrobbleDateField().setText(Zenses.getInstance().getNextScrobbledDate());
    	this.getMainWindow().getScrobbleTimeField().setText(Zenses.getInstance().getNextScrobbledTime());
    }
    
    public synchronized void findDevices() {
    	if (!this.getZenses().getServicesReady()) {
			this._devicesUpdateTimer = new Timer();
			this._devicesUpdateTimer.schedule(new TimerTask() {
				public void run() {
					ViewHandler.getInstance().findDevices();
				}
			}, 500);
			
			return;
    	}
    	
    	if (this._devicesUpdateTimer != null) {
    		this._devicesUpdateTimer.cancel();
    	}
    	
    	new Thread() {
    		public void run() {
    			ViewHandler.getInstance().updateStateMessage("Searching for devices ...");
    			
    			@SuppressWarnings("unchecked")
    			List<MtpDevice> devices = Zenses.getInstance().getDeviceService().getDevices();
    			
    			ViewHandler.getInstance().getMainWindow().getConnectedDevicesComboBox().removeAllItems();
    			
    			if (!devices.isEmpty()) {
    				for (MtpDevice<?> device : devices) {
    					ViewHandler.getInstance().getMainWindow().getConnectedDevicesComboBox().addItem(device);
    				}
    				
    				ViewHandler.getInstance().getMainWindow().getConnectedDevicesComboBox().setEnabled(true);
    				
    				ViewHandler.getInstance().updateStateMessage("Found " + devices.size() + " device(s)");
    			} else {
    				ViewHandler.getInstance().getMainWindow().getConnectedDevicesComboBox().setEnabled(false);
    				
    				ViewHandler.getInstance().updateStateMessage("No devices could be found");
    			}
    		}
    	}.start();
    }

	public void showMainWindow() {
		ZensesApplication.getApplication().show(this._mainWindow);
		
		new Thread() {
			public void run() {
				ViewHandler.getInstance().updateUI();
			}
		}.start();	
	}
	
	public void showAboutWindow() {
		this._aboutWindow.setAlwaysOnTop(true);
		this._aboutWindow.setVisible(true);
		this._aboutWindow.setLocationRelativeTo(this._mainWindow);
	}

	public void showPreferencesView() {
		this._preferencesWindow.setAlwaysOnTop(true);
		this._preferencesWindow.setPreferences();
		this._preferencesWindow.setVisible(true);
		this._preferencesWindow.setLocationRelativeTo(null);
	}
	
	public void hidePreferencesWindow(Boolean savePreferences) {
		if (savePreferences) {
			this._preferencesWindow.savePreferences();
		}
		
		this._preferencesWindow.setVisible(false);
	}
	
	public MainWindow getMainWindow() {
		return this._mainWindow;
	}
	
	public void hideUpdateWindow() {
		this._updateWindow.setVisible(false);
		this._updateWindow.dispose();
	}
	
	public PreferencesWindow getPreferencesWindow() {
		return this._preferencesWindow;
	}
	
	/*public void findDevices() {
    	if (!this.getZenses().getServicesReady()) {
			this._findDevicesTimer = new Timer();
			this._findDevicesTimer.schedule(new TimerTask() {
				public void run() {
					ViewHandler.getInstance().findDevices();
				}
			}, 500);
			
			return;
    	}
    	
    	if (this._findDevicesTimer != null) {
    		this._findDevicesTimer.cancel();
    	}
		
		this.updateStateMessage("Searching for devices ...");
		
		@SuppressWarnings("unchecked")
		List<MtpDevice> devices = this._zenses.getDeviceService().getDevices();
		
		this._mainWindow.getConnectedDevicesComboBox().removeAllItems();
		
		if (!devices.isEmpty()) {
			for (MtpDevice<?> device : devices) {
				this._mainWindow.getConnectedDevicesComboBox().addItem(device);
			}
			
			this._mainWindow.getConnectedDevicesComboBox().setEnabled(true);
			
			this.updateStateMessage("Found " + devices.size() + " device(s)");
		} else {
			this._mainWindow.getConnectedDevicesComboBox().setEnabled(false);
			
			this.updateStateMessage("No devices could be found");
		}
	}*/
	
	@SuppressWarnings("unchecked")
	public synchronized void fetchTracksFromSelectedDevice() {
		new Thread() {

			public void run() {
				MtpDevice device = (MtpDevice) ViewHandler.getInstance().getMainWindow().getConnectedDevicesComboBox().getSelectedItem();
				ViewHandler.getInstance().updateStateMessage("Connecting to device ...");
				
				List<MtpDeviceTrack> tracks;
				
				try
				{
					tracks = ZensesApplication.getApplication().getZenses().getDeviceService().getTracks(device);
				}
				catch (MTPException e)
				{
					ViewHandler.getInstance().updateStateMessage("Failed.");
					ViewHandler.getInstance().showError(e.getMessage() + "\nTry to reboot the device and make sure its connected.");
					
					return;
				}

				ViewHandler.getInstance().updateStateMessage("Fetching track information from device ...");
				
				if (!tracks.isEmpty()) {
					for (int i = 0; i < tracks.size(); i++) {
						MtpDeviceTrack track = tracks.get(i);
						try {
							logger.info("Updating track " + i + " out of " + tracks.size());
							Zenses.getInstance().getDeviceTrackService().createOrUpdate(track);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				ViewHandler.getInstance().updateUI();
				ViewHandler.getInstance().updateStateMessage("Device tracks collected and ready for scrobbling");
			}
		}.start();
	}
	
	private synchronized void bindUnscrobbledTrable(boolean updateStateMessage) {
		List<DeviceTrackDto> tracks = this.getZenses().getDeviceTrackService().getUnsubmittedTracks();
		
		TrackTableModel tableModel = new TrackTableModel(tracks);
		JTable table = this.getMainWindow().getUnscrobbledTracksTable();
		
		table.setModel(tableModel);
		table.setRowHeight(20);
		table.setRowMargin(2);
		table.setIntercellSpacing(new Dimension(5, 5));
		
		int columnWidth = table.getWidth() / tableModel.getColumnCount() + 1;
		
		if (updateStateMessage) {
			this.updateStateMessage(tracks.size() + " unscrobbled tracks found");
		}
		
		// resize the columns
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			TableColumn column = table.getColumnModel().getColumn(i);

			if (i == 0) {
				column.setHeaderRenderer(new CheckboxHeader(new CheckboxHeaderListener(table)));
				column.setMaxWidth(25);
			} else if (i == 1) {
				column.setPreferredWidth((columnWidth * 2) + (columnWidth - 25));
			} else {
				column.setPreferredWidth(columnWidth);
			}
		}
	}
	
	private void bindHistoryTable(boolean updateStateMessage) {
		List<LastFmSubmissionDto> tracks = this.getZenses().getDeviceTrackService().getScrobbledTracks();
		
		ScrobbledTrackTableModel tableModel = new ScrobbledTrackTableModel(tracks);
		JTable table = this.getMainWindow().getScrobbledHistoryTable();
		
		table.setModel(tableModel);
		table.setRowHeight(20);
		table.setRowMargin(2);
		table.setIntercellSpacing(new Dimension(5, 5));
		
		int columnWidth = this.getMainWindow().getScrobbledHistoryTable().getWidth() / tableModel.getColumnCount() + 1;
		
		if (updateStateMessage) {
			this.updateStateMessage(tracks.size() + " already scrobbled tracks found");
		}
		
		// resize the columns
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			TableColumn column = this.getMainWindow().getScrobbledHistoryTable().getColumnModel().getColumn(i);
			
			if (i == 0) {
				column.setPreferredWidth((columnWidth * 2) + (columnWidth - 15));
			} else {
				column.setPreferredWidth(columnWidth);
			}
		}
	}
	
	public void showError(String message) {
		JOptionPane.showMessageDialog(this.getMainWindow(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
	}
	
	public long getScrobbleFrom() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm "+this.getZenses().getPreferences().getDateFormat());
		long scrobbleFromMilliseconds = formatter.parseMillis(this.getMainWindow().getScrobbleTimeField().getText()+" "+this.getMainWindow().getScrobbleDateField().getText());
		
		return scrobbleFromMilliseconds;
	}
	
	public void updateAvailable(String changes) {
		this._updateWindow = new UpdateWindow();
		this._updateWindow.updateVersionMessage();
		this._updateWindow.updateChanges();
		
		this._updateWindow.setVisible(true);
		this._updateWindow.setAlwaysOnTop(true);
		this._updateWindow.setLocationRelativeTo(this.getMainWindow());
	}
	
	public void scrobbleSelectedTracks() {
		this._tracksToScrobble = new ArrayList<DeviceTrackDto>();
		
		//this.getZenses().authenticate();
		
		if (!this.getZenses().isAuthorised()) {
			this.showError("Please autheticate with Last.fm before attempting to scrobble tracks.");
			
			return;
		}
		
		this.updateStateMessage("Validating scrobbles");
		
		Date scrobbleFromDate = new Date(this.getScrobbleFrom());
		String scrobbleFromOriginal = new SimpleDateFormat("hh:mma d MMMM yyyy").format(scrobbleFromDate);

		if (scrobbleFromDate.before(this.getZenses().getLastScrobbledDateTime())) {
			this.showError("You must choose a date to scrobble from that is after your last scrobble to Last.fm, which was " + this.getZenses().getLastScrobbledTime() +" "+ this.getZenses().getLastScrobbledDate());
			
			this.getMainWindow().getScrobbleDateField().setText(Zenses.getInstance().getNextScrobbledDate());
	    	this.getMainWindow().getScrobbleTimeField().setText(Zenses.getInstance().getNextScrobbledTime());
			
			this.updateStateMessage("Scrobbles failed validation");
			
			return;
		}
		
		this.updateStateMessage("Calculating tracks to scrobble");

		for (int i = 0; i < this.getMainWindow().getUnscrobbledTracksTable().getRowCount(); i++) {
			Object val = this.getMainWindow().getUnscrobbledTracksTable().getModel().getValueAt(i, 0);

			if (val instanceof Boolean && (new Boolean(val.toString())) == true) {
				DeviceTrackDto track = ((TrackTableModel)this.getMainWindow().getUnscrobbledTracksTable().getModel()).getRowDto(i);
				
				this._tracksToScrobble.add(track);
			}
		}
		
		if (!this._tracksToScrobble.isEmpty()) {
			String title = "Confirm Scrobbles";
			String message = "Are you sure you want to scrobble "+this._tracksToScrobble.size()+" tracks from "+ scrobbleFromOriginal +"?";
			Object[] options = { "Yes", "No" };
			
			int choice = JOptionPane.showOptionDialog(this.getMainWindow(), message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			
			if (choice == 1) {
				this.updateStateMessage("Cancelled scrobbling tracks");
				return;
			}
			
			this.updateStateMessage("Attempting to scrobble " + this._tracksToScrobble.size() + " tracks");
		
			new CustomThread<List<DeviceTrackDto>>(this._tracksToScrobble) {
				public void run() {
					Date scrobbleFromDate = new Date(ViewHandler.getInstance().getScrobbleFrom());
					String scrobbleFrom = new SimpleDateFormat("HH:mm dd/MM/yyyy").format(scrobbleFromDate);
		
					try {
						Zenses.getInstance().getTracksSubmitter().updateTracks(this.getData(), scrobbleFrom, Zenses.getInstance().getPreferences().getIntervalBetweenScrobbles());
						ViewHandler.getInstance().updateStateMessage("Completed scrobbling " + this.getData().size() + " tracks");
					} catch (IllegalArgumentException e) {
						ViewHandler.getInstance().showError(e.getMessage());
						ViewHandler.getInstance().updateStateMessage("Failed scrobbling " + this.getData().size() + " tracks");
					} catch (IOException e) {
						ViewHandler.getInstance().showError(e.getMessage());
						ViewHandler.getInstance().updateStateMessage("Failed scrobbling " + this.getData().size() + " tracks");
					} finally {
						ViewHandler.getInstance().updateUI(false);
					}
				}
			}.start();
		} else {
			this.updateStateMessage("No tracks selected to scrobble");
		}
	}
	
	public static ViewHandler getInstance() {
		return ViewHandler.__instance;
	}

	public void updateStateMessage(String message) {
		this._mainWindow.getStatusLabel().setText(message);
	}
	
	private void updateSummaryMessage() {
		String message = "";
		
		int count = this.getZenses().getDeviceTrackService().getScrobbledTracksCount();
		
		if (count == 0) {
			message = "Nothing scrobbled yet";
		} else if (count == 1) {
			message = "Scrobbled only " + this.getZenses().getDeviceTrackService().getScrobbledTracksCount() + " track";
		} else {
			message = "Scrobbled a total of " + this.getZenses().getDeviceTrackService().getScrobbledTracksCount() + " tracks!";
		}

		this.getMainWindow().getSummaryTextLabel().setText(message);
		
		if (this.getZenses().authenticate(false)) {
			this.getMainWindow().authenticated();
		}
	}
    
	protected void setupUserInterface() {
		this._mainWindow = new MainWindow(this);
		this._preferencesWindow = new PreferencesWindow();
		this._aboutWindow = new AboutWindow();
	}

	public Zenses getZenses() {
		return _zenses;
	}
}
