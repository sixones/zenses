package org.zenses.lastfm;

import java.io.IOException;
import java.util.List;

import org.zenses.data.dto.DeviceTrackDto;

public interface TracksSubmitter {

	// TODO: add another method that would take as parameter how long the breaks
	// between the tracks should be

	/**
	 * Main entrypoint - will submit all the songs to last fm and update
	 * appropriate records in the database
	 * 
	 * @param tracks
	 * @param startDateTime
	 *            format: HH:mm dd/MM/yyyy
	 * @param intervalBetweenSongs
	 *            if it is a negative value - this value will be subtracted from
	 *            song time and this will be used as interval between current
	 *            and next song. If it is 0, then song length will be used as
	 *            interval. If it is positive, the interval will be greater by
	 *            this value from song length.
	 * @throws IOException
	 */
	void updateTracks(List<DeviceTrackDto> tracks, String startDateTime, int intervalBetweenSongs) throws IOException;

	void setLastFmAuthentication(String username, String password) throws IOException;
}