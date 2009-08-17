package org.zenses.lastfm;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.zenses.data.dto.DeviceTrackDto;
import org.zenses.data.service.DeviceTrackService;

public class TracksSubmitterImpl implements TracksSubmitter {

	@Autowired
	private DeviceTrackService deviceTrackService;

	@Autowired
	private LastFmTracksSubmitter submitter;

	private Logger logger = Logger.getLogger(TracksSubmitterImpl.class);

	public void updateTracks(List<DeviceTrackDto> tracks, String startDateTime, int intervalBetweenSongs) throws IOException {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm dd/MM/yyyy");
		long newSubmissionTimestamp = formatter.parseMillis(startDateTime);
		
		// Doesnt seem to be needed, last.fm is letting us scrobble tracks earlier!
		/*long lastSubmissionTimestamp = this.deviceTrackService.getLastSubmissionTimestamp();
		
		Date lastSubmissionDate = new Date(lastSubmissionTimestamp);
		DateFormat format = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
		
		Assert.isTrue(lastSubmissionTimestamp < newSubmissionTimestamp,
				"New submissions must be later than "+format.format(lastSubmissionDate));*/
		
		long lastFmSubmissionStart = newSubmissionTimestamp / 1000;
		
		for (DeviceTrackDto dto : tracks) {
			for (int i = 0; i < dto.getPlayCount() - dto.getSubmissions().size(); i++) {
				try {
					submitter.submitSingleTrack(dto, lastFmSubmissionStart);
					deviceTrackService.addSubmission(dto, lastFmSubmissionStart);
					logger.info("Submitted " + dto);
					lastFmSubmissionStart += (dto.getLength().intValue() / 1000 + intervalBetweenSongs);
				} catch (ConnectException e) {
					logger.error(e.getMessage() + " Track not submitted: " + dto);
				} catch (IllegalArgumentException iae) {
					logger.error(iae.getMessage() + " Track not submitted: " + dto);
				}
			}
		}
	}

	@Override
	public boolean authenticatesNow(String token) throws IOException {
		return submitter.authenticatesNow(token);
	}
	
	@Override
	public void authenticate() throws IOException {
		submitter.authenticate();
	}
	
	@Override
	public String getSessionToken() {
		return this.submitter.getSession().getKey();
	}
	
	@Override
	public String getRequestToken() {
		return this.submitter.getRequestToken();
	}
	
	@Override
	public String getSessionUsername() {
		return this.submitter.getSessionUser().getName();
	}
	
	@Override
	public boolean isSubscriber() {
		return this.submitter.getSessionUser().isSubscriber();
	}
	
	@Override
	public String getSessionApiKey() {
		return this.submitter.getSession().getApiKey();
	}
	
	@Override
	public String getNextSubmissionTime() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm dd/MM/yyyy");
		
		return formatter.print(this.deviceTrackService.getLastSubmissionTimestamp() + 5000);
	}
}
