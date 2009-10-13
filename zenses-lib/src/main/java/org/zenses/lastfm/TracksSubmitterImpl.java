package org.zenses.lastfm;

import java.io.IOException;
import java.net.ConnectException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.zenses.data.dto.DeviceTrackDto;
import org.zenses.data.service.DeviceTrackService;

public class TracksSubmitterImpl implements TracksSubmitter {

	private interface IntervalProvider {

		long getInterval(DeviceTrackDto dto);

	}

	@Autowired
	private DeviceTrackService deviceTrackService;

	@Autowired
	private LastFmTracksSubmitter submitter;

	private Logger logger = Logger.getLogger(TracksSubmitterImpl.class);

	@Override
	public void ignoreTracks(List<DeviceTrackDto> tracks) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm dd/MM/yyyy");
		long newSubmissionTimestamp = formatter.parseMillis("01:01 01/01/2001");
		long lastFmSubmissionStart = newSubmissionTimestamp / 1000;

		for (DeviceTrackDto dto : tracks) {
			for (int i = 0; i < dto.getPlayCount() - dto.getSubmissions().size(); i++) {
				deviceTrackService.addSubmission(dto, lastFmSubmissionStart);
				logger.info("Faked submission " + dto);
			}
		}

	}

	private void updateTracks(List<DeviceTrackDto> tracks, String startDateTime, IntervalProvider intervalProvider,
			boolean goBackInTime) throws IOException {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm dd/MM/yyyy");
		long newSubmissionTimestamp = formatter.parseMillis(startDateTime);

		//DON'T HAVE TO CHECK THIS ANYMORE
//		long lastSubmissionTimestamp = this.deviceTrackService.getLastSubmissionTimestamp();
//		Date lastSubmissionDate = new Date(lastSubmissionTimestamp);
//		DateFormat format = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
//		Assert.isTrue(lastSubmissionTimestamp < newSubmissionTimestamp, "New submissions must be later than "
//				+ format.format(lastSubmissionDate));

		long lastFmSubmissionStart = newSubmissionTimestamp / 1000;

		for (DeviceTrackDto dto : tracks) {
			for (int i = 0; i < dto.getPlayCount() - dto.getSubmissions().size(); i++) {
				try {
					submitter.submitSingleTrack(dto, lastFmSubmissionStart);
					deviceTrackService.addSubmission(dto, lastFmSubmissionStart);
					logger.info("Submitted " + dto);
					if (goBackInTime) {
						lastFmSubmissionStart -= intervalProvider.getInterval(dto);
					} else {
						lastFmSubmissionStart += intervalProvider.getInterval(dto);
					}
				} catch (ConnectException e) {
					logger.error(e.getMessage() + " Track not submitted: " + dto);
				} catch (IllegalArgumentException iae) {
					logger.error(iae.getMessage() + " Track not submitted: " + dto);
				}
			}
		}
	}

	/**
	 * @deprecated Use {@link
	 *             #updateTracks(List<DeviceTrackDto>,String,int,boolean)}
	 *             instead
	 */
	public void updateTracks(List<DeviceTrackDto> tracks, String startDateTime, int intervalBetweenSongs)
			throws IOException {
		updateTracks(tracks, startDateTime, intervalBetweenSongs, false);
	}

	public void updateTracks(List<DeviceTrackDto> tracks, String startDateTime, final int intervalBetweenSongs,
			boolean goBackInTime) throws IOException {
		updateTracks(tracks, startDateTime, new IntervalProvider() {
			@Override
			public long getInterval(DeviceTrackDto dto) {
				return dto.getLength().intValue() / 1000 + intervalBetweenSongs;
			}
		}, goBackInTime);
	}

	/**
	 * @deprecated Use {@link
	 *             #updateTracks(List<DeviceTrackDto>,String,boolean)} instead
	 */
	@Override
	public void updateTracks(List<DeviceTrackDto> tracks, String startDateTime) throws IOException {
		updateTracks(tracks, startDateTime, false);
	}

	@Override
	public void updateTracks(List<DeviceTrackDto> tracks, String startDateTime, boolean goBackInTime)
			throws IOException {
		updateTracks(tracks, startDateTime, new IntervalProvider() {
			public long getInterval(DeviceTrackDto dto) {
				return 60;
			}
		}, goBackInTime);
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

	@Override
	public Date getLastSubmissionDateTime() {
		return this.submitter.lastScrobbledTime();
	}

}
