package org.zenses.data.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.zenses.data.dao.DeviceTrackDao;
import org.zenses.data.dto.DeviceTrackDto;
import org.zenses.data.dto.LastFmSubmissionDto;
import org.zenses.mtp.MtpDeviceTrack;

@Transactional
public class DeviceTrackServiceImpl implements DeviceTrackService {

	Logger logger = Logger.getLogger(DeviceTrackServiceImpl.class);

	@Autowired
	private DeviceTrackDao deviceTrackDao;

	public void createOrUpdate(MtpDeviceTrack dt) {
		// PERFORMANCE TRICK: it only makes sense to do anything with tracks
		// with count > 0
		if (dt.getPlayCount() > 0) {
			DeviceTrackDto deviceTrackDto = deviceTrackDao.findByPersistentId(dt.getPersistentId(), dt.getDeviceId());
			if (deviceTrackDto == null) {
				deviceTrackDto = new DeviceTrackDto(dt.getZenId(), dt.getPersistentId(), dt.getDeviceId(), dt
						.getArtist(), dt.getTitle(), dt.getAlbum(), dt.getLength(), dt.getPlayCount(), dt.getFilename());
				// logger.info("Creating new track " + deviceTrackDto);
				deviceTrackDao.saveOrUpdate(deviceTrackDto);
			} else {
				if (!deviceTrackDto.getPlayCount().equals(dt.getPlayCount())) {
					// performance improvement - only update records if
					// playCount changed
					// logger.info("Updating track " + deviceTrackDto);
					deviceTrackDto.setLastRead(new Date());
					deviceTrackDto.setPlayCount(dt.getPlayCount());
					deviceTrackDao.saveOrUpdate(deviceTrackDto);
				}
			}
		}
		// logger.info("Track saved to db: " + deviceTrackDto);
	}

	public void addSubmission(DeviceTrackDto dt, long submittedTime) {
		LastFmSubmissionDto submission = new LastFmSubmissionDto(dt, new Date(submittedTime * 1000));
		deviceTrackDao.save(submission);
		logger.info("Submission added to db: " + dt);
	}

	public long getLastSubmissionTimestamp() {
		Date mostRecentSubmission = deviceTrackDao.getMostRecentSubmission();

		if (mostRecentSubmission == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(2000, 1, 1, 0, 0);

			return calendar.getTimeInMillis();
		}

		return mostRecentSubmission.getTime();
	}

	@Override
	public String getNextSubmissionTime() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm dd/MM/yyyy");
		return formatter.print(getLastSubmissionTimestamp() + 60000);
	}

	@Override
	public List<DeviceTrackDto> getUnsubmittedTracks() {
		return deviceTrackDao.getUnsubmittedTracks();
	}

	@Override
	public List<LastFmSubmissionDto> getScrobbledTracks() {
		return deviceTrackDao.getScrobbledTracks();
	}

	@Override
	public int getScrobbledTracksCount() {
		return deviceTrackDao.getScrobbledTracksCount();
	}
}
