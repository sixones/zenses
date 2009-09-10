package org.zenses.mtp;

import java.util.List;

import jmtp.PortableDevice;
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.zenses.AbstractSpringTest;
import org.zenses.data.service.DeviceTrackService;

@ContextConfiguration(locations = "classpath:mtplib-linux-context.xml")
@Transactional
public class MtpDeviceDaoTest extends AbstractSpringTest {

	@Autowired
	private MtpDeviceService<PortableDevice> mtpDeviceDao;

	@Autowired
	private DeviceTrackService deviceTrackService;

	@Test
	public void init() {
		List<MtpDevice<PortableDevice>> devices = mtpDeviceDao.getDevices();
		if (devices.size() > 0) {
			List<MtpDeviceTrack> tracks;
			
			try {
				tracks = mtpDeviceDao.getTracks(devices.get(0));
				
				for (MtpDeviceTrack track : tracks) {
					deviceTrackService.createOrUpdate(track);
				}
			} catch (MTPException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("Connect at least one device!");
		}

	}
}
