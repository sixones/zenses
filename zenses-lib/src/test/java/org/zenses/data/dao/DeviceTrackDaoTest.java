package org.zenses.data.dao;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.zenses.AbstractSpringTest;
import org.zenses.data.dto.DeviceTrackDto;
import org.zenses.data.dto.LastFmSubmissionDto;

@Transactional
public class DeviceTrackDaoTest extends AbstractSpringTest {

	private static final String DEVICE_ID = "FAKE_DEVICE2";

	private static final String PERSISTENT_ID = "098-3434-3424";

	@Autowired
	private DeviceTrackDao deviceTrackDao;

	@Test
	public void testCreateTack() {
		DeviceTrackDto deviceTrackDto = new DeviceTrackDto("{12345}", PERSISTENT_ID, DEVICE_ID, "HSM", "Scream",
				"High School Musical 3", new BigInteger("300234"), 3L, "HSM - Scream");
		deviceTrackDao.saveOrUpdate(deviceTrackDto);
		LastFmSubmissionDto submission = new LastFmSubmissionDto(deviceTrackDto, new Date());
		deviceTrackDao.save(submission);
		// TODO: assertions
		// System.out.println(deviceTrackDto.getSubmissions().size());
	}

	@Test
	public void testSubmissions() {
		@SuppressWarnings("unused")
		DeviceTrackDto deviceTrackDto = deviceTrackDao.findByPersistentId(PERSISTENT_ID, DEVICE_ID);
		// System.out.println(deviceTrackDto.getSubmissions().size());
	}

	@Test
	public void testGetUnsubmitted() {
		List<DeviceTrackDto> tracks = deviceTrackDao.getUnsubmittedTracks();
		assertEquals(1, tracks.size());
		assertEquals(2, tracks.get(0).getNumberOfPlaysToSubmit());

	}

}
