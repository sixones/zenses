package org.zenses.lastfm;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.zenses.AbstractSpringTest;
import org.zenses.data.dao.DeviceTrackDao;
import org.zenses.data.dto.DeviceTrackDto;

@Transactional
public class TracksProcessorTest extends AbstractSpringTest {

	@Autowired
	private TracksSubmitter processor;
	
	@Autowired
	private DeviceTrackDao deviceTrackDao;

	@Test
	public void testUpdateTracksInDatabase() throws IOException {
		List<DeviceTrackDto> tracks = deviceTrackDao.getUnsubmittedTracks();
		processor.updateTracks(tracks, "04:55 03/06/2009", 0, true);
	}

}
