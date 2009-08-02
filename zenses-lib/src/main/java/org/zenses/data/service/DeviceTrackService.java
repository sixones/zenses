package org.zenses.data.service;

import java.util.List;

import org.zenses.data.dto.DeviceTrackDto;
import org.zenses.data.dto.LastFmSubmissionDto;
import org.zenses.mtp.MtpDeviceTrack;

/**
 * @author lilith
 * 
 */
public interface DeviceTrackService {

	/**
	 * Will try to get the track from DB and update its playcount, or will
	 * create it if it didn't exist. The search will base on track persistent
	 * id.
	 * 
	 * @param dt
	 */
	void createOrUpdate(MtpDeviceTrack dt);

	/**
	 * Will create and store information that given track has been submitted to last.fm at given time.
	 * @param dt
	 * @param submittedTime
	 */
	void addSubmission(DeviceTrackDto dt, long submittedTime);

	/**
	 * Will check what is the last submission time
	 * @return
	 */
	long getLastSubmissionTimestamp();

	//TODO: add version of this method with filtering on device id
	public List<DeviceTrackDto> getUnsubmittedTracks();
	
	public List<LastFmSubmissionDto> getScrobbledTracks();
	
	public int getScrobbledTracksCount();

	String getNextSubmissionTime();

}
