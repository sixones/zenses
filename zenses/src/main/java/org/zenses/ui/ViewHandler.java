/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zenses.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;

import org.zenses.Zenses;
import org.zenses.ZensesApplication;
import org.zenses.data.dto.DeviceTrackDto;
import org.zenses.data.dto.LastFmSubmissionDto;
import org.zenses.models.ScrobbledTrackTableModel;
import org.zenses.models.TrackTableModel;
import org.zenses.mtp.MtpDevice;
import org.zenses.mtp.MtpDeviceTrack;
import org.zenses.ui.views.MainWindow;
import org.zenses.ui.views.PreferencesWindow;

/**
 *
 * @author sixones
 */
public class ViewHandler
{
	public static int TAB_SUMMARY = 0;
	public static int TAB_TRACKS = 1;
	public static int HISTORY = 2;
	
    protected MainWindow _mainWindow;
    protected PreferencesWindow _preferencesWindow;
    
    private Zenses _zenses;
    protected List<DeviceTrackDto> _tracksToScrobble;

    public ViewHandler(Zenses zenses)
    {
        this._zenses = zenses;
        
        this.setupUserInterface();
    }

	public void showMainWindow() {
		this.bindHistoryToDatabase();
		
		ZensesApplication.getApplication().show(this._mainWindow);
		
		this.updateSummaryMessage();
		this.bindTracksToDatabase();
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
	
	public PreferencesWindow getPreferencesWindow() {
		return this._preferencesWindow;
	}
	
	public void findDevices() {
		this.updateStatusMessage("Searching for devices ...");
		
		@SuppressWarnings("unchecked")
		List<MtpDevice> devices = this._zenses.getDeviceService().getDevices();
		
		if (!devices.isEmpty()) {
			for (MtpDevice<?> device : devices) {
				this._mainWindow.getConnectedDevicesComboBox().addItem(device);
			}
			
			this._mainWindow.getConnectedDevicesComboBox().setEnabled(true);
			
			this.updateStatusMessage("Found " + devices.size() + " device(s)");
		} else {
			this._mainWindow.getConnectedDevicesComboBox().setEnabled(false);
			
			this.updateStatusMessage("No devices could be found");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fetchTracksFromSelectedDevice() {
		MtpDevice device = (MtpDevice) this._mainWindow.getConnectedDevicesComboBox().getSelectedItem();
		this.updateStatusMessage("Connecting to device ...");
		
		List<MtpDeviceTrack> tracks = this._zenses.getDeviceService().getTracks(device);
		
		if (!tracks.isEmpty()) {
			for (MtpDeviceTrack track : tracks) {
				try {
					this._zenses.getDeviceTrackService().createOrUpdate(track);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		this.bindTracksToDatabase();
		this.updateStatusMessage("Device tracks collected and ready for scrobbling");
	}
	
	private void bindTracksToDatabase() {
		List<DeviceTrackDto> tracks = this._zenses.getDeviceTrackService().getUnsubmittedTracks();
		
		TrackTableModel tableModel = new TrackTableModel(tracks);
		
		this._mainWindow.getUnscrobbledTracksTable().setModel(tableModel);
		
		int columnWidth = this._mainWindow.getUnscrobbledTracksTable().getWidth() / tableModel.getColumnCount() + 1;
		
		this.updateStatusMessage(tracks.size() + " unscrobbled tracks found");
		
		// resize the columns
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			TableColumn column = this._mainWindow.getUnscrobbledTracksTable().getColumnModel().getColumn(i);
			
			if (i == 0) {
				column.setPreferredWidth(15);
			} else if (i == 2) {
				column.setPreferredWidth((columnWidth * 2) + (columnWidth - 15));
			} else {
				column.setPreferredWidth(columnWidth);
			}
		}
	}
	
	private void bindHistoryToDatabase() {
		List<LastFmSubmissionDto> tracks = this._zenses.getDeviceTrackService().getScrobbledTracks();
		
		ScrobbledTrackTableModel tableModel = new ScrobbledTrackTableModel(tracks);
		
		this._mainWindow.getScrobbledHistoryTable().setModel(tableModel);
		
		int columnWidth = this._mainWindow.getScrobbledHistoryTable().getWidth() / tableModel.getColumnCount() + 1;
		
		this.updateStatusMessage(tracks.size() + " already scrobbled tracks found");
		
		// resize the columns
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			TableColumn column = this._mainWindow.getScrobbledHistoryTable().getColumnModel().getColumn(i);
			
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
	
	public void scrobbleSelectedTracks() {
		this._tracksToScrobble = new ArrayList<DeviceTrackDto>();
		
		this.updateStatusMessage("Calculating tracks to scrobble");
		
		if (this.getZenses().getPreferences().get_lastfmUsername() != "" && this.getZenses().getPreferences().get_lastfmPassword() != "") {
			try {
				this.getZenses().getTracksSubmitter().setLastFmAuthentication(this.getZenses().getPreferences().get_lastfmUsername(), this.getZenses().getPreferences().get_lastfmPassword());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				this.showError("Failed to login to Last.fm as " + this.getZenses().getPreferences().get_lastfmUsername());
				
				return;
			}
		}
		
		for (int i = 0; i < this.getMainWindow().getUnscrobbledTracksTable().getRowCount(); i++) {
			Object val = this.getMainWindow().getUnscrobbledTracksTable().getModel().getValueAt(i, 0);

			if (val instanceof Boolean && (new Boolean(val.toString())) == true) {
				DeviceTrackDto track = ((TrackTableModel)this.getMainWindow().getUnscrobbledTracksTable().getModel()).getRowDto(i);
				
				this._tracksToScrobble.add(track);
			}
		}
		
		if (!this._tracksToScrobble.isEmpty()) {
			this.updateStatusMessage("Attempting to scrobble " + this._tracksToScrobble.size() + " tracks");
			
			String startDateTime = this.getMainWindow().getScrobbleTimeField().getText(); 
			
			try {
				this._zenses.getTracksSubmitter().updateTracks(this._tracksToScrobble, startDateTime, this.getZenses().getPreferences().getIntervalBetweenScrobbles());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
			this.updateStatusMessage("Completed scrobbling " + this._tracksToScrobble.size() + " tracks");
			
			this.bindTracksToDatabase();
			this.bindHistoryToDatabase();
		} else {
			this.updateStatusMessage("No tracks selected to scrobble");
		}
	}
	
	private void updateStatusMessage(String message) {
		this._mainWindow.getStatusLabel().setText(message);
	}
	
	public void updateSummaryMessage() {
		String message = "";
		int count = this.getZenses().getDeviceTrackService().getScrobbledTracksCount();
		
		if (count == 0) {
			message = "Nothing scrobbled yet :(";
		} else if (count == 1) {
			message = "Scrobbled only " + this.getZenses().getDeviceTrackService().getScrobbledTracksCount() + " track";
		} else {
			message = "Scrobbled a total of " + this.getZenses().getDeviceTrackService().getScrobbledTracksCount() + " tracks!";
		}

		this.getMainWindow().getSummaryTextLabel().setText(message);
	}
    
	protected void setupUserInterface() {
		this._mainWindow = new MainWindow(this);
		this._preferencesWindow = new PreferencesWindow();
	}

	public void set_zenses(Zenses _zenses) {
		this._zenses = _zenses;
	}

	public Zenses getZenses() {
		return _zenses;
	}
}
