package org.zenses.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import jmtp.PortableDevice;

import org.junit.Ignore;
import org.junit.Test;
import org.zenses.data.service.DeviceTrackService;
import org.zenses.lastfm.TracksSubmitter;
import org.zenses.mtp.MtpDeviceService;
import org.zenses.mtp.linux.MtpDeviceServiceImpl;

public class ContextProviderUserprofileTest {

	static {
		System.setProperty("dbtype", "hsql");
		System.setProperty("mtplib", "linux");
	}

	@SuppressWarnings("unchecked")
	@Test
	@Ignore
	// should only be run to check that the lib is accessing db located in
	// userprofile folder
	public void testContext() {
		MtpDeviceService<PortableDevice> deviceService = ContextProvider.getDeviceService();
		DeviceTrackService deviceTrackService = ContextProvider.getDeviceTrackService();
		TracksSubmitter tracksSubmitter = ContextProvider.getTracksSubmitter();

		assertNotNull(deviceService);
		assertNotNull(deviceTrackService);
		assertNotNull(tracksSubmitter);

		assertEquals(MtpDeviceServiceImpl.class, deviceService.getClass());

		deviceTrackService.getUnsubmittedTracks();

	}
}
