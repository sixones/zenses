package org.zenses.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.zenses.data.dto.LastFmSubmissionDto;

public class ScrobbledTrackTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -69676278993105429L;
	
	protected String[] _columns = { "Title", "Artist", "Album", "Submissions", "Date Submitted" };
	protected List<LastFmSubmissionDto> _tracks;
	protected boolean[] _selectedToSubmit;
	
	public ScrobbledTrackTableModel(List<LastFmSubmissionDto> tracks) {
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
		LastFmSubmissionDto track = this._tracks.get(rowIndex);
		
		switch (columnIndex)
		{
		case 0:
			return track.getTrack().getTitle();
		case 1:
			return track.getTrack().getArtist();
		case 2:
			return track.getTrack().getAlbum();
		case 3:
			return track.getTrack().getSubmissions().size();
		case 4:
			return track.getDateSubmitted();
		}
		
		return "Unknown";
	}

	public LastFmSubmissionDto getRowDto(int rowIndex) {
		return this._tracks.get(rowIndex);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
    
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		throw new RuntimeException("Unexpected call to set value");
    }
}
