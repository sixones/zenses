package org.zenses.lastfm;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
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
		long lastSubmissionTimestamp = deviceTrackService.getLastSubmissionTimestamp();
		Assert.isTrue(lastSubmissionTimestamp < newSubmissionTimestamp,
				"New submissions must be later than existing ones!");
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
	public void setLastFmAuthentication(String username, String password) throws IOException {
		submitter.initializeScrobbler(username, password);
	}
}
