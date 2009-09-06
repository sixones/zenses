package org.zenses.mtp;

import java.util.List;

public interface MtpDeviceService<T> {

	List<MtpDevice<T>> getDevices();

	List<MtpDeviceTrack> getTracks(MtpDevice<T> portableDevice);

	/**
	 * Additional method, mainly for debugging purposes, to avoid scanning the
	 * device for a huge number of tracks
	 * 
	 * @param portableDevice
	 * @param maxNumberOfTracks
	 * @return
	 */
	List<MtpDeviceTrack> getTracks(MtpDevice<T> portableDevice, int maxNumberOfTracks);

}