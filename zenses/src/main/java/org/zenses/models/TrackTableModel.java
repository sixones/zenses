package org.zenses.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.zenses.data.dto.DeviceTrackDto;
import org.zenses.ui.ViewHandler;

public class TrackTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1561610603232645582L;

	protected Object[] _columns = { "", "Title", "Artist", "Album", "Total Play Count", "Unscrobbled Play Count" };
	protected List<DeviceTrackDto> _tracks;
	protected boolean[] _selectedToSubmit;
	
	public TrackTableModel(List<DeviceTrackDto> tracks) {
		this._tracks = tracks;
		this._selectedToSubmit = new boolean[tracks.size()];
	}
	
	//@Override
	public int getColumnCount() {
		return this._columns.length;
	}

	//@Override
	public int getRowCount() {
		return this._tracks.size();
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return this._columns[columnIndex].toString();
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DeviceTrackDto track = this._tracks.get(rowIndex);
		
		switch (columnIndex)
		{
		case 0:
			return _selectedToSubmit[rowIndex];
		case 1:
			return track.getTitle();
		case 2:
			return track.getArtist();
		case 3:
			return track.getAlbum();
		case 4:
			return track.getPlayCount();
		case 5:
			return track.getPlayCount() - track.getSubmissions().size();
		}
		
		return "Unknown";
	}

	public DeviceTrackDto getRowDto(int rowIndex) {
		return this._tracks.get(rowIndex);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0) {
			return Boolean.class;
		}
		
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return true;
		}
		
		return false;
	}
    
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == 0){
			this._selectedToSubmit[rowIndex] = (Boolean) aValue;
			
			// refresh the info from the view handler
			new Thread() {
				public void run() {
					ViewHandler.getInstance().updateInformation();
				}
			}.start();
		} else {
			throw new RuntimeException("Unexpected call to set value");
		}
    }
}
