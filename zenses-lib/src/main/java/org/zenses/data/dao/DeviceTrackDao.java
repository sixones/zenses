package org.zenses.data.dao;

import java.util.Date;
import java.util.List;

import org.zenses.data.dto.DeviceTrackDto;
import org.zenses.data.dto.LastFmSubmissionDto;

/**
 * Just a very simple DAO for saving and retrieving songs and last.fm
 * submissions
 * 
 * @author lilith
 * 
 */
public interface DeviceTrackDao {

	/**
	 * Finds the song basing on device persistent id and device id
	 * 
	 * @param persistentId
	 * @param deviceId
	 * @return
	 */
	DeviceTrackDto findByPersistentId(String persistentId, String deviceId);

	/**
	 * Persists last.fm submission
	 * 
	 * @param submission
	 */
	void save(LastFmSubmissionDto submission);

	/**
	 * Saves or updates. Yes, really! :)
	 * 
	 * @param deviceTrackDto
	 */
	void saveOrUpdate(DeviceTrackDto deviceTrackDto);

	/**
	 * Returns the list of tracks for which the number of submissions to last.fm
	 * is lower than the play count
	 * 
	 * @return
	 */
	List<DeviceTrackDto> getUnsubmittedTracks();

	List<LastFmSubmissionDto> getScrobbledTracks();

	int getScrobbledTracksCount();

	/**
	 * Returns a submission that has most recent time. It will be helpful to
	 * validate that we won't use playtime that's before already submitted
	 * tracks - last.fm doesn't like it
	 * 
	 * @return
	 */
	Date getMostRecentSubmission();

	/**
	 * !!!! Do NOT use !!!! this method outside of tests unless you REALLY know
	 * exactly what you're doing
	 * 
	 * @param entity
	 * @return
	 */
	DeviceTrackDto flushAndReload(DeviceTrackDto entity);

}