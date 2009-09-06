package org.zenses;
import java.util.List;

import jmtp.PortableDevice;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.zenses.mtp.MtpDevice;
import org.zenses.mtp.MtpDeviceService;
import org.zenses.mtp.MtpDeviceTrack;

@ContextConfiguration(locations = "classpath:mtplib-jmtp-context.xml")
public class DebuggerTest extends AbstractSpringTest {

	private final static Logger logger = Logger.getLogger(DebuggerTest.class);

	@Autowired
	private MtpDeviceService<PortableDevice> mtpDeviceDao;

	@Test
	public void init() {
		List<MtpDevice<PortableDevice>> devices = mtpDeviceDao.getDevices();
		if (devices.size() > 0) {
			logger.warn("Found " + devices.size() + " device(s)");
			for (MtpDevice<PortableDevice> mtpDevice : devices) {
				logger.warn("Processing device: " + mtpDevice.getName() + " - fetching tracks...");
				List<MtpDeviceTrack> tracks = mtpDeviceDao.getTracks(mtpDevice, 200);
				logger.warn("Found " + tracks.size() + " tracks on the device [limited to 100 for performance]. Listing tracks with >0 listenCount:");
				for (MtpDeviceTrack track : tracks) {
					if (track.getPlayCount() > 0)
						logger.info(track);
				}
			}
		} else {
			logger.error("No devices found!");
		}

	}
}
