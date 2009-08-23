package org.zenses.mtp.linux;

import java.util.ArrayList;
import java.util.List;

import org.zenses.mtp.MtpDevice;
import org.zenses.mtp.MtpDeviceService;
import org.zenses.mtp.MtpDeviceTrack;

public class MtpDeviceServiceImpl implements MtpDeviceService<FakeLibraryDevice> {

	public List<MtpDevice<FakeLibraryDevice>> getDevices() {
		MtpDeviceImpl device1 = new MtpDeviceImpl(new FakeLibraryDevice("ZEN"));
		MtpDeviceImpl device2 = new MtpDeviceImpl(new FakeLibraryDevice("SANSA"));
		ArrayList<MtpDevice<FakeLibraryDevice>> devices = new ArrayList<MtpDevice<FakeLibraryDevice>>();
		devices.add(device1);
		devices.add(device2);
		return devices;
	}

	public List<MtpDeviceTrack> getTracks(MtpDevice<FakeLibraryDevice> portableDevice) {
		List<MtpDeviceTrack> tracks = new ArrayList<MtpDeviceTrack>();
		tracks.add(new MtpDeviceTrackImpl("Madonna", "Frozen", "Ray of light", portableDevice.getDelegate().getName()));
		return tracks;
	}

}
