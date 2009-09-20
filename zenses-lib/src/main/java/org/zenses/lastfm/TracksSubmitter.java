package org.zenses.lastfm;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.zenses.data.dto.DeviceTrackDto;

public interface TracksSubmitter {

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

	/**
	 * Main entrypoint - will submit all the songs to last fm and update
	 * appropriate records in the database with interval between them set to
	 * constant value (15sec)
	 * 
	 * @param tracks
	 * @param startDateTime
	 *            format: HH:mm dd/MM/yyyy
	 * 
	 * @throws IOException
	 */
	void updateTracks(List<DeviceTrackDto> tracks, String startDateTime) throws IOException;

	boolean authenticatesNow(String token) throws IOException;

	void authenticate() throws IOException;

	String getSessionApiKey();

	String getSessionToken();

	String getSessionUsername();

	boolean isSubscriber();

	String getRequestToken();

	String getNextSubmissionTime();

	public Date getLastSubmissionDateTime();

	/**
	 * Will create fake entries in the db to make the tracks ignored
	 * @param tracks
	 */
	void ignoreTracks(List<DeviceTrackDto> tracks);
}